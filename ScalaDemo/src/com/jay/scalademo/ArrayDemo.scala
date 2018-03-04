package com.jay.scalademo

import scala.collection.mutable
import scala.io.Source

/**
  * Created by jay on 2017/3/7.
  */

class ArrayDemo {

  val greetStrings = new Array[String](3)

  greetStrings(0) = "Hello"
  greetStrings(1) = ","
  greetStrings(2) = "world!"

  for (i <- 0 to 2)
    print(greetStrings(i))

  val list = List("a", "b")
  val list2 = List("c", "d")
  val list3 = list:::list2

  val par = (10, "a")
  print(par.toString())
  Source.fromFile(new java.io.File(("")))

  val amap = mutable.Map[Int, String]()
  amap += (1 -> "a")
}