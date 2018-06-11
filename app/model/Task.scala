package model

import play.api.libs.json.{JsValue, Writes}

case class Task(var id: String, title: String, description: String, completed: Boolean = false, deadline: Long)

object Task {

  import play.api.libs.json.Json

  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val taskFormat = Json.format[Task]
}

