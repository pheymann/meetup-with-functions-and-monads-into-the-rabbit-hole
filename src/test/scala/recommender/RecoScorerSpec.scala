package recommender

import recommender.model._
import UserAttributes._

import org.specs2.mutable.Specification

final class RecoScorerSpec extends Specification {

  "RecoScorer" >> {
    "scores user and user" >> {
      val base   = User(0.userId, "John".firstName, "Foo".lastName, 30.age, "Hamburg".city, "Data Engineer".job)
      val scorer = implicitly[RecoScorer[User, User]]

      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 30.age, "Hamburg".city, "Data Engineer".job)) === Score(3)
      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 29.age, "Hamburg".city, "Data Engineer".job)) === Score(2)
      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 30.age, "Berlin".city, "Data Engineer".job)) === Score(2)
      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 30.age, "Hamburg".city, "Data Scientist".job)) === Score(2)
      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 29.age, "Berlin".city, "Data Engineer".job)) === Score(1)
      scorer.score(base, User(1.userId, "Jim".firstName, "Fuu".lastName, 29.age, "Berlin".city, "Data Scientist".job)) === Score(0)
    }
  }
}
