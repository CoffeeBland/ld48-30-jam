package com.coffeebland.util;

/**
 * Created by dagothig on 8/23/14.
 */
public class Maybe<T> {
    public Maybe(T val) {
        this.val = val;
    }
    public Maybe() {}

    private T val;

    public boolean hasValue() {
        return val != null;
    }
    public T getValue() {
        return val;
    }
}
