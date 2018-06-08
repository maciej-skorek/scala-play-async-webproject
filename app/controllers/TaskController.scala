package controllers

import javax.inject.Inject

import model.Task
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import repository.TaskRepoImpl
import services.TaskService



class TaskController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi
                              ) extends AbstractController(components) with MongoController with ReactiveMongoComponents {


  val taskRepo = new TaskRepoImpl(reactiveMongoApi)(defaultExecutionContext)
  val taskService = new TaskService()(taskRepo);

  def find = Action.async { implicit request =>
    taskService.findAll.map(tasks => Ok(Json.toJson(tasks)))(defaultExecutionContext)
  }

  def add = Action.async { implicit request =>
    val json = request.body.asJson.get
    taskService.create(json.as[Task])
    .map(result => Ok(result.ok.toString))(defaultExecutionContext)
  }

  def delete =  Action.async { implicit request =>
    val json = request.body.asJson.get
    taskService.delete(json.as[Task]._id)
      .map(result => Ok(result.ok.toString))(defaultExecutionContext)
  }

  def modify = Action.async { implicit request =>
    val json = request.body.asJson.get
    taskService.modify(json.as[Task])
      .map(result => Ok(result.ok.toString))(defaultExecutionContext)
  }

  def findTaskByPhrase = TODO

  def findDeadlineBeforeDate = TODO


}
