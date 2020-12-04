package part1.column1

import org.scalameter.api._
import org.scalameter.picklers.noPickler.instance

class CrackingTheOysterBenchmarkTest extends Bench.LocalTime {
  //override def measurer = new Measurer.MemoryFootprint

  private val fileSizeGen = Gen.enumeration("fileSize")(1000, 2500, 5000, 10000, 100000)
  private val sortingAlgoGen = Gen.enumeration("algorithm")(MergeSort, ScalaDefaultSort, ScalaMergeSort)
  private val inputs = Gen.crossProduct(fileSizeGen, sortingAlgoGen)

  private val externalSortingAlgoGen = Gen.enumeration("externalAlgorithm")(ExternalMergeSort)

  performance of "CrackingTheOyster" in {
    measure method "sortFile" in {
      using(inputs) in { case (size, algorithm) =>
        CrackingTheOyster.sortFile(s"src/test/scala/resources/part1/column1/listSize$size.txt", algorithm)
      }
    }

    measure method "externalSortFile" in {
      using(externalSortingAlgoGen) in { algorithm =>
        CrackingTheOyster.externalSortFile(
          "src/test/scala/resources/part1/column1/listSize1000000.txt",
          "src/test/scala/resources/part1/column1/resultTmp.txt",
          algorithm)
      }
    }
  }
}
