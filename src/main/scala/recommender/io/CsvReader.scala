package recommender.io

import recommender.model._
import UserAttributes._

import scala.util.control.NonFatal
import scala.collection.mutable.Builder
import scala.concurrent.{Future, ExecutionContext}

object CsvReader {

  case class IOReadException(msg: String) extends Exception

  private def readFile[A](lines: Iterator[String])(f: String => Either[String, Option[A]]): Future[Seq[A]] = {
    // we go with mutable.Builder here to prevent a costly reverse
    def readLoop(iter: Iterator[String], acc: Builder[A, Seq[A]]): Either[String, Seq[A]] =
      if (iter.hasNext) {
        f(iter.next()) match {
          case Right(Some(a)) => readLoop(iter, acc += a)
          case Right(None)    => readLoop(iter, acc)
          case Left(error)    => Left(error)
        }
      }
      else
        Right(acc.result())

    readLoop(lines, Seq.newBuilder[A]) match {
      case Right(results) => Future.successful(results)
      case Left(error)    => Future.failed(IOReadException(error))
    }
  }

  def readUser(lines: Iterator[String], userId: UserId)(implicit ec: ExecutionContext): Future[User] = readFile[User](lines) { line =>
    line.split("\\s*,\\s*").map(_.trim) match {
      case Array(id, fName, lName, a, c, j) =>
        try {
          val uId = id.toLong.userId

          if (uId.id == userId.id)
            Right(Some(User(uId, fName.firstName, lName.lastName, a.toInt.age, c.city, j.job)))
          else
            Right(None)
        } catch {
          case parseError: NumberFormatException => Left(s"failed to parse user attribute: ${parseError.getMessage()}")
          case NonFatal(other)                   => Left(s"unexpected error: $other")
        }

      case other => Left(s"line has invalid number = ${other.length} of tokens: $line")
    }
  }.map(_.headOption.getOrElse (
      throw new NoSuchElementException(s"no user found with id = $userId")
  ))

  def readUsersExcept(lines: Iterator[String], user: User): Future[Seq[User]] = readFile[User](lines) { line =>
    line.split("\\s*,\\s*").map(_.trim) match {
      case Array(id, fName, lName, a, c, j) =>
        try {
          val uId = id.toLong.userId

          if (uId.id != user.id.id)
            Right(Some(User(uId, fName.firstName, lName.lastName, a.toInt.age, c.city, j.job)))
          else
            Right(None)
        } catch {
          case parseError: NumberFormatException => Left(s"failed to parse user attribute: ${parseError.getMessage()}")
          case NonFatal(other)                   => Left(s"unexpected error: $other")
        }

      case other => Left(s"line has invalid number = ${other.length} of tokens: $line")
    }
  }
}
