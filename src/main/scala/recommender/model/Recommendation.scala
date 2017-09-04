package recommender.model

import cats._
import play.api.libs.json._

final case class Score(score: Int) extends AnyVal

object Score {

  implicit val format = Json.format[Score]

  implicit val scoreOrder = new Order[Score] {
    def compare(l: Score, r: Score): Int =
      if (l.score < r.score)       -1
      else if (l.score == r.score)  0
      else                          1
  }
}

final case class Recommendation[A](element: A, score: Score)

object Recommendation {

  implicit val writeUser = Json.writes[Recommendation[User]]

  implicit def recoOrder[A] = new Order[Recommendation[A]] {
    def compare(l: Recommendation[A], r: Recommendation[A]): Int =
      Order[Score].compare(l.score, r.score)
  }
}
