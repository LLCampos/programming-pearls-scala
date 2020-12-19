package part1.column2

import org.scalameter.api.{Bench, Gen}
import org.scalameter.picklers.noPickler.instance


class AnagramFinderBenchmarkTest extends Bench.LocalTime {

  private val FilesPath = "src/test/scala/resources/part1/column2"

  private val anagramFinderAlgoGen = Gen.enumeration("algorithm")(AnagramFinder1, AnagramFinder2)
  private val fileSizeGen = Gen.enumeration("fileSize")("Small")
  private val inputs = Gen.crossProduct(anagramFinderAlgoGen, fileSizeGen)

  performance of "AnagramFinder" in {
    measure method "find" in {
      using(inputs) in { case (algorithm, size) =>
        algorithm.find(s"$FilesPath/words$size.txt")
      }
    }
  }

}
