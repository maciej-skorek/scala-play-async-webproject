package repository

import javax.inject.Inject

import model.Task
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json.ImplicitBSONHandlers._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}


class TaskRepoImpl @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec: ExecutionContext) {


  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(
    _.collection[JSONCollection]("tasks"))

  def find(): Future[List[Task]] = {
    val cursor: Future[Cursor[Task]] = collection.map {
      _.find(BSONDocument.empty).cursor[Task](ReadPreference.primary)
    }
    val futureTaskList: Future[List[Task]] =
      cursor
        .flatMap(_.collect[List](-1, Cursor.FailOnError[List[Task]]()))
    return futureTaskList
  }

  def update(selector: BSONDocument, update: Task): Future[WriteResult] =
    collection.flatMap(_.update(selector, update))

  def remove(document: BSONDocument): Future[WriteResult] = {
   collection.flatMap(_.remove(document))
  }

  def save(task: Task): Future[WriteResult] = {
    task.id = BSONObjectID.generate().stringify;
    return collection.flatMap(_.insert(task))
  }
}
