package part1.column2

import org.specs2.mutable.Specification

class FindMissingIntegerTest extends Specification {

  val FilesPath = "src/test/scala/resources/part1/column2"

  "FindMissingIntegerTest" should {
    "find" should {
      "find the only missing integer" in {
        FindMissingInteger.find1(s"$FilesPath/listSize10000.txt") must be equalTo 9123
        FindMissingInteger.find2(s"$FilesPath/listSize10000.txt") must be equalTo 9123
        FindMissingInteger.find3(s"$FilesPath/listSize10000.txt") must be equalTo 9123
        FindMissingInteger.find4(s"$FilesPath/listSize10000.txt") must be equalTo 9123
        FindMissingInteger.find5(s"$FilesPath/listSize10000.txt") must be equalTo 9123
      }
    }
  }
}
