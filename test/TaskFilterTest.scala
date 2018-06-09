import filters.{DeadlineFilter, PhraseFilter, TaskFilter}
import org.scalatest.FlatSpec

class TaskFilterTest extends FlatSpec {

  "A chain build with no filters" should "not be null" in {
    val taskFilter = TaskFilter.buildFilterChain(List())
    assert(taskFilter.isInstanceOf[TaskFilter])
  }

  "A chain build on null filter" should "throw NullPtrException" in {
    assertThrows[NullPointerException] {
      val taskFilter = TaskFilter.buildFilterChain(List(null))
    }
  }

  "Build chain" should "contain inserted filters" in {
    val filtersList =List(PhraseFilter("e"), DeadlineFilter(1), PhraseFilter(""))

    val taskFilter = TaskFilter.buildFilterChain(filtersList)

    var currentFilter = new TaskFilter()
    for( i <- 2 to -1){
      currentFilter = taskFilter.nextFilter.get
      assert(currentFilter == filtersList(i))
    }
  }
}
