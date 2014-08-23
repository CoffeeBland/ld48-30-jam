package com.coffeebland.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
            FEET_DECAL = 4,
            FRAME_DECAL_X = -FRAME_WIDTH / 2,
            FRAME_STAND = 0,
            FRAME_WALK = 1,
            FRAME_RUN = 2;

    public static final float
            RUN_BREAKPOINT = 1,
            SLOW_RATE = 0.1f,
            SLOW_FACTOR = 1.1f,
            SPEED_WALK = 1,
            SPEED_RUN = 2;

    public Pedestrian(String skinRef, Color skinColor, String clothesRef, String hairRef, Color hairColor, float x, float y) {
        setSkin(skinRef, skinColor);
        setClothes(clothesRef);
        setHair(hairRef, hairColor);
        this.x = x;
        this.y = y;
    }

    private AnimatedImageSheet skin, clothes, hair;
    private Color skinColor, hairColor;
    private float x, y, speed;
    private boolean flip, isWalking;

    public void setSkin(String ref, Color skinColor) {
        skin = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, 12, true);
        this.skinColor = skinColor;
    }
    public void setClothes(String ref) {
        clothes = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, 12, true);
    }
    public void setHair(String ref, Color hairColor) {
        hair = new AnimatedImageSheet(ref, FRAME_WIDTH, FRAME_HEIGHT, 12, true);
        this.hairColor = hairColor;
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
    }


    public void runLeft() {
        isWalking = true;
        speed -= SPEED_RUN;
    }
    public void runRight() {
        isWalking = true;
        speed += SPEED_RUN;
    }

    @Override
    public void update(float delta) {
        skin.update(delta);
        clothes.update(delta);
        hair.update(delta);

        speed /= SLOW_FACTOR;
        if (Math.abs(speed) < SLOW_RATE) {
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
            skin.setFrameY(FRAME_RUN);
            clothes.setFrameY(FRAME_RUN);
            hair.setFrameY(FRAME_RUN);
        } else if (speed != 0) {
            skin.setFrameY(FRAME_WALK);
            clothes.setFrameY(FRAME_WALK);
            hair.setFrameY(FRAME_WALK);
        } else {
            skin.setFrameY(FRAME_STAND);
            clothes.setFrameY(FRAME_STAND);
            hair.setFrameY(FRAME_STAND);
        }
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {

        skin.render(batch, x + FRAME_DECAL_X - camera.getPosition(), y + FEET_DECAL, flip, skinColor);
        clothes.render(batch, x + FRAME_DECAL_X, y + FEET_DECAL, flip);
        hair.render(batch, x + FRAME_DECAL_X, y + FEET_DECAL, flip, hairColor);
    }
}
