package com.coffeebland.res;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by dagothig on 8/23/14.
 */
public class AnimatedImageSheet extends ImageSheet {
    public AnimatedImageSheet(String ref, int frameWidth, int frameHeight, int fps, boolean loop) {
       super(ref, frameWidth, frameHeight);
        this.loop = loop;
        setFps(fps);
    }

    boolean loop;
    int fps, frameX = 0, frameY = 0;
    float frameLength, durationRemaining;

    public int getFrameX() {
        return frameX;
    }
    public void setFrameX(int frameX) {
        this.frameX = frameX;
        durationRemaining = frameLength;
    }
    public int getFrameY() {
        return frameY;
    }
    public void setFrameY(int frameY) {
        this.frameY = frameY;
    }

    public void setFps(int fps) {
        frameLength = 1000 / fps;
        this.fps = fps;
    }

    public void render(SpriteBatch batch, float x, float y, boolean flip) {
        render(batch, x, y, frameX, frameY, flip);
    }
    public void render(SpriteBatch batch, float x, float y, boolean flip, Color tint) {
        render(batch, x, y, frameX, frameY, flip, tint);
    }

    public void update(float delta) {
        durationRemaining -= delta;
        while (durationRemaining < 0) {
            if (loop || frameX < framesX) {
                durationRemaining += frameLength;
                frameX = (frameX + 1) % framesX;
            }
        }
    }
}
