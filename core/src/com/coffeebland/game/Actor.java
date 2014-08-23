package com.coffeebland.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by dagothig on 8/23/14.
 */
public abstract class Actor {
    public abstract void render(SpriteBatch batch, Camera camera);
    public abstract void update(long delta);
}
