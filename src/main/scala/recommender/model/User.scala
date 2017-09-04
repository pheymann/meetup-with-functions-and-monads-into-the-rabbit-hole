package recommender.model

import play.api.libs.json._

/* you may want to use shapeless.tag*/

final case class UserId(id: Long) extends AnyVal
final case class FirstName(name: String) extends AnyVal
final case class LastName(name: String) extends AnyVal
final case class Age(age: Int) extends AnyVal
final case class City(city: String) extends AnyVal
final case class Job(job: String) extends AnyVal


object UserAttributes {

  implicit val userIdW = Writes[UserId](id => JsNumber(id.id))

  implicit class UserIdOps(val id: Long) extends AnyVal {
    def userId: UserId = UserId(id)
  }

  implicit val fistNameW = Writes[FirstName](name => JsString(name.name))

  implicit class FirstNameOps(val name: String) extends AnyVal {
    def firstName: FirstName = FirstName(name)
  }

  implicit val lastNameW = Writes[LastName](name => JsString(name.name))

  implicit class LastNameOps(val name: String) extends AnyVal {
    def lastName: LastName = LastName(name)
  }

  implicit val ageW = Writes[Age](age => JsNumber(age.age))

  implicit class AgeOps(val _age: Int) extends AnyVal {
    def age: Age = Age(_age)
  }

  implicit val cityW = Writes[City](city => JsString(city.city))

  implicit class CityOps(val _city: String) extends AnyVal {
    def city: City = City(_city)
  }

  implicit val jobW = Writes[Job](job => JsString(job.job))

  implicit class JobOps(val _job: String) extends AnyVal {
    def job: Job = Job(_job)
  }
}

final case class User(id: UserId, firstName: FirstName, lastName: LastName, age: Age, city: City, job: Job)

object User {

  import UserAttributes._

  implicit val writes = Json.writes[User]
}
