package recfun

import scala.collection.mutable.Stack

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = (c, r) match {
    case (0, _) => 1
    case (c, r) if (c == r) => 1
    case (c, r) => pascal(c-1, r-1) + pascal(c, r-1)
  }
  
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceUtil2(charList: List[Char], parenStack: Stack[Char]): Boolean = {
      if (charList.isEmpty) {
        if (parenStack.isEmpty) true
        else false
      } else {
        val curChar = charList.head
        curChar match {
          case '(' => parenStack.push(curChar); balanceUtil2(charList.tail, parenStack)
          case ')' => (parenStack.isEmpty) match {
            case true => false
            case _ => parenStack.pop; balanceUtil2(charList.tail, parenStack)
          }
          case _ => balanceUtil2(charList.tail, parenStack)
        }
      }
    }

    def balanceUtil(charList: List[Char], parenStack: Stack[Char]): Boolean = {
      if (charList.isEmpty) {
        if (parenStack.isEmpty) true
        else false
      } else {
        val curChar = charList.head
        curChar match {
          case '(' => parenStack.push(curChar)
          case ')' => if (parenStack.isEmpty) return false
                        else parenStack.pop
          case _ => // Do nothing
        }
        balanceUtil(charList.tail, parenStack)
      }
    }

    balanceUtil(chars, new Stack[Char]())
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money < 0) 0
    else if (money == 0) 1
    else if (coins.isEmpty == true && money > 0) 0
    else countChange(money, coins.tail) + countChange(money - coins.head, coins)
  }

  }
