package com.coffeebland.game;

import com.badlogic.gdx.graphics.Texture;
import com.coffeebland.res.Images;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/24/14.
 */
public enum Candidate {
    BUSINESS("Catherine", "sprites/candidates/date_business.png"),
    GEEK("3L173", "sprites/candidates/date_geek.png"),
    HIPSTER("Lovecat", "sprites/candidates/date_hipster.png"),
    POP("xxxLillyxxx", "sprites/candidates/date_pop.png"),
    PUNK("Radical Pussy", "sprites/candidates/date_punk.png");

    static {
    }

    public static Candidate SELECTED_CANDIDATE = POP;

    private Candidate(String name, String imgRef) {
        this.name = name;
        img = Images.get(imgRef);
        stats = new HashMap<Stat, Integer>();
        for (Stat stat : Stat.values()) {
            stats.put(stat, 35);
        }
    }

    private Texture img;
    private String name;
    private Map<Stat, Integer> stats;

    public String getName() {
         return name;
    }

    public Texture getImage() {
        return img;
    }
}
