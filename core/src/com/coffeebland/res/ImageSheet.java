package com.coffeebland.res;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/23/14.
 */
public class ImageSheet implements Updateable {
    public ImageSheet(String ref, int framesX, int framesY) {
        texture = Images.get(ref);
        this.framesX = framesX;
        this.framesY = framesY;
        this.frameWidth = texture.getWidth() / framesX;
        this.frameHeight = texture.getHeight() / framesY;
    }

    int framesX, frameWidth, framesY, frameHeight;
    Texture texture;

    public void render(SpriteBatch batch, float x, float y, int imageX, int imageY, boolean flip) {
        batch.draw(texture, x, y, frameWidth, frameHeight, frameWidth * imageX, frameHeight * imageY, frameWidth, frameHeight, flip, false);
    }
    public void render(SpriteBatch batch, float x, float y, int imageX, int imageY, boolean flip, Color tint) {
        batch.setColor(tint);
        render(batch, x, y, imageX, imageY, flip);
        batch.setColor(Color.WHITE);
    }

    public void update(float delta) {}
}
