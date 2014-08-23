package com.coffeebland.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by dagothig on 8/23/14.
 */
public class Camera {
    private float position;
    public float getPosition() {
        return position;
    }
    public void setPosition(float position) {
        this.position = position - (Gdx.graphics.getWidth() / 2);
    }
}
