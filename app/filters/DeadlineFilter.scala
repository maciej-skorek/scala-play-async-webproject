package filters

import model.Task
import play.api.libs.json.Json

case class DeadlineFilter(maxDate: Long) extends TaskFilter{

  override def matchFilter(task: Task): Boolean = {
    task.deadline <= maxDate
  }

}

object DeadlineFilter{
  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val deadlineFilterFormat = Json.format[DeadlineFilter]
}