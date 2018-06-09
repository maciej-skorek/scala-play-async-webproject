import filters.{CompletionFilter, DeadlineFilter}
import model.Task
import org.scalatest.{FlatSpec, FunSuite}

class CompletionFilterTest extends FlatSpec {

  "A filter with completion state set" should "properly filter task" in {
    val task = Task(1,"","",false,123)
    val filter = CompletionFilter(true)

    assert(filter.matchFilter(task) == false)
  }
}
