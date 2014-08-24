package com.coffeebland.game;

import com.badlogic.gdx.Gdx;
import com.coffeebland.HotSpot;

/**
 * Created by dagothig on 8/23/14.
 */
public class Camera {
    private float position;
    public float getPosition() {
        return position;
    }
    public void setPosition(float position) {
        this.position = position - ((Gdx.graphics.getWidth() / HotSpot.UPSCALE_RATE) / 2);
    }
}
