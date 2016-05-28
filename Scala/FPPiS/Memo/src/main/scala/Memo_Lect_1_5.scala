/**
  * Created by Ehwaz on 2016-05-28.
  */
object Memo_Lect_1_5 extends App{
  println("Lecture 1.5: Example: square roots with Newton's method")

  def myAbs(x: Double) = if (x < 0) -x else x

  def mySqrt(x: Double) = {
    def mySqrtUtil(guess: Double, x: Double): Double =
      if (isGoodEnough(guess, x)) guess
      else mySqrtUtil(improve(guess, x), x)

    // Using absolute value as termination condition
    // leads to incorrect answer(when x is small) or non-termination(when x is large)
    def isGoodEnough(guess: Double, x: Double) =
      myAbs(guess * guess - x) < 0.00001 * x

    def improve(guess: Double, x: Double) = (guess + x / guess) / 2

    mySqrtUtil(1, x)
  }

  println(mySqrt(2))
  println(mySqrt(4))
  println(mySqrt(1e-6))
  println(mySqrt(1e60))

  println(mySqrt(0.001))
  println(mySqrt(0.1e-20))
  println(mySqrt(1e20))
  println(mySqrt(1e50))
}
