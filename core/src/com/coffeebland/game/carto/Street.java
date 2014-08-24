package com.coffeebland.game.carto;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.HotSpot;
import com.coffeebland.game.Camera;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.util.CameraRenderable;
import com.coffeebland.util.Maybe;
import com.coffeebland.util.Updateable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class Street implements Updateable, CameraRenderable {
    public static final int
            TILE_SIZE = 32,
            ROAD_WIDTH = 2,
            EARTH_WIDTH = 1,
            SIDEWALK_WIDTH = 1,
            OVERFLOW_WIDTH = 1;


    public static final float
            PEDESTRIANS_START_DISTANCE = (ROAD_WIDTH + EARTH_WIDTH) * TILE_SIZE + 6,
            PEDESTRIANS_END_DISTANCE = PEDESTRIANS_START_DISTANCE - 6 + SIDEWALK_WIDTH * TILE_SIZE;

    public static ImageSheet
            SIDEWALK_SHEET = new ImageSheet("sprites/sidewalk.png", TILE_SIZE, TILE_SIZE, false),
            ROAD_SHEET = new ImageSheet("sprites/road.png", TILE_SIZE, TILE_SIZE, false);

    public static enum RoadType {
        START,
        REGULAR,
        START_HORIZONTAL_CROSSING,
        HORIZONTAL_CROSSING,
        END_HORIZONTAL_CROSSING,
        VERTICAL_CROSSING,
        END;
    }

    public Street(float x, float y, float length, boolean isVertical, long seed) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.isVertical = isVertical;
        this.rand = new Random(seed);
    }

    private float x, y, length;
    private boolean isVertical;
    private Maybe<Texture> background = new Maybe<Texture>();
    private Random rand;

    public Set<Street> connectingStreets = new HashSet<Street>();
    private Street[] connectingStreetTiles;
    public Set<Building> buildings = new HashSet<Building>();

    private void renderSideWalk(Pixmap pixmap, int x, RoadType type) {
        int len;
        switch (type) {
            case START:
                len = 1 + ROAD_WIDTH + EARTH_WIDTH + OVERFLOW_WIDTH;
                for (int i = OVERFLOW_WIDTH; i < len; i++) {
                    if (i == OVERFLOW_WIDTH) {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 0, 1);
                    } else if (i >= len - 1) {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 2, 2);
                    } else {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 0, 2);
                    }
                }
                break;
            case REGULAR:
                SIDEWALK_SHEET.render(pixmap, x, OVERFLOW_WIDTH * TILE_SIZE, 0, 0);
                break;
            case START_HORIZONTAL_CROSSING:
                SIDEWALK_SHEET.render(pixmap, x, 0 * TILE_SIZE, 1, 0);
                SIDEWALK_SHEET.render(pixmap, x, OVERFLOW_WIDTH * TILE_SIZE, 1, 1);
                break;
            case END_HORIZONTAL_CROSSING:
                SIDEWALK_SHEET.render(pixmap, x, 0 * TILE_SIZE, 2, 0);
                SIDEWALK_SHEET.render(pixmap, x, OVERFLOW_WIDTH * TILE_SIZE, 2, 1);
                break;
            case VERTICAL_CROSSING:
                SIDEWALK_SHEET.render(pixmap, x, OVERFLOW_WIDTH * TILE_SIZE, 3, 0);
                break;
            case END:
                len = 1 + ROAD_WIDTH + EARTH_WIDTH + OVERFLOW_WIDTH;
                for (int i = OVERFLOW_WIDTH; i < len; i++) {
                    if (i == OVERFLOW_WIDTH) {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 3, 1);
                    } else if (i == len - 1) {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 2, 2);
                    } else {
                        SIDEWALK_SHEET.render(pixmap, x, i * TILE_SIZE, 3, 2);
                    }
                }
                break;
        }
    }
    private void renderRoad(Pixmap pixmap, int x, RoadType type) {
        int start;
        switch (type) {
            case REGULAR:
                start = 1 + OVERFLOW_WIDTH;
                for (int r = start; r < start + ROAD_WIDTH; r++) {
                    ROAD_SHEET.render(pixmap, x, r * TILE_SIZE, 0, 0);
                }
                start += ROAD_WIDTH;
                for (int e = start; e < start + EARTH_WIDTH; e++) {
                    ROAD_SHEET.render(pixmap, x, e * TILE_SIZE, 0, 3);
                }
                break;
            case START_HORIZONTAL_CROSSING:
                start = 1 + OVERFLOW_WIDTH;
                for (int r = start; r < start + ROAD_WIDTH; r++) {
                    ROAD_SHEET.render(pixmap, x, r * TILE_SIZE, 0, 1);
                }
                start += ROAD_WIDTH;
                for (int e = start; e < start + EARTH_WIDTH; e++) {
                    ROAD_SHEET.render(pixmap, x, e * TILE_SIZE, 0, 2);
                }
                break;
            case HORIZONTAL_CROSSING:
                ROAD_SHEET.render(pixmap, x, 0 * TILE_SIZE, 3, 0);
                ROAD_SHEET.render(pixmap, x, 1 * TILE_SIZE, 1, 0);
                start = 1 + OVERFLOW_WIDTH;
                for (int r = start; r < start + ROAD_WIDTH; r++) {
                    ROAD_SHEET.render(pixmap, x, r * TILE_SIZE, 0, 0);
                }
                start += ROAD_WIDTH;
                for (int e = start; e < start + EARTH_WIDTH; e++) {
                    ROAD_SHEET.render(pixmap, x, e * TILE_SIZE, 0, 3);
                }
                break;
            case END_HORIZONTAL_CROSSING:
                start = 1 + OVERFLOW_WIDTH;
                for (int r = start; r < start + ROAD_WIDTH; r++) {
                    ROAD_SHEET.render(pixmap, x, r * TILE_SIZE, 3, 1);
                }
                start += ROAD_WIDTH;
                for (int e = start; e < start + EARTH_WIDTH; e++) {
                    ROAD_SHEET.render(pixmap, x, e * TILE_SIZE, 3, 2);
                }
                break;
            case VERTICAL_CROSSING:
                start = 1 + OVERFLOW_WIDTH;
                for (int r = start; r < start + ROAD_WIDTH; r++) {
                    ROAD_SHEET.render(pixmap, x, r * TILE_SIZE, 1, 1);
                }
                start += ROAD_WIDTH;
                for (int e = start; e < start + EARTH_WIDTH; e++) {
                    ROAD_SHEET.render(pixmap, x, e * TILE_SIZE, 1, 2);
                }
                break;
        }
    }
    public void initTexture() {
        if (background.hasValue())
            return;

        SIDEWALK_SHEET.initPixmap("sprites/sidewalk.png");
        ROAD_SHEET.initPixmap("sprites/road.png");
        Pixmap pixmap = new Pixmap((int)length, (ROAD_WIDTH + EARTH_WIDTH + SIDEWALK_WIDTH + OVERFLOW_WIDTH) * TILE_SIZE, Pixmap.Format.RGBA8888);
        connectingStreetTiles = new Street[(int)Math.ceil(length / TILE_SIZE)];

        for (int i = 0; i < length; i+= TILE_SIZE) {
            RoadType type = RoadType.REGULAR;
            if (i == 0) {
                type = RoadType.START;
            } else if (i >= length - TILE_SIZE) {
                type = RoadType.END;
            } else {
                for (Street street : connectingStreets) {
                    float rel = getRelativePosition(street);
                    float pos = getPositionTile(rel);
                    float tilePos = pos * TILE_SIZE;
                    if (tilePos - TILE_SIZE * 2 == i) {
                        type = RoadType.START_HORIZONTAL_CROSSING;
                        connectingStreetTiles[i / TILE_SIZE] = street;
                    } else if (tilePos + TILE_SIZE * 2 == i) {
                        type = RoadType.END_HORIZONTAL_CROSSING;
                        connectingStreetTiles[i / TILE_SIZE] = street;
                    } else if (tilePos >= i - TILE_SIZE && tilePos <= i + TILE_SIZE) {
                        type = RoadType.HORIZONTAL_CROSSING;
                        connectingStreetTiles[i / TILE_SIZE] = street;
                    }
                }
            }

            if (type == RoadType.REGULAR) {
                if (rand.nextFloat() < 0.1f) {
                    type = RoadType.VERTICAL_CROSSING;
                }
            }

            renderRoad(pixmap, i, type);
            renderSideWalk(pixmap, i, type);
        }

        background = new Maybe<Texture>(new Texture(pixmap));
    }

    private float getRelativePosition(Street street) {
        if (street.isVertical() == isVertical()) {
            return Float.NaN;
        }

        return street.alignment();
    }
    private float getPositionTile(float pos) {
        return Math.round((pos - getStart()) / TILE_SIZE);
    }
    public Maybe<Street> getCurrentTile(float pos) {
        return new Maybe<Street>(connectingStreetTiles[(int)Math.floor((pos - getStart()) / TILE_SIZE)]);
    }
    public float getPosComingFrom(Street street) {
        float rel = getRelativePosition(street);
        float pos = getPositionTile(rel);
        float tilePos = pos * TILE_SIZE;
        return tilePos + getStart() + TILE_SIZE / 2;
    }

    public float getStartX() {
        return x;
    }
    public float getEndX() {
        return x + (isVertical ? 0 : length);
    }

    public float getStartY() {
        return y;
    }
    public float getEndY() {
        return y + (isVertical ? length : 0);
    }

    public float getStart() {
        return isVertical() ? getStartY() : getStartX();
    }
    public float getEnd() {
        return isVertical() ? getEndY() : getEndX();
    }

    public float alignment() {
        return isVertical() ? (getStartX() + getEndX()) / 2 : (getStartY() + getEndY()) / 2;
    }

    public boolean isVertical() {
        return isVertical;
    }

    private void renderBackground(SpriteBatch batch, Camera camera) {
        if (background.hasValue()) {
            Texture background = this.background.getValue();
            batch.draw(background, (getStart() - camera.getPosition()) * HotSpot.UPSCALE_RATE, 0 * HotSpot.UPSCALE_RATE, background.getWidth() * HotSpot.UPSCALE_RATE, background.getHeight() * HotSpot.UPSCALE_RATE);
        }
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
