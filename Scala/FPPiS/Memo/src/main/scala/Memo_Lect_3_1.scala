/**
  * Created by Ehwaz on 2016-06-12.
  */
object Memo_Lect_3_1 {
  def main(args: Array[String]) {
    println("Lecture 3.1: Class Hierarchies")

    val t1 = new NonEmpty(3, Empty, Empty)
    val t2 = t1.incl(4)

    println(t1)
    println(t2)
  }
}

/*
OOP languages implement dynamic method dispatch.
It means that the code invoked by a method call depends on the runtime type of the object that contains the method.
 */
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(x: IntSet): IntSet
}

// Below 2 classes implement IntSet as persistent data structure.
/*
// Instead of declaring Empty class, we can declare it singleton class by just using 'object' keyword.
class Empty extends IntSet {
  def incl(x: Int): IntSet  = new NonEmpty(x, new Empty, new Empty)
  def contains(x: Int): Boolean = false
  override def toString = "."
}
*/

object Empty extends IntSet {
  def incl(x: Int): IntSet  = new NonEmpty(x, Empty, Empty)
  def contains(x: Int): Boolean = false
  def union(x: IntSet): IntSet = x
  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left.incl(x), right)
    else if (x > elem) new NonEmpty(elem, left, right.incl(x))
    else this
  def contains(x: Int): Boolean =
    if (x < elem) left.contains(x)
    else if (x > elem) right.contains(x)
    else true
  def union(x: IntSet): IntSet = left.union(right).union(x).incl(elem)
  override def toString = "{" + left + elem + right + "}"
}