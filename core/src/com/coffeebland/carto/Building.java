package com.coffeebland.carto;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Camera;
import com.coffeebland.util.CameraRenderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/23/14.
 */
public class Building implements Updateable, CameraRenderable {
    private BuildingType type;
    private double position;
    private Street street;

    @Override
    public void render(SpriteBatch batch, Camera camera) {

    }

    @Override
    public void update(float delta) {

    }
}
