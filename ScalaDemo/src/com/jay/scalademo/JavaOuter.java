package com.jay.scalademo;

/**
 * Created by jay on 16/11/20.
 */
public class JavaOuter {
    class Inner {
        private void f() {
            System.out.println("a");
        }
        class InnerMost {
            public void g() {
                f();
            }
        }
    }

    public void h() {
        new Inner().f();
    }

    protected void m() {

    }
}
