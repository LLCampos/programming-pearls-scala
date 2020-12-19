package part1.column2

import scala.io.Source

trait AnagramFinder {
  def find(filePath: String): Set[Set[String]]
}

object AnagramFinder1 extends AnagramFinder {
  type CharCount = Map[Char, Int]
  type WordsByCount = Map[CharCount, Set[String]]

  def find(filePath: String): Set[Set[String]] = {
    //noinspection SourceNotClosed
    val words = Source.fromFile(filePath).getLines().toList
    val charCounts = words.map(w => w -> countChars(w))

    charCounts.foldLeft(Map[CharCount, Set[String]]())((wordsByCount, count) => {
      val (word, charCount) = count
      wordsByCount + (charCount -> (wordsByCount.getOrElse(charCount, Set()) + word))
    }).filter(_._2.size > 1).values.toSet
  }

  private def countChars(w: String): CharCount =
    w.foldLeft(Map[Char, Int]())((charCount, char) =>
      charCount + (char -> (charCount.getOrElse(char, 0) + 1))
    )
}

// Similar in speed to the above, but faster and simpler!
object AnagramFinder2 extends AnagramFinder {
  def find(filePath: String): Set[Set[String]] = {
    //noinspection SourceNotClosed
    val words = Source.fromFile(filePath).getLines().toSet
    words.groupBy(countChars).filter(_._2.size > 1).values.toSet
  }

  private def countChars(w: String): Map[Char, Int] =
    w.foldLeft(Map[Char, Int]())((charCount, char) =>
      charCount + (char -> (charCount.getOrElse(char, 0) + 1))
    )
}
