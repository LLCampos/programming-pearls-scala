package part1.column1

import org.scalameter.api._
import org.scalameter.picklers.noPickler.instance

class CrackingTheOysterBenchmarkTest extends Bench.LocalTime {
  //override def measurer = new Measurer.MemoryFootprint

  private val fileSizeGen = Gen.enumeration("fileSize")(1000, 2500, 5000)

  performance of "CrackingTheOyster" in {
    measure method "sortFile" in {
      using(fileSizeGen) in { size =>
        CrackingTheOyster.sortFile(s"src/test/scala/resources/part1/column1/listSize$size.txt")
      }
    }
  }
}
