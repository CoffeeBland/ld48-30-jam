package com.coffeebland.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.carto.Street;
import com.coffeebland.res.AnimatedImageSheet;
import com.coffeebland.util.CameraRenderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/23/14.
 */
public class Pedestrian implements Updateable, CameraRenderable {
    public static final int
            FRAME_WIDTH = 32,
            FRAME_HEIGHT = 64,
            FEET_DECAL = -4,
            FRAME_DECAL_X = -FRAME_WIDTH / 2,
            FRAME_STAND = 0,
            FRAME_RUN = 1,
            FRAME_RUN_CELL = 2,
            FRAME_WALK = 3,
            FRAME_WALK_CELL = 4,
            CYCLE_FRAMERATE = 20;

    public static final float
            RUN_BREAKPOINT = 4f,
            SLOW_RATE = 0.2f,
            SLOW_FACTOR = 1.03f,
            SPEED_WALK = 0.15f,
            SPEED_RUN = 0.2f;

    public Pedestrian(String skinRef, Color skinColor, String clothesRef, String hairRef, Color hairColor, float x, float y) {
        setSkin(skinRef, skinColor);
        setClothes(clothesRef);
        setHair(hairRef, hairColor);
        this.x = x;
        this.y = y;

        cell = new AnimatedImageSheet("sprites/character/cell.png", FRAME_WIDTH, FRAME_HEIGHT, CYCLE_FRAMERATE, true, true);
    }

    private AnimatedImageSheet skin, clothes, hair, cell;
    private Color skinColor, hairColor;
    private float x, y, speed, yBeforeTransition;
    private boolean flip, isWalking, holdCell = false;
    private int animTransitionDuration = 0, animTransitionRemaining = 0;
    private boolean isLeaving;

    public boolean isHoldingCell() {
        return holdCell;
    }
    public void raiseCell() {
        holdCell = true;
    }
    public void lowerCell() {
        holdCell = false;
    }

    public void setSkin(String ref, Color skinColor) {
        skin = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, CYCLE_FRAMERATE, true, true);
        this.skinColor = skinColor;
    }
    public void setClothes(String ref) {
        clothes = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, CYCLE_FRAMERATE, true, true);
    }
    public void setHair(String ref, Color hairColor) {
        hair = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, CYCLE_FRAMERATE, true, true);
        this.hairColor = hairColor;
    }

    public Color getSkinColor() {
        return skinColor;
    }

    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

    public void setFps(int fps) {
        skin.setFps(fps);
        clothes.setFps(fps);
        hair.setFps(fps);
        cell.setFps(fps);
    }
    public void setFrameX(int frameX) {
        skin.setFrameX(frameX);
        clothes.setFrameX(frameX);
        hair.setFrameX(frameX);
        cell.setFrameX(frameX);
    }
    public void setFrameY(int frameY) {
        skin.setFrameY(frameY);
        clothes.setFrameY(frameY);
        hair.setFrameY(frameY);
        cell.setFrameY(frameY);
    }

    public void runLeft() {
        isWalking = true;
        speed -= SPEED_RUN;
    }
    public void runRight() {
        isWalking = true;
        speed += SPEED_RUN;
    }

    public void animEnter(int duration) {
        animTransitionRemaining = animTransitionDuration = duration;
        isLeaving = false;
        speed = 0;
        yBeforeTransition = y;
        setFps(CYCLE_FRAMERATE);
    }
    public void animLeave(int duration) {
        animTransitionRemaining = animTransitionDuration = duration;
        isLeaving = true;
        speed = 0;
        yBeforeTransition = y;
        setFps(CYCLE_FRAMERATE);
    }

    public void updateAnims(float delta) {
        skin.update(delta);
        clothes.update(delta);
        hair.update(delta);
        cell.update(delta);
    }

    @Override
    public void update(float delta) {
        updateAnims(delta);

        if (animTransitionRemaining > 0) {
            animTransitionRemaining -= delta;

            setFrameY(FRAME_WALK);
            float scale = (animTransitionRemaining / (float)animTransitionDuration);
            if (isLeaving)
                scale = 1 - scale;
            y = yBeforeTransition + scale * Street.TILE_SIZE;

            if (animTransitionRemaining <= 0) {
                animTransitionRemaining = 0;
                y = yBeforeTransition;
            }
        } else {

            speed /= SLOW_FACTOR;
            if (Math.abs(speed) < SLOW_RATE && !isWalking) {
                speed = 0;
            } else {
                if (speed < 0) {
                    if (!isWalking)
                        speed += SLOW_RATE;
                    flip = true;
                } else if (speed > 0) {
                    if (!isWalking)
                        speed -= SLOW_RATE;
                    flip = false;
                }
            }
            isWalking = false;

            x += speed;

            if (Math.abs(speed) > RUN_BREAKPOINT) {
                if (holdCell) {
                    setFrameY(FRAME_RUN_CELL);
                } else {
                    setFrameY(FRAME_RUN);
                }
                setFps(CYCLE_FRAMERATE);
            } else if (speed != 0) {
                if (holdCell) {
                    setFrameY(FRAME_WALK_CELL);
                } else {
                    setFrameY(FRAME_WALK);
                }
                setFps(CYCLE_FRAMERATE);
            } else {
                setFrameX(0);
                setFrameY(FRAME_STAND);
                setFps(0);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        skin.render(batch, x + FRAME_DECAL_X - camera.getPosition(), y + FEET_DECAL, flip, skinColor);
        clothes.render(batch, x + FRAME_DECAL_X - camera.getPosition(), y + FEET_DECAL, flip);
        hair.render(batch, x + FRAME_DECAL_X - camera.getPosition(), y + FEET_DECAL, flip, hairColor);
        cell.render(batch, x + FRAME_DECAL_X - camera.getPosition(), y + FEET_DECAL, flip);
    }
}
