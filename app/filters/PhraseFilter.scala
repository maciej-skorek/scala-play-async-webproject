package filters

import java.util.regex.Pattern

import model.Task
import play.api.libs.json.Json

case class PhraseFilter(val pattern: String) extends TaskFilter{

  override def matchFilter(task: Task): Boolean = {
    task.title.matches(pattern) || task.description.matches(pattern)
  }

}
object PhraseFilter{
  implicit val phraseFilterFormat = Json.format[PhraseFilter]
}