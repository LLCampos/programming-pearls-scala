package part1.column1

import org.specs2.mutable.Specification

class CrackingTheOysterTest extends Specification {

  "CrackingTheOysterTest" should {
    "sort" should {
      "be able to sort small list with odd size" in {
        val result = CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize3.txt")
        result must be equalTo List(1111111, 2222222, 3333333)
      }

      "be able to sort small list with odd size" in {
        val result = CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize4.txt")
        result must be equalTo List(1111111, 2222222, 3333333, 4444444)
      }

      "return unmodified list of only one element" in {
        val result = CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize1.txt")
        result must be equalTo List(2222222)
      }

      "return empty list on empty life" in {
        val result = CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize0.txt")
        result must be equalTo List()
      }
    }
  }
}
