package filters

import model.Task
import play.api.libs.json.Json

case class PhraseFilter(pattern: String) extends TaskFilter {

  override def matchFilterInternal(task: Task): Boolean = {
    task.title.toLowerCase().contains(pattern.toLowerCase()) ||
      task.description.toLowerCase().contains(pattern.toLowerCase())
  }

}

object PhraseFilter {
  implicit val phraseFilterFormat = Json.format[PhraseFilter]
}