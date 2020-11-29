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

object MergeSort extends SortingAlgorithm {
  def sort(list: Seq[Int]): Seq[Int] =
    if (list.size <= 1) {
      list
    } else {
      val subListSize = (list.size / 2.0).ceil.toInt
      val (subList1, subList2) = list.splitAt(subListSize)
      val sortedSubLists = List(subList1, subList2).map(sort)
      mergeSort(sortedSubLists.head, sortedSubLists.last)
    }

  @tailrec
  private def mergeSort(subList1: Seq[Int], subList2: Seq[Int], newList: Seq[Int] = Seq.empty): Seq[Int] =
    (subList1.headOption, subList2.headOption) match {
      case (Some(a), Some(b)) if a <= b =>
        mergeSort(subList1.drop(1), subList2, newList :+ a)
      case (Some(a), Some(b)) if b < a =>
        mergeSort(subList1, subList2.drop(1), newList :+ b)
      case (Some(a), None) =>
        mergeSort(subList1.drop(1), subList2, newList :+ a)
      case (None, Some(b)) =>
        mergeSort(subList1, subList2.drop(1), newList :+ b)
      case (None, None) =>
        newList
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
