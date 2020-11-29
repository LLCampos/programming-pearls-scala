package part1.column1

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
 */
object MergeSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] =
    if (list.size <= 1) {
      list
    } else {
      val subListSize = list.size / 2
      val (subListX, subListY) = list.splitAt(subListSize)
      mergeSort(sort(subListX), sort(subListY))
    }

  private def mergeSort(subListX: Seq[Int], subListY: Seq[Int]): Seq[Int] =
    (subListX, subListY) match {
      case (x :: subListXTail, y :: subListYTail) =>
        if (x <= y)
          x +: mergeSort (subListXTail, subListY)
        else
          y +: mergeSort(subListX, subListYTail)
      case (subListX, Nil) => subListX
      case (Nil, subListY) => subListY
    }
}

object ScalaDefaultSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] = list.sorted
}

object ScalaMergeSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] = scala.util.Sorting.stableSort(list)
}

// https://www.programmersought.com/article/50122609997/
object ProgrammerSoughtMergeSort extends SortingAlgorithm {

  def sort(list: Seq[Int]): Seq[Int] = {

    def merged(xList: Seq[Int], yList: Seq[Int]): Seq[Int] = {
      (xList, yList) match {
        case (Nil, _) => yList
        case (_, Nil) => xList
        case (x :: xTail, y :: yTail) =>
          if (x < y) {
            x +: merged(xTail, yList)
          } else {
            y +: merged(xList, yTail)
          }
      }
    }

    val n = list.length / 2
    if (n == 0) list
    else {
      val (x, y) = list splitAt n
      merged(sort(x), sort(y))
    }
  }
}
