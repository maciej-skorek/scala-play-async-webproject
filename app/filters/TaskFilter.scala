package filters

import model.Task
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

class TaskFilter(var nextFilter: Option[TaskFilter] = None){
  val filterType = this.getClass.getSimpleName
  def matchFilter(task: Task):Boolean = if (nextFilter.isEmpty) true else nextFilter.get.matchFilter(task)
}

object TaskFilter{

  def setFilterTo(chain: TaskFilter, someFilter: Some[TaskFilter]): TaskFilter = {
    chain.nextFilter = someFilter
    someFilter.get
  }

  def buildFilterChain(filters: List[TaskFilter]):TaskFilter ={
    filters.foldLeft(new TaskFilter){(chain,filter) => setFilterTo(chain,Some(filter))}
  }

  implicit val reader = ( (JsPath \ "filterType").read[String] and (JsPath.json.pick) ).tupled.flatMap{ case (filterType, js) =>
    filterType match {
      case "PhraseFilter" => Reads{ _ => Json.fromJson[PhraseFilter](js) }.map{ c => c: TaskFilter }
      case "DeadlineFilter" => Reads{ _ => Json.fromJson[DeadlineFilter](js) }.map{ c => c: TaskFilter }
      case _ => throw  new IllegalStateException("Unknown filter type")
    }
  }

  implicit val writer: Writes[TaskFilter] = new Writes[TaskFilter] {
    override def writes(ins: TaskFilter): JsValue = ins match {
      case a: PhraseFilter    => Json.toJson(a)(Json.writes[PhraseFilter])
      case b: DeadlineFilter  => Json.toJson(b)(Json.writes[DeadlineFilter])
      case _ => throw new IllegalStateException("Unknown TaskFilter")
    }
  }
}