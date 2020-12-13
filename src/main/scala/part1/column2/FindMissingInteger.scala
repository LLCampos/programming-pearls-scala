package part1.column2

import part1.column1.{CrackingTheOyster, ExternalMergeSort}

import java.io.File
import scala.collection.Searching.Found
import scala.io.Source

/**
 * Solving the same problem but for a smaller number of integers/files of smaller size. Because well, it will take too
 * much time to generate a 16GB file of random integers. :)
 */
object FindMissingInteger {

  // Naive solution. Loads everything into memory. Also slow.
  def find1(filePath: String): Int = {
    //noinspection SourceNotClosed
    val integers = Source.fromFile(filePath).getLines().toList.map(_.toInt)
    (0 to Int.MaxValue).find(i => !integers.contains(i)).get
  }

  // Still loads everything to memory but it's faster, since it doesn't have to go through the list of integers many
  // times (what happens on find1 because of contains)
  def find2(filePath: String): Int = {
    //noinspection SourceNotClosed
    val integers = Source.fromFile(filePath).getLines().toVector.map(_.toInt).sorted
    (0 to Int.MaxValue).zip(integers).find(i => i._1 != i._2).get._1
  }

  // Same as above, but uses lazy zip. A little faster.
  def find3(filePath: String): Int = {
    //noinspection SourceNotClosed
    val integers = Source.fromFile(filePath).getLines().toVector.map(_.toInt).sorted
    (0 to Int.MaxValue).lazyZip(integers).find(i => i._1 != i._2).get._1
  }

  // 3x times slower, but more memory efficient
  def find4(filePath: String): Int = {
    val sortedFilePath = "find4.txt"
    CrackingTheOyster.externalSortFile(filePath, sortedFilePath, ExternalMergeSort, 25000)
    val file = new File(sortedFilePath)
    //noinspection SourceNotClosed
    val integers = Source.fromFile(file).getLines().map(_.toInt)
    val resultInt = integers.zip(0 to Int.MaxValue).find(i => i._1 != i._2).get._2
    file.delete()
    resultInt
  }

  // Like find1, but uses arrays and binary search.
  // It's faster than find1, but slower than find2 and find3
  def find5(filePath: String): Int = {
    //noinspection SourceNotClosed
    val integers = Source.fromFile(filePath).getLines().toArray.map(_.toInt).sorted
    (0 to Int.MaxValue).find(i => integers.search(i) match {
      case Found(_) => false
      case _ => true
    }).get
  }
}
