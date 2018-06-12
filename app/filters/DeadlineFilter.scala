package filters

import model.Task
import play.api.libs.json.Json

case class DeadlineFilter(maxDate: Long) extends TaskFilter{

  override def matchFilterInternal(task: Task): Boolean = {
    if (task.deadline.isDefined)
      task.deadline.get <= maxDate
    else
      true
  }

}

object DeadlineFilter{
  implicit val deadlineFilterFormat = Json.format[DeadlineFilter]
}