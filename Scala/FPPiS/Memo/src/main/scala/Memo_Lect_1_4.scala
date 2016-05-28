/**
  * Created by Ehwaz on 2016-05-28.
  */
object Memo_Lect_1_4 extends App {
  println("Lecture 1.4: Contiaionals and Value Definitions")
  /*
  Difference between 'val' and 'def'
    - def: "by-name". Right hand side is evalutated on each use.
    - val: "by-value" RHS is evaluated at the point of the definition itself.
   */
  def loop: Boolean = loop
  def x_def = loop              // OK.
  //val x_val = loop              // loop is evalutated; leads to infinite loop.

  /*
  Implement (x && y) and or(x || y) without &&, ||
   */
  def myAnd(x:Boolean, y: Boolean): Boolean = if (x == true) if (y == true) true else false else false
  // Example from the lecture. uses '=>' operator for call-by-name parameter.
  def betterAnd(x: Boolean, y: => Boolean) = if (x) y else false
  def betterOr(x: Boolean, y: => Boolean) = if (x) true else y
  betterAnd(false, loop)
  betterOr(true, loop)
}
