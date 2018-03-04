package com.jay.scalademo

/**
  * Created by jay on 2017/3/12.
  */

class Point(val x: Int, val y: Int)

trait Rectangular {
  def topLeft: Point
  def bottomRight: Point
  def left = topLeft.x
  def right = bottomRight.x
  def width = left - right
}

class Rectanger(val topLeft: Point, val bottomRight: Point) extends Rectangular {

}

object Test {

  def main(args: Array[String]) {
    val rec: Rectanger = new Rectanger(new Point(0, 0), new Point(1, 1))
    println(rec.left)
  }
}
