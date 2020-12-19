package part1.column2

import org.specs2.mutable.Specification
import org.specs2.specification.core.Fragments

class AnagramFinderTest extends Specification {

  val FilesPath = "src/test/scala/resources/part1/column2"

  "AnagramFinderTest" should {
    "find" should {
      Fragments.foreach(Seq(AnagramFinder1, AnagramFinder2)) { algorithm =>
        s"${algorithm.getClass.getName}" should {
          "find anagrams in small file" in {
            algorithm.find(s"$FilesPath/wordsSmall.txt") must be equalTo Set(
              Set("pots", "stop", "tops"),
              Set("rat", "tar"),
              Set("angel", "glean")
            )
          }
        }
      }
    }
  }
}
