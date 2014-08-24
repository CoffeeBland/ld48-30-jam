package com.coffeebland.game;

import com.badlogic.gdx.Gdx;
import com.coffeebland.HotSpot;
import com.coffeebland.game.carto.Street;
import com.coffeebland.util.Maybe;

/**
 * Created by dagothig on 8/23/14.
 */
public class Camera {
    private Maybe<Street> currentStreet = new Maybe<Street>();
    private float position;
    public float getPosition() {
        return position;
    }
    public void setPosition(float position) {
        float min = Integer.MIN_VALUE,
            max = Integer.MAX_VALUE;
        if (currentStreet.hasValue()) {
            min = currentStreet.getValue().getStart();
            max = currentStreet.getValue().getEnd() - Gdx.graphics.getWidth() / HotSpot.UPSCALE_RATE;
        }
        this.position = Math.max(min, Math.min(max,
                position - ((Gdx.graphics.getWidth() / HotSpot.UPSCALE_RATE) / 2)
        ));
    }

    public void setCurrentStreet(Street street) {
        currentStreet = new Maybe<Street>(street);
    }
}
