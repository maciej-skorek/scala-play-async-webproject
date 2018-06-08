package model

case class Task(var id: Option[Int], title: String, description: Option[String], completed: Boolean = false, deadline: Option[Long])

object Task {

  import play.api.libs.json.Json

  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val taskFormat = Json.format[Task]
}

