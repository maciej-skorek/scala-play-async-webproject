package model

import play.api.libs.json.{JsValue, Writes}
import reactivemongo.bson.BSONObjectID

case class Task(var id: String, title: String, description: String, completed: Option[Boolean] = Option(false), deadline: Option[Long] = Option.empty){

}

object Task {

  import play.api.libs.json.Json

  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val taskFormat = Json.format[Task]
}

