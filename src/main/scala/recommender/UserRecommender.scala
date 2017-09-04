package recommender

import recommender.model._

import cats._
import data._

import scala.language.higherKinds

object UserRecos {

  import RecoScorer._
  import Recommender._

  trait UserReader[F[_]] {

    def findUser(userId: UserId): F[User]
    def fetchUsersExcept(user: User): F[Seq[User]]
  }

  def recosForUser[F[_]](userId: UserId)(implicit F: Monad[F]): Kleisli[F, UserReader[F], Seq[Recommendation[User]]] =
    for {
      user  <- Kleisli[F, UserReader[F], User](_.findUser(userId))
      recos <- Kleisli[F, UserReader[F], Seq[User]](_.fetchUsersExcept(user))
    } yield createRecos[User, User](user, recos)
}
