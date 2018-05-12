package controllers

import javax.inject.Inject

import model.Task
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import repository.TaskRepoImpl


class TaskController @Inject()(components: ControllerComponents, val reactiveMongoApi: ReactiveMongoApi
                              ) extends AbstractController(components) with MongoController with ReactiveMongoComponents {


  def taskRepo = new TaskRepoImpl(reactiveMongoApi)(defaultExecutionContext)

  def getTask = Action.async { implicit request =>
    taskRepo.find().map(tasks => Ok(Json.toJson(tasks)))(defaultExecutionContext)
  }

  def postTask = Action.async{ implicit request =>
    taskRepo.save(Task("New task")).map(result => Ok(result.ok.toString))(defaultExecutionContext)
  }

  def putTask = TODO

  def deleteTask = TODO

}
