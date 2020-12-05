package part1.column1

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeEach
import org.specs2.specification.core.Fragments

import java.io.File
import scala.io.Source

class CrackingTheOysterTest extends Specification with BeforeEach {

  sequential

  private val PathToTestFiles = "src/test/scala/resources/part1/column1"
  private val ResultFileName = s"$PathToTestFiles/resultTmp.txt"

  override def before: Any =
    new File(ResultFileName).delete()

  "CrackingTheOysterTest" should {
    "sort" should {
      Fragments.foreach(Seq(MergeSort, ScalaDefaultSort, ScalaMergeSort)) { algorithm =>
        s"${algorithm.getClass.getName}" should {
          "be able to sort small list with odd size" in {
            val result = CrackingTheOyster.sortFile(s"$PathToTestFiles/listSize3.txt", algorithm)
            result must be equalTo List(1111111, 2222222, 3333333)
          }

          "be able to sort small list with odd size" in {
            val result = CrackingTheOyster.sortFile(s"$PathToTestFiles/listSize4.txt", algorithm)
            result must be equalTo List(1111111, 2222222, 3333333, 4444444)
          }

          "return unmodified list of only one element" in {
            val result = CrackingTheOyster.sortFile(s"$PathToTestFiles/listSize1.txt", algorithm)
            result must be equalTo List(2222222)
          }

          "return empty list on empty life" in {
            val result = CrackingTheOyster.sortFile(s"$PathToTestFiles/listSize0.txt", algorithm)
            result must be equalTo List()
          }
        }
      }
    }

    "bit sort" should {
      "be able to sort small list with odd size" in {
        val result = BitSort.sort(s"$PathToTestFiles/listSize3.txt")
        result must be equalTo List(1111111, 2222222, 3333333)
      }

      "be able to sort small list with odd size" in {
        val result = BitSort.sort(s"$PathToTestFiles/listSize4.txt")
        result must be equalTo List(1111111, 2222222, 3333333, 4444444)
      }

      "return unmodified list of only one element" in {
        val result = BitSort.sort(s"$PathToTestFiles/listSize1.txt")
        result must be equalTo List(2222222)
      }

      "return empty list on empty life" in {
        val result = BitSort.sort(s"$PathToTestFiles/listSize0.txt")
        result must be equalTo List()
      }
    }

    "internal sort" should {
      Fragments.foreach(Seq(ExternalMergeSort)) { algorithm =>
        s"${algorithm.getClass.getName}" should {
          "be able to sort small list with odd size" in {
            CrackingTheOyster.externalSortFile(
              s"$PathToTestFiles/listSize3.txt",
              ResultFileName,
              algorithm,
              2)

            getResultFileLines must be equalTo List(1111111, 2222222, 3333333)
          }

          "be able to sort small list with even size" in {
            CrackingTheOyster.externalSortFile(
              s"$PathToTestFiles/listSize4.txt",
              ResultFileName,
              algorithm,
              2)

            getResultFileLines must be equalTo List(1111111, 2222222, 3333333, 4444444)
          }

          "return unmodified list of only one element" in {
            CrackingTheOyster.externalSortFile(
              s"$PathToTestFiles/listSize1.txt",
              ResultFileName,
              algorithm,
              2)

            getResultFileLines must be equalTo List(2222222)
          }

          "return empty list on empty life" in {
            CrackingTheOyster.externalSortFile(
              s"$PathToTestFiles/listSize0.txt",
              ResultFileName,
              algorithm,
              2)

            getResultFileLines must be equalTo List()
          }
        }
      }
    }
  }

  def getResultFileLines: Seq[Int] = {
    val file = Source.fromFile(ResultFileName)
    val result = file.getLines().toList.map(_.toInt)
    file.close()
    result
  }
}
