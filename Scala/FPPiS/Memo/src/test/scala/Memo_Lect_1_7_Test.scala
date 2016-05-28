import org.scalatest.FunSuite

import Memo_Lect_1_7._

/**
  * Created by Ehwaz on 2016-05-28.
  */
class Memo_Lect_1_7_Test extends FunSuite {

  test("testMyFactTailRec") {
    val answer = myFactIter(20)
    println(answer)
    assert(myFactTailRec(20) == answer)
  }

  test("testMyFactTailRec2") {
    val answer = myFactIter(20)
    assert(myFactTailRec2(20) == answer)
  }

}
