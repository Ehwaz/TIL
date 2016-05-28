import org.scalatest.FunSuite

import scala.math
import Memo_Lect_1_5.mySqrt

/**
  * Created by Ehwaz on 2016-05-28.
  */
class Memo_Lect_1_5_Test extends FunSuite {

  def printDiff(x: Double) = println(math.abs(mySqrt(x) - math.sqrt(x)))

  test("testMySqrt") {
    printDiff(2)
    printDiff(4)
    printDiff(1e-6)
    printDiff(1e60)

    printDiff(0.001)
    printDiff(0.1e-20)
    printDiff(1e20)
    printDiff(1e50)
  }

}
