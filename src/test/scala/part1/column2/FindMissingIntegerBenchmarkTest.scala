package part1.column2

import org.scalameter.api.{Bench, Gen}

class FindMissingIntegerBenchmarkTest extends Bench.LocalTime {

  private val nullGen = Gen.unit("null")
  private val FilesPath = "src/test/scala/resources/part1/column2"

  performance of "FindMissingInteger" in {
    measure method "find2" in {
      using(nullGen) in { _ =>
        FindMissingInteger.find2(s"$FilesPath/listSize100000.txt")
      }
    }

    measure method "find3" in {
      using(nullGen) in { _ =>
        FindMissingInteger.find3(s"$FilesPath/listSize100000.txt")
      }
    }

    measure method "find4" in {
      using(nullGen) in { _ =>
        FindMissingInteger.find4(s"$FilesPath/listSize100000.txt")
      }
    }

    measure method "find5" in {
      using(nullGen) in { _ =>
        FindMissingInteger.find4(s"$FilesPath/listSize100000.txt")
      }
    }
  }
}
