package com.coffeebland.res;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.HotSpot;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/23/14.
 */
public class ImageSheet implements Updateable {
    public ImageSheet(String ref, int frameWidth, int frameHeight, boolean useScaling) {
        texture = Images.get(ref);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.framesX = texture.getWidth() / frameWidth;
        this.framesY = texture.getHeight() / frameHeight;
        this.useScaling = useScaling;
    }

    int framesX, frameWidth, framesY, frameHeight;
    float scale = 1;
    Texture texture;
    boolean useScaling;

    public int getFrameWidth() {
        return frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void render(SpriteBatch batch, float x, float y, int imageX, int imageY, boolean flip) {
        if (useScaling) {
            batch.draw(texture,
                    x * HotSpot.UPSCALE_RATE, y * HotSpot.UPSCALE_RATE,
                    frameWidth * HotSpot.UPSCALE_RATE * scale, frameHeight * HotSpot.UPSCALE_RATE * scale,
                    frameWidth * imageX,
                    frameHeight * imageY,
                    frameWidth, frameHeight,
                    flip, false
            );
        } else {
            batch.draw(texture,
                    x, y,
                    frameWidth * scale, frameHeight * scale,
                    frameWidth * imageX,
                    frameHeight * imageY,
                    frameWidth, frameHeight,
                    flip, false
            );
        }
    }
    public void render(SpriteBatch batch, float x, float y, int imageX, int imageY, boolean flip, Color tint) {
        batch.setColor(tint);
        render(batch, x, y, imageX, imageY, flip);
        batch.setColor(Color.WHITE);
    }

    public void update(float delta) {}
}
