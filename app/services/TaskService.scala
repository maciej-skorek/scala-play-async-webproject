package services

import java.util.Date

import model.Task
import play.api.libs.json.Json
import reactivemongo.bson.BSONDocument
import repository.TaskRepoImpl


 class TaskService()(implicit repository: TaskRepoImpl) {

  def findAll = repository.find()

  def findByPhase(phase:String)={}

  def findBeforeDeadline(date: Date) = {}


  def delete(id: Int)= repository.remove(BSONDocument("_id" -> id))

  def modify(task: Task) = repository.update(BSONDocument("_id" -> task._id), task)

   def create(task: Task) = repository.save(task)


}
