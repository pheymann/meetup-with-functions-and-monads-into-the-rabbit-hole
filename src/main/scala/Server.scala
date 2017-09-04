
import recommender._
import recommender.model._
import recommender.io._

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport._
import org.rogach.scallop._
import cats.implicits._

import scala.io.Source
import scala.util.{Success, Failure}
import scala.concurrent.{ExecutionContext, Future}

object Server {

  private val Port = 8080

  import UserAttributes._
  import UserRecos._
  import StatusCodes.BadRequest

  def userReader(filename: String)(implicit ec: ExecutionContext) = new UserReader[Future] {
    def findUser(userId: UserId): Future[User] = CsvReader.readUser(Source.fromFile(filename).getLines(), userId)
    def fetchUsersExcept(user: User): Future[Seq[User]] = CsvReader.readUsersExcept(Source.fromFile(filename).getLines(), user)
  }

  class CliParams(args: Array[String]) extends ScallopConf(args) {
    val filename = opt[String](required = true, descr = "user database file")

    verify()
  }

  private def routes(userR: UserReader[Future])(implicit ec: ExecutionContext) =
    path("recommendations" / "user" / LongNumber) { id =>
      get {
        val calcRecos = UserRecos.recosForUser[Future](id.userId)

        onComplete(calcRecos(userR)) {
          case Success(recos) => complete(recos)
          case Failure(cause) => complete((BadRequest, s"user = $id raised an issue: ${cause.getMessage()}"))
        }
      }
    }

  def main(args: Array[String]): Unit = {
    val params = new CliParams(args)

    implicit val system       = ActorSystem("user_service")
    implicit val materializer = ActorMaterializer()

    import system.dispatcher

    Http().bindAndHandle(routes(userReader(params.filename())), "localhost", Port)
  }
}
