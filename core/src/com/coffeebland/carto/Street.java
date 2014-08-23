package com.coffeebland.carto;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Actor;
import com.coffeebland.game.Camera;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class Street extends Actor {
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
    public void update(long delta) {
        for (Building building: buildings) {
            building.update(delta);
        }
    }
}
