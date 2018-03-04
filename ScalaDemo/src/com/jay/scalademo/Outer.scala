package com.jay.scalademo

/**
  * Created by jay on 16/11/20.
  */
class Outer {
  class Inner {
    private def f() = {println("a")}
    class InnerMost {
      f()
    }
  }

//  (new Inner).f()  error
}

package P {
  class Super {
    protected def f(): Unit = {
      println("b")
    }
  }
  class Sub extends Super {
    f()
  }

  class other {
//    (new Super).f()  error
  }
}
