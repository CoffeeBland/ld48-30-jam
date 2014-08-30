package com.coffeebland.game.carto;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Camera;
import com.coffeebland.util.CameraRenderable;
import com.coffeebland.util.Updateable;

import java.util.Random;

/**
 * Created by dagothig on 8/23/14.
 */
public class Building implements Updateable, CameraRenderable {
    protected static enum BuildingPart {
        WALL_LEFT, WALL_RIGHT, DOOR, BASE(3), MID(2), ROOF(2);

        private int tH;
        private int getTileHeight() {
            return tH;
        }

        private BuildingPart(int tH) {
            this.tH = tH;
        }
        private BuildingPart() {

        }
    }

    protected static class TileDefinition {
        protected TileDefinition(int tX, int tY, int tW, int tH, BuildingPart... parts) {
            this.tX = tX;
            this.tY = tY;
            this.tW = tW;
            this.tH = tH;
            this.parts = parts;
        }

        protected int tX, tY, tW, tH;
        protected BuildingPart[] parts;
        protected boolean isPart(BuildingPart part) {
            for (BuildingPart p : parts) {
                if (p == part) {
                    return true;
                }
            }
            return false;
        }
    }

    protected static final TileDefinition[] TILE_DEFINITIONS = {
        new TileDefinition(0, 0, 2, 4, BuildingPart.BASE, BuildingPart.WALL_LEFT, BuildingPart.DOOR)
    };

    public static void renderBuilding(Pixmap target, int tilePosition, int tileWidth, Random random) {
        int extraFloors = (int)Math.sqrt(random.nextFloat() * 2);
        int doors = Math.max(random.nextInt(tileWidth / 6), 1);
        int tileHeight = BuildingPart.BASE.getTileHeight() + BuildingPart.MID.getTileHeight() * extraFloors +
        boolean[][] tiles = new boolean[tileWidth][tileHeight];

        for (int i = 0; o)

        for (int f = 0; f < floors; f++) {
            if (tileWidth == 2) {

            }
        }
    }

    private BuildingType type;
    private float position;
    private Street street;

    @Override
    public void render(SpriteBatch batch, Camera camera) {

    }

    @Override
    public void update(float delta) {

    }
}
