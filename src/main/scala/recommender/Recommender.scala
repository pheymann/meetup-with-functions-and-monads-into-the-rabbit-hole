package recommender

import recommender.model._

import cats._

trait RecoScorer[Base, Result] {

  def score(base: Base, elem: Result): Score
}

object RecoScorer {

  implicit val userScorer = new RecoScorer[User, User] {
    def score(base: User, elem: User): Score =
      if (base.age == elem.age && base.city == elem.city && base.job == elem.job) Score(3)
      else if (base.age == elem.age && base.city == elem.city)                    Score(2)
      else if (base.age == elem.age && base.job == elem.job)                      Score(2)
      else if (base.city == elem.city && base.job == elem.job)                    Score(2)
      else if (base.age == elem.age || base.city == elem.city || base.job == elem.job) Score(1)
      else Score(0)
  }
}

object Recommender {

  private[recommender] def createRecos[Base, Res](base: Base, elems: Seq[Res])
                                                 (implicit scorer: RecoScorer[Base, Res]): Seq[Recommendation[Res]] =
    elems
      .map(elem => Recommendation(elem, scorer.score(base, elem)))
      .filter(reco => Order[Score].gt(reco.score, Score(0)))
      .sortWith((l, r) => Order[Recommendation[Res]].gt(l, r))
}
