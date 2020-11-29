package part1.column1

import org.scalameter.api._
import org.scalameter.picklers.noPickler.instance

class CrackingTheOysterBenchmarkTest extends Bench.LocalTime {
  //override def measurer = new Measurer.MemoryFootprint

  private val fileSizeGen = Gen.enumeration("fileSize")(1000, 2500, 5000)
  private val sortingAlgoGen = Gen.enumeration("algorithm")(MergeSort)
  private val inputs = Gen.crossProduct(fileSizeGen, sortingAlgoGen)

  performance of "CrackingTheOyster" in {
    measure method "sortFile" in {
      using(inputs) in { case (size, algorithm) =>
        CrackingTheOyster.sortFile(s"src/test/scala/resources/part1/column1/listSize$size.txt", algorithm)
      }
    }
  }
}
