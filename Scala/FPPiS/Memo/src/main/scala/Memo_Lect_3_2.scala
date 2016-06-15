/**
  * Created by Ehwaz on 2016-06-12.
  */
object Memo_Lect_3_2 {

}

// Just like Java, a class can only have one superclass.
// So if there's need to declare multiple superclasses, use 'trait'.
// Classes, objects and traits can inherit from at most one class but arbitrary many traits.
// ex) class Square extends Shape with Planar with Movable...

// Difference of traits with Java interface:

trait Planar {
  def height: Int
  def width: Int
  def surface = height * width
}
