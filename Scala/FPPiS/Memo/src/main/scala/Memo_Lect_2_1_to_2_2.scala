import scala.annotation.tailrec

/**
  * Created by Ehwaz on 2016-06-05.
  */
object Memo_Lect_2_1_to_2_2 {
  println("Lecture 2.1: Higher-Order functions")

  // Examples for Higher-Order function. (functions as first-class citizens)
  def sumFromAtoB(f: Int => Int, a: Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sumFromAtoB(f, a + 1, b)

  def id(x: Int): Int = x
  def cube(x: Int): Int = x * x * x
  def fact(x: Int): Int = if (x == 0) 1 else fact(x - 1)

  def sumInts(a: Int, b: Int) = sumFromAtoB(id, a, b)
  def sumCubes(a: Int, b: Int) = sumFromAtoB(cube, a, b)
  def sumFact(a: Int, b: Int) = sumFromAtoB(fact, a, b)

  // Syntax of anonymous function
  val anoFunc = (x: Int) => x * x * x
  def sumCubes_3(a: Int, b: Int) = sumFromAtoB(anoFunc, a, b)

  // Exercize: tail-recursive version of sum
  def sumFromAtoBTailRec(f: Int => Int, a: Int, b: Int): Int = {
    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, acc + f(a))
    }
    loop(a, 0)
  }

  println("Lecture 2.2: Currying")
  // A function takes a function as input and returns another function as result.
  def sumFromAtoB_2(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a + 1, b)
    sumF
  }
  // Removing common parameters in function definition
  def sumInts_2 = sumFromAtoB_2(id)
  def sumCubes_2 = sumFromAtoB_2(cube)
  def sumFact_2 = sumFromAtoB_2(fact)
  // Then there's no need to define sumInt_2, sumCubes_2, etc.. in the first place.
  // Instead of calling sumInts_2(3, 5), Just call a function like sumFromAtoB_2(id)(a, b)!

  // This style of definition and function application is called currying.
  // Scala provides syntactic sugar for currying:
  def sumFromAtoB_3(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 0 else f(a) + sumFromAtoB_3(f)(a + 1, b)
  }

  // Functional types are associate to the right.
  // ex) "Int => Int => Int" is equivalent to Int => (Int => Int).

  def productFromAtoB(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1 else f(a) * productFromAtoB(f)(a + 1, b)
  }
  def factorialUsingCurrying(n: Int): Int = productFromAtoB(id)(1, n)

  def executeFromAtoB(combine: (Int, Int) => Int, execute: Int => Int, baseValue: Int)(a: Int, b: Int): Int = {
    if (a > b) baseValue
    else combine(execute(a), executeFromAtoB(combine, execute, baseValue)(a + 1, b))
  }
  def productFromAtoB_2(f: Int => Int)(a: Int, b: Int): Int = {
    executeFromAtoB((x, y) => x * y, f, 1)(a, b)
  }
  def factorialUsingCurrying_2(n: Int): Int = productFromAtoB_2(id)(1, n)

  def main(args: Array[String]) {
    val result = sumFromAtoBTailRec(x => x * x, 3, 5)
    println(result == 50)

    val result_2 = sumFromAtoB_2(x => x * x)(3, 5)
    println(result_2 == 50)

    println(factorialUsingCurrying(4))
    println(factorialUsingCurrying_2(4))
  }
}
