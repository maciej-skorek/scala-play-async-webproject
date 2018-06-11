package services

import java.util.Date

import filters.TaskFilter
import model.Task
import reactivemongo.bson.BSONDocument
import repository.TaskRepoImpl

import scala.concurrent.ExecutionContext


class TaskService()(implicit repository: TaskRepoImpl) {

  def findAll = repository.find()

  def findByPhase(phase: String) = {}

  def findBeforeDeadline(date: Date) = {}


  def delete(id: String) = repository.remove(BSONDocument("id" -> id))

  def modify(task: Task) = repository.update(BSONDocument("id" -> task.id), task)

  def create(task: Task) = repository.save(task)

  def findFiltered(filters: List[TaskFilter])(implicit ec: ExecutionContext) = {
    val filterChain = TaskFilter.buildFilterChain(filters)
    this.findAll.map(list => list.filter(filterChain.matchFilter))
  }


}
