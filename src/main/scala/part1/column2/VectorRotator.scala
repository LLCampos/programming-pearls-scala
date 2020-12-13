package part1.column2

import scala.annotation.tailrec

trait VectorRotator {
  def rotate[A](vector: Seq[A], i: Int): Seq[A]
}

object VectorRotator1 extends VectorRotator {
  @tailrec
  def rotate[A](vector: Seq[A], i: Int): Seq[A] =
    if (i == 0 || vector.isEmpty) {
      vector
    } else {
      rotate(vector.tail :+ vector.head, i - 1)
    }
}

// Generally faster than VectorRotator1
object VectorRotator2 extends VectorRotator {
  def rotate[A](vector: Seq[A], i: Int): Seq[A] = {
    if (vector.isEmpty) {
      vector
    } else {
      val simpleI = i % vector.size
      vector.slice(simpleI, vector.size) ++ vector.slice(0, simpleI)
    }
  }
}
