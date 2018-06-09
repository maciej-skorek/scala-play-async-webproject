package filters

import model.Task
import play.api.libs.functional.syntax._
import play.api.libs.json._


class TaskFilter(var nextFilter: Option[TaskFilter] = None){
  val filterType = this.getClass.getSimpleName

  def matchFilterInternal(task: Task) = true

  def matchFilter(task: Task):Boolean = {
      matchFilterInternal(task) && (nextFilter.isEmpty || nextFilter.get.matchFilter(task))
  }
}

object TaskFilter{

  def setFilterTo(chain: TaskFilter, someFilter: Option[TaskFilter]): TaskFilter = {
    chain.nextFilter = someFilter
    chain
  }

  def buildFilterChain(filters: List[TaskFilter]):TaskFilter ={
    filters.foldRight(new TaskFilter){(chain,filter) => setFilterTo(chain,Option(filter))}
  }

  implicit val reader = ( (JsPath \ "filterType").read[String] and (JsPath.json.pick) ).tupled.flatMap{ case (filterType, js) =>
    filterType match {
      case "PhraseFilter" => Reads{ _ => Json.fromJson[PhraseFilter](js) }.map{ c => c: TaskFilter }
      case "DeadlineFilter" => Reads{ _ => Json.fromJson[DeadlineFilter](js) }.map{ c => c: TaskFilter }
      case "CompletionFilter" => Reads{ _ => Json.fromJson[CompletionFilter](js) }.map{ c => c: TaskFilter }
      case _ => throw  new IllegalStateException("Unknown filter type")
    }
  }

  implicit val writer: Writes[TaskFilter] = new Writes[TaskFilter] {
    override def writes(ins: TaskFilter): JsValue = ins match {
      case a: PhraseFilter    => Json.toJson(a)(Json.writes[PhraseFilter])
      case b: DeadlineFilter  => Json.toJson(b)(Json.writes[DeadlineFilter])
      case c: CompletionFilter  => Json.toJson(c)(Json.writes[CompletionFilter])
      case _ => throw new IllegalStateException("Unknown TaskFilter")
    }
  }
}