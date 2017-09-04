package recommender

import recommender.model._
import UserAttributes._

import cats._
import implicits._
import org.specs2.mutable.Specification

final class UserRecommender extends Specification {

  import UserRecos._

  def testUserR(user: User, users: Seq[User]) = new UserReader[Id] {
    def findUser(userId: UserId) = user
    def fetchUsersExcept(user: User) = users
  }

  "UserRecommender" >> {
    "recommends other user to a given base user" >> {
      val base = User(0.userId, "John".firstName, "Foo".lastName, 30.age, "Hamburg".city, "Data Engineer".job)

      val reco0 = User(1.userId, "Jim".firstName, "Fuu".lastName, 29.age, "Berlin".city, "Data Engineer".job)
      val reco1 = User(2.userId, "Tim".firstName, "Faa".lastName, 29.age, "Hamburg".city, "Data Engineer".job)
      val reco2 = User(3.userId, "Tom".firstName, "Fii".lastName, 29.age, "Berlin".city, "Data Scientist".job)

      val calcRecos = recosForUser[Id](0.userId)

      calcRecos(testUserR(base, Seq(reco0, reco1, reco2)))  === Seq(
        Recommendation(reco1, Score(2)),
        Recommendation(reco0, Score(1))
      )
    }
  }
}
