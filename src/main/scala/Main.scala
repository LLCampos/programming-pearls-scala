import part1.column1.{CrackingTheOyster, MergeSort}

object Main {
  def main(args: Array[String]): Unit = {
    CrackingTheOyster.sortFile("src/test/scala/resources/part1/column1/listSize5000.txt", MergeSort)
  }
}
