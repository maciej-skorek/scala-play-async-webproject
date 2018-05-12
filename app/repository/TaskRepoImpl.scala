package repository

import javax.inject.Inject

import model.Task
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.ImplicitBSONHandlers._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future}


class TaskRepoImpl @Inject()(reactiveMongoApi: ReactiveMongoApi)(implicit ec:ExecutionContext) {


  def collection: Future[JSONCollection] = reactiveMongoApi.database.map(
    _.collection[JSONCollection]("tasks"))

   def find(): Future[List[Task]] = {
    val cursor: Future[Cursor[Task]] = collection.map {
      _.find(BSONDocument.empty).cursor[Task](ReadPreference.primary)
    }
    val futureTaskList: Future[List[Task]] = cursor.flatMap(_.collect[List](-1,Cursor.FailOnError[List[Task]]()))
    return futureTaskList
  }

   def select(selector: BSONDocument): Future[Option[Task]] = {
    //    collection.find(selector).one[Task]
    return null
  }

   def update(selector: BSONDocument, update: BSONDocument): Future[WriteResult] = {
    //    collection.update(selector, update)
    return null
  }

   def remove(document: BSONDocument): Future[WriteResult] = {
    //    collection.remove(document)
    return null
  }

   def save(task: Task): Future[WriteResult] = {
    //    collection.update(BSONDocument("_id" -> document.get("_id").getOrElse(BSONObjectID.generate)), document, upsert = true)
    return collection.flatMap(_.insert(task))
  }
}
