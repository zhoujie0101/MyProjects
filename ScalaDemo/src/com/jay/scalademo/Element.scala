package com.jay.scalademo

/**
  * Created by jay on 2017/3/12.
  */
abstract class Element {
  def contents: Array[String]

  val height: Int = this.contents.length

  val width: Int =
    if (height == 0) 0 else this.contents(0).length

  def above(that: Element): ArrayElement =
    new ArrayElement(this.contents ++ that.contents)

  def beside(that: Element): Element = {
    val contents = new Array[String](this.contents.length)
    for (i <- this.contents.indices) {
      contents(i) = this.contents(i) + that.contents(i)
    }
    new ArrayElement(contents)
  }

  def beside2(that: Element): Element = {
    new ArrayElement(
      for (
        (line1, line2) <- this.contents zip that.contents
      ) yield line1 + line2
    )
  }

  override def toString: String = this.contents mkString "\n"
}

class ArrayElement(conts: Array[String]) extends Element {
  override def contents: Array[String] = conts
}

class ArrayElement2(val contents: Array[String]) extends Element

class LineElements(s: String) extends Element {
  val contents = Array(s)
  override val width = s.length
  override val height = 1
}

class UniformElement(val ch: Char, override val width: Int, override val height: Int) extends Element {
  private val line = ch.toString * width
  def contents = Array.fill(height)(line)
}

object Element {
  def elem(contents: Array[String]): Element = new ArrayElement(contents)

  def elem(chr: Char, width: Int, height: Int): Element = new UniformElement(chr, width, height)

  def elem(line: String): Element = new LineElements(line)
}
