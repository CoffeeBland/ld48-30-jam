package com.coffeebland.res;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.util.Maybe;

import java.util.*;

/**
 * Created by dagothig on 8/23/14.
 */
public class AnimatedImageSheet extends ImageSheet {
    public AnimatedImageSheet(String ref, int frameWidth, int frameHeight, int fps, boolean loop, boolean useScaling) {
       super(ref, frameWidth, frameHeight, useScaling);
        this.loop = loop;
        setFps(fps);
    }

    boolean loop, isQueuedFrame = false;
    int fps, frameX = 0, frameY = 0, singleFrame = -1;
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
        if (fps <= 0) {
            this.fps = 0;
        } else {
            frameLength = 1000 / fps;
            this.fps = fps;
        }
    }

    public void render(SpriteBatch batch, float x, float y, boolean flip) {
        render(batch, x, y, frameX, frameY, flip);
    }
    public void render(SpriteBatch batch, float x, float y, boolean flip, Color tint) {
        render(batch, x, y, frameX, frameY, flip, tint);
    }

    private Maybe<Queue<Integer>> queuedFrames = new Maybe<Queue<Integer>>();
    public void setSingleFrame(int frame) {
        singleFrame = frame;

        if (!isQueuedFrame && frame != -1)
            frameX = singleFrame;
    }
    public void queueFrame(int frame) {
        if (!isQueuedFrame) {
            frameX = frame;
            isQueuedFrame = true;
            return;
        }

        if (!queuedFrames.hasValue())
            queuedFrames = new Maybe<Queue<Integer>>(new ArrayDeque<Integer>());

        queuedFrames.getValue().add(frame);
    }
    public void clearQueue() {
        if (queuedFrames.hasValue())
            queuedFrames = new Maybe<Queue<Integer>>();
    }

    public void update(float delta) {
        if (fps != 0) {
            durationRemaining -= delta;
            while (durationRemaining < 0) {
                durationRemaining += frameLength;

                if (queuedFrames.hasValue()) {
                    int queuedFrame = queuedFrames.getValue().remove();
                    if (queuedFrames.getValue().isEmpty())
                        queuedFrames = new Maybe<Queue<Integer>>();
                    frameX = queuedFrame;
                    isQueuedFrame = true;
                } else if (!isQueuedFrame) {
                    if (singleFrame != -1) {
                        frameX = singleFrame;
                    } else if (loop || frameX < framesX - 1) {
                        frameX = (frameX + 1) % framesX;
                    }
                    isQueuedFrame = false;
                } else {
                    isQueuedFrame = false;
                }
            }
        }
    }
}
