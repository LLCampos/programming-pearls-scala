package part1.column1.util

import java.io.{File, PrintWriter}

import scala.annotation.tailrec

/**
 * Creates file with N random non-repeating numbers.
 */
object CreateExampleFile {
  private val FilePath = "src/test/scala/resources/part1/column1/listSize1000000.txt"
  private val N = 1000000
  private val Random = scala.util.Random

  def main(args: Array[String]): Unit =
    createFileWithNRandomNumbers(N, FilePath)

  def createFileWithNRandomNumbers(n: Int, filePath: String): Unit = {
    val pw = new PrintWriter(new File(filePath))
    createListOfNonRepeatingRandomNumbers(n).foreach(n => pw.write(n + "\n"))
    pw.close()
  }

  def createListOfNonRepeatingRandomNumbers(listSize: Int): Seq[Int] = {
    @tailrec
    def addNonRepeatingNumberToList(l: Seq[Int]): Seq[Int] = {
      val nextRandomNumber = Random.nextInt(9999999)
      if (l.contains(nextRandomNumber)) addNonRepeatingNumberToList(l) else l :+ nextRandomNumber
    }

    (1 to listSize).foldLeft(Seq.empty[Int])((l, i) => {
      println(s"Generating ${i}th random number")
      addNonRepeatingNumberToList(l)
    })
  }
}
