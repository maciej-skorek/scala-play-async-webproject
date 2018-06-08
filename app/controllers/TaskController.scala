package controllers

import javax.inject.Inject
import model.Task
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import repository.TaskRepoImpl

import scala.concurrent.Future


class TaskController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi
                              ) extends AbstractController(components) with MongoController with ReactiveMongoComponents {


  def taskRepo = new TaskRepoImpl(reactiveMongoApi)(defaultExecutionContext)

  var idCounter = 0 : Int

  var tasks : List[Task] = List()

  def getTask = Action{
      Ok(Json.toJson(tasks))
  }

  def postTask = Action(parse.json){ request =>
    val task: Task = Json.fromJson[Task](request.body).get
    idCounter += 1
    task.id = Some(idCounter)
    tasks = task :: tasks
    Ok(Json.toJson(task))
  }

  def putTask = Action(parse.json){ request =>
    val task: Task = Json.fromJson[Task](request.body).get
    val oldTask = tasks.find(_.id.contains(task.id.get)).get
    tasks = tasks.updated(tasks.indexOf(oldTask), task)
    Ok(Json.toJson(task))
  }

  //  def getTask = Action.async { implicit request =>
//    taskRepo.find().map(tasks => {
//      var result = Ok(Json.toJson(tasks))
//      result.header.headers.+("Access-Control-Allow-Origin") //  +{"Access-Control-Allow-Origin": "true"}
//      result
//    })(defaultExecutionContext)
//  }

//
//
//  def postTask = Action.async { implicit request =>
//    taskRepo
//      .save(Task(1, "Title1", "description1", false, 2L))
//    .map(result => Ok(result.ok.toString))(defaultExecutionContext)
//  }

  def deleteTask = TODO

}
