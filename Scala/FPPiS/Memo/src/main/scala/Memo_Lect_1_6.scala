/**
  * Created by Ehwaz on 2016-05-28.
  */

import Memo_Lect_1_5.myAbs

object Memo_Lect_1_6 extends App {
  println("Lecture 1.6: Blocks and Lexical Scope")

  val x = 0
  def f(y: Int) = y + 1
  val result = {
    // The definitions inside a block shadow definitions of the same names outside the block.
    val x = f(3)
    val k = 4
    x * x
  }
  //println(k)      // The definitions inside a block are only visible in the same block.
  println(result)

  /*
  Definitions of outer blocks are visible inside a block unless they are shadowed.
  So. we can simplify mySqrt() by eliminating redundant occurrences of the 'x', which means the same value in everywhere.
   */
  def mySqrt2(x: Double) = {
    def mySqrtUtil(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else mySqrtUtil(improve(guess))

    def isGoodEnough(guess: Double) = myAbs(guess * guess - x) < 0.00001 * x

    def improve(guess: Double) = (guess + x / guess) / 2

    mySqrtUtil(1)
  }

  // Semicolon is used to write multiple expressions in a single line.
  val y = 2 + 1; println(y * y)
}
