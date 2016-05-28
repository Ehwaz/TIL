import org.scalatest.FunSuite

import Memo_Lect_1_4._

/**
  * Created by Ehwaz on 2016-05-28.
  */
class Memo_Lect_1_4_Test extends FunSuite {

  test("testBetterAnd") {
      assert(betterAnd(true, true) == true)
      assert(betterAnd(true, false) == false)
      assert(betterAnd(false, true) == false)
      assert(betterAnd(false, false) == false)

      assert(betterAnd(false, loop) == false) // 2nd argument is evaluated only when needed.
  }

  test("testMyAnd") {
    assert(myAnd(true, true) == true)
    assert(myAnd(true, false) == false)
    assert(myAnd(false, true) == false)
    assert(myAnd(false, false) == false)
  }

  test("testBetterOr") {
    assert(betterOr(true, true) == true)
    assert(betterOr(true, false) == true)
    assert(betterOr(false, true) == true)
    assert(betterOr(false, false) == false)

    assert(betterOr(true, loop) == true)    // 2nd argument is evaluated only when needed.
  }
}
