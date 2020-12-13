package part1.column2

import org.scalameter.Bench
import org.scalameter.api.Gen
import org.scalameter.picklers.noPickler.instance

class VectorRotatorBenchmarkTest extends Bench.LocalTime {

  private val vectorGen = Gen.enumeration("vector")(Seq(1,5,6,7,8), 1 to 10000, Seq("A", "B", "C", "D"))
  private val positionGen = Gen.enumeration("position")(1, 10, 100, 1000)
  private val rotatorAlgoGen = Gen.enumeration("algorithm")(VectorRotator1, VectorRotator2)
  private val inputs = Gen.crossProduct(vectorGen, positionGen, rotatorAlgoGen)

  performance of "VectorRotator" in {
    measure method "rotate" in {
      using(inputs) in { case (vector, position, algorithm) =>
        algorithm.rotate(vector, position)
      }
    }
  }

}
