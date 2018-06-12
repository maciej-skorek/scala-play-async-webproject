import filters.PhraseFilter
import model.Task
import org.scalatest.FlatSpec

class PhraseFilterTest extends FlatSpec {

  "A filter with pattern set" should "properly filter task" in {
    val expectedContent = "expected"
    val otherValue = "not"
    val task = Task("id", otherValue, otherValue, Some(false), Some(123))
    val filter = PhraseFilter(expectedContent)

    assert(!filter.matchFilter(task))
  }

  "A filter with pattern set" should "properly match title and description" in {
    val expectedContent = "expected"
    val taskTitle = Task("id", "this is expected", "", Some(false), Some(123))
    val taskDesc = Task("id2", "", "this could be unexpected", Some(false), Some(123))
    val filter = PhraseFilter(expectedContent)

    assert(filter.matchFilter(taskTitle))
    assert(filter.matchFilter(taskDesc))
  }
}
