package com.coffeebland.game;

import com.badlogic.gdx.graphics.Texture;
import com.coffeebland.res.Images;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/24/14.
 */
public enum Candidate {
    STICK_MAN("Stick Man", "sprites/candidates/candidate.png"),
    CHIX("Chix", "sprites/candidates/candidate.png");

    static {
        STICK_MAN.stats.put(Stat.HIPSTER, 75);
    }

    public static Candidate SELECTED_CANDIDATE = STICK_MAN;

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
