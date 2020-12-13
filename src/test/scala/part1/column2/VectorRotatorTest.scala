package part1.column2

import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import org.specs2.specification.core.Fragments

class VectorRotatorTest extends Specification with ScalaCheck {

  "VectorRotatorTest" should {
    "rotate" should {
      Fragments.foreach(Seq(VectorRotator1, VectorRotator2)) { algorithm =>
        s"${algorithm.getClass.getName}" should {
          "rotating an empty vector returns an empty vector" in {
            val inputVector = Seq.empty
            algorithm.rotate(inputVector, 1) must be equalTo inputVector
          }

          "rotating by 0 positions doesn't change the vector" in prop { (inputVector: Seq[Int]) =>
            algorithm.rotate(inputVector, 0) must be equalTo inputVector
          }

          "rotating a vector of size n by n positions doesn't change the vector" in prop { (inputVector: Seq[Int]) =>
            algorithm.rotate(inputVector, inputVector.size) must be equalTo inputVector
          }

          "correctly rotate vector of size n by i (i < n, i != 0) positions" in {
            val inputVector = Seq("A", "B", "C", "D")
            algorithm.rotate(inputVector, 1) must be equalTo Seq("B", "C", "D", "A")
            algorithm.rotate(inputVector, 2) must be equalTo Seq("C", "D", "A", "B")
            algorithm.rotate(inputVector, 3) must be equalTo Seq("D", "A", "B", "C")
          }

          "correctly rotate vector of size n by i (i > n) positions" in {
            val inputVector = Seq("A", "B", "C", "D")
            algorithm.rotate(inputVector, 5) must be equalTo Seq("B", "C", "D", "A")
            algorithm.rotate(inputVector, 6) must be equalTo Seq("C", "D", "A", "B")
            algorithm.rotate(inputVector, 7) must be equalTo Seq("D", "A", "B", "C")
          }
        }
      }
    }
  }
}
