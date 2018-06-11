package filters
import model.Task
import play.api.libs.json.Json

case class CompletionFilter(val completion: Boolean) extends TaskFilter {
  override def matchFilterInternal(task: Task): Boolean = {
    task.completed.get == completion
  }
}
object CompletionFilter{
  implicit val completionFilterFormat = Json.format[CompletionFilter]
}