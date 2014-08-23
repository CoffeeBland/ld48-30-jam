package com.coffeebland.carto;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Camera;
import com.coffeebland.util.CameraRenderable;
import com.coffeebland.util.Updateable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class Street implements Updateable, CameraRenderable {
    public static final float
            PEDESTRIANS_START_DISTANCE = 100,
            PEDESTRIANS_END_DISTANCE = 50;

    private double x, y, length;
    private boolean isVertical;

    public double getStartX() {
        return x;
    }
    public double getEndX() {
        return x + (isVertical ? 0 : length);
    }

    public double getStartY() {
        return y;
    }
    public double getEndY() {
        return y + (isVertical ? length : 0);
    }

    public boolean isVertical() {
        return isVertical;
    }

    public Set<Street> connectingStreets = new HashSet<Street>();
    public Set<Building> buildings = new HashSet<Building>();

    private void renderBackground(SpriteBatch batch, Camera camera) {
        // TODO: render background
    }
    @Override
    public void render(SpriteBatch batch, Camera camera) {
        renderBackground(batch, camera);
        for (Building building : buildings) {
            building.render(batch, camera);
        }
    }
    @Override
    public void update(float delta) {
        for (Building building: buildings) {
            building.update(delta);
        }
    }
}
