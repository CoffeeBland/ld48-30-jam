package com.coffeebland.game;

/**
 * Created by dagothig on 8/24/14.
 */
public enum Stat {
    HIPSTER("Hipster"),
    PUNK("Punk"),
    BUSINESS("Business"),
    POP("Pop"),
    GEEK("Geek");

    public static final int MAX_VALUE = 100;

    private Stat(String name) {
        setName(name);
    }

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
