package com.coffeebland.util;

/**
 * Created by kiasaki on 8/24/14.
 */
public class Tuple<A,B> {
    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }
    public B getB() {
        return b;
    }
}
