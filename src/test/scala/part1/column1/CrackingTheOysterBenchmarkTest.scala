package part1.column1

import org.scalameter.api._

class CrackingTheOysterBenchmarkTest extends Bench.LocalTime {
  private val nullGen = Gen.unit("null")

  performance of "CrackingTheOyster" in {
    measure method "sortFile" in {
      using(nullGen) in { _ =>
        CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize10000.txt")
      }
    }
  }
}
