package part1.column1

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
  def sortFile(fileName: String, sortingAlgorithm: SortingAlgorithm): Seq[Int] = {
    //noinspection SourceNotClosed
    val values = Source.fromFile(fileName).getLines().toList.map(_.toInt)
    sortingAlgorithm.sort(values)
  }
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
