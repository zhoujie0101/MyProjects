package com.jay.scalademo

/**
  * Created by jay on 2017/3/11.
  */
class Rational(n: Int, d: Int) {
  require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val nume: Int = n / g
  val denom: Int = d / g

  def this(n: Int) = this(n, 1)

  override def toString = nume +"/"+ denom

  def + (that: Rational): Rational =
    new Rational(
      this.nume * that.denom + that.nume * this.denom,
      this.denom * that.denom
    )

  def add(that: Rational): Rational =
    new Rational(
      this.nume * that.denom + that.nume * this.denom,
      this.denom * that.denom
    )

  def * (that: Rational): Rational =
    new Rational(
      this.nume * that.nume, this.denom * that.denom
    )

  def lessThan(that: Rational): Boolean =
    this.nume * that.denom < that.nume * this.denom

  def max(that: Rational): Rational =
    if (this.lessThan(that)) that else this

  private def gcd(a: Int, b: Int): Int = {
    val max = a.max(b)
    val min = a.min(b)
    if (min == 0) max else gcd(min, max % min)
  }
}

object Rational {
  def main(args: Array[String]) {
    val x = new Rational(1, 2)
    val y = new Rational(2, 3)

    println(x + y)
    println(x.+(y))
    println(x * y)
    println(x.*(y))
  }
}
