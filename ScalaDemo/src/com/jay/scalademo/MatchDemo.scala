package com.jay.scalademo

/**
  * Created by jay on 2017/3/11.
  */
object MatchDemo {
  def main(args: Array[String]) {
    val a = "c"
    a match {
      case "a" => println("a")
      case "b" => println("b")
      case _ => println("unknown")
    }
  }

  def test() = {
    for(
      a <- 1 to 10
      if a == 1
    ) yield a
  }
}
