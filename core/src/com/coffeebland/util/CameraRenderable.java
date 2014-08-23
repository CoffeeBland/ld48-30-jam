package com.coffeebland.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Camera;

/**
 * Created by dagothig on 8/23/14.
 */
public interface CameraRenderable {
    public void render(SpriteBatch batch, Camera camera);
}
