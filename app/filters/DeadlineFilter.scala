package filters

import model.Task
import play.api.libs.json.Json

case class DeadlineFilter(maxDate: Long) extends TaskFilter{

  override def matchFilterInternal(task: Task): Boolean = {
    task.deadline <= maxDate
  }

}

object DeadlineFilter{
  implicit val deadlineFilterFormat = Json.format[DeadlineFilter]
}