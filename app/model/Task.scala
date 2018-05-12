package model

case class Task(val name: String);

object Task {

  import play.api.libs.json.Json

  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val taskFormat = Json.format[Task]
}

