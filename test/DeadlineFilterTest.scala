import filters.{DeadlineFilter, TaskFilter}
import model.Task
import org.scalatest.FlatSpec

class DeadlineFilterTest extends FlatSpec {

  "A filter with max date set" should "properly filter task" in {
    val task = Task(1,"","",false,123)
    val filter = DeadlineFilter(120)

    assert(filter.matchFilter(task) == false)
  }
}
