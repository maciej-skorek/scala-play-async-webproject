package filters

import model.Task
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

trait TaskFilter {
  val filterType = this.getClass.getSimpleName

  def insertInto(tasks: Future[List[Task]])(implicit context: ExecutionContext): Unit = {
    tasks.map(list => list.withFilter(this.matchFilter))
  }

  def matchFilter(task: Task) = true
}

object TaskFilter{

  implicit val reader = ( (JsPath \ "filterType").read[String] and (JsPath.json.pick) ).tupled.flatMap{ case (filterType, js) =>
    filterType match {
      case "PhraseFilter" => Reads{ _ => Json.fromJson[PhraseFilter](js) }.map{ c => c: TaskFilter }
      case "DeadlineFilter" => Reads{ _ => Json.fromJson[DeadlineFilter](js) }.map{ c => c: TaskFilter }
      case _ => throw  new IllegalStateException("Unknown filter type")
    }
  }

  implicit val writer: Writes[TaskFilter] = new Writes[TaskFilter] {
    def writes(ins: TaskFilter): JsValue = ins match {
      case a: PhraseFilter    => Json.toJson(a)(Json.writes[PhraseFilter])
      case b: DeadlineFilter  => Json.toJson(b)(Json.writes[DeadlineFilter])
      case _ => throw new IllegalStateException("Unknown TaskFilter")
    }
  }
}