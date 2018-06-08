package filters

import java.util.regex.Pattern

import model.Task
import play.api.libs.json.Json

case class PhraseFilter(val pattern: String) extends TaskFilter{


  override def matchFilter(task: Task): Boolean = super.matchFilter(task)
}
object PhraseFilter{
  // Generates Writes and Reads for Task thanks to Json Macros
  implicit val phraseFilterFormat = Json.format[PhraseFilter]
}