package part1.column1

import java.io.{File, PrintWriter}
import scala.annotation.tailrec
import scala.io.Source

/**
 * Input:       A file containing at most n positive integers, each less than n, where n=10⁷. It is a fatal errors if
 *              any integer occurs twice in the input. No other data is associated with the integer.
 * Output:      A sorted list in increasing order of the input integers.
 * Constraints: At most (roughly) a megabyte of storage is available in main memory; ample disk storage is available.
 *              The run time can be at most several minutes; a run time of ten seconds need not be decreased.
 */

object CrackingTheOyster {

  // 32 bit per integer
  // 1 MB = 1000 kilobytes = 1000 * 1000 bytes = 100000 bytes = 100000 * 8 bits = 800000 bits
  // 800000 bits / 32 bit/integer = 250000 integers
  private val ChunkLength = 250000

  def sortFile(fileName: String, sortingAlgorithm: SortingAlgorithm): Seq[Int] = {
    //noinspection SourceNotClosed
    val values = Source.fromFile(fileName).getLines().toList.map(_.toInt)
    sortingAlgorithm.sort(values)
  }

  def externalSortFile(
    fileName: String,
    resultFileName:
    String, sortingAlgorithm: ExternalSortingAlgorithm,
    chunkLength: Int = ChunkLength
  ): Unit =
    sortingAlgorithm.sort(fileName, resultFileName, chunkLength)
}

trait SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int]
}

/**
 * Lessons:
 *
 * 1 - Prepending is much faster than appending
 * 2 - Less cases in pattern matching makes things faster
 *
 * Improved my algorithm after this one: https://www.programmersought.com/article/50122609997/
 */
object MergeSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] =
    if (list.size <= 1) {
      list
    } else {
      val subListSize = list.size / 2
      val (subListX, subListY) = list.splitAt(subListSize)
      mergeSort(sort(subListX), sort(subListY)).reverse
    }

  @tailrec
  private def mergeSort(listX: Seq[Int], listY: Seq[Int], listNew: Seq[Int] = Seq.empty): Seq[Int] =
    (listX, listY) match {
      case (x :: listXTail, y :: listYTail) =>
        if (x <= y)
          mergeSort(listXTail, listY, x +: listNew)
        else
          mergeSort(listX, listYTail, y +: listNew)
      case (_, Nil) => listX.reverse ++ listNew
      case (Nil, _) => listY.reverse ++ listNew
    }
}

object ScalaDefaultSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] = list.sorted
}

object ScalaMergeSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] = scala.util.Sorting.stableSort(list)
}


trait ExternalSortingAlgorithm {
  // Writes result file to disk
  def sort(fileName: String, resultFileName: String, chunkLength: Int): Unit
}

object ExternalMergeSort extends ExternalSortingAlgorithm {

  case class Chunk(
    iterator: Iterator[Int],
    currentValue: Option[Int],
    index: Int
  )

  private val DirToSaveTmpFiles = "/tmp/external_merge_sort"

  def sort(fileName: String, resultFileName: String, chunkLength: Int): Unit = {
    sortChunksAndSaveToFiles(fileName, chunkLength)

    val tmpSortedFiles = new File(DirToSaveTmpFiles).listFiles()

    val chunksIterators = tmpSortedFiles
      .map(Source.fromFile)
      .map(_.getLines().map(_.toInt))
      .zipWithIndex
      .map(v => Chunk(v._1, v._1.nextOption(), v._2))

    val outputPrintWriter = new PrintWriter(new File(resultFileName))
    merge(chunksIterators, outputPrintWriter)
    outputPrintWriter.close()
    tmpSortedFiles.map(_.delete())
  }

  @tailrec
  private def merge(chunks: Seq[Chunk], outputPrintWriter: PrintWriter): Unit =
    if (chunks.isEmpty) {
      ()
    } else {
      val minChunk = chunks.minBy(_.currentValue.get)
      outputPrintWriter.write(minChunk.currentValue.get + "\n")
      val updatedChunks = chunks.map(v => {
        if (v.index == minChunk.index) {
          Chunk(v.iterator, v.iterator.nextOption(), v.index)
        } else v
      }).filter(_.currentValue.nonEmpty)
      merge(updatedChunks, outputPrintWriter)
    }

  private def sortChunksAndSaveToFiles(fileName: String, chunkLength: Int): Unit = {
    val inputFile = Source.fromFile(fileName)
    inputFile
      .getLines()
      .map(_.toInt)
      .grouped(chunkLength)
      .foreach(l => writeToFile(l.sorted))
    inputFile.close()
  }

  private def writeToFile(l: Seq[Int]): Unit = {
    val fileName = java.util.UUID.randomUUID.toString
    val pw = new PrintWriter(new File(s"$DirToSaveTmpFiles/$fileName"))
    l.foreach(i => pw.write(i.toString + "\n"))
    pw.close()
  }
}

// TODO: Solution with streams?
