import filters.{DeadlineFilter, PhraseFilter}
import model.Task
import org.scalatest.FlatSpec

class PhraseFilterTest extends FlatSpec {

  "A filter with pattern set" should "properly filter task" in {
    val expectedContent = "expected"
    val otherValue = "not"
    val task = Task(1,otherValue,otherValue,false,123)
    val filter = PhraseFilter(expectedContent)

    assert(filter.matchFilter(task) == false)
  }

  "A filter with pattern set" should "properly match title and description" in {
    val expectedContent = "expected"
    val taskTitle = Task(1,expectedContent,"",false,123)
    val taskDesc = Task(1,"",expectedContent,false,123)
    val filter = PhraseFilter(expectedContent)

    assert(filter.matchFilter(taskTitle) == true)
    assert(filter.matchFilter(taskDesc) == true)
  }
}
