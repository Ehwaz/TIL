import scala.annotation.tailrec

/**
  * Created by Ehwaz on 2016-05-28.
  */
object Memo_Lect_1_7 extends App {
  println("Lecture 1.7: Tail Recursion")

  // Recursive function in scala must specify its return value type.
  @tailrec
  def myGcdRec(a: Int, b: Int): Int = if (b == 0) a else myGcdRec(b, a % b)

  def myFactIter(n: Int) = {
    var result = 1L;
    for (i <- 1 to n) {
      result *= i
    }
    result
  }

  def myFactRec(n: Int): Long = if (n == 0) 1L else n * myFactRec(n - 1)

  println(myFactRec(5))

  /*
  If a function calls itself as its last action, the function's stack frame can be used.
  This is called 'tail recursion'.
    - Tail recursive function is the functional form of a loop.
    - By using tail recursion, a function can avoid stack overflow.
    - In above functions, myGcdRec() is tail-recursive, but myFactRec() is not.
   */

  def myFactTailRec(n: Int): Long = {
    @tailrec
    def myFactTailRecUtil(n: Int, value: Long): Long = {
      if (n == 0) value
      else {
        myFactTailRecUtil(n - 1, n * value)
      }
    }
    myFactTailRecUtil(n, 1L)
  }

  println(myFactTailRec(5))


  def myFactTailRec2(n: Int): Long = {
    @tailrec
    def myFactTailRecUtil(n: Int, value: Long): Long = n match {
      case 0 => value
      case _ => myFactTailRecUtil(n - 1, n * value)
    }
    myFactTailRecUtil(n, 1L)
  }

  println(myFactTailRec2(5))
}
