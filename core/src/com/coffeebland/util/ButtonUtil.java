package com.coffeebland.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.geom.Rectangle2D;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class ButtonUtil {
    static float border = 2;
    static float padding = 10;

    public static void drawButton(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        Texture bg = ColorUtil.whitePixel();
        TextBounds bounds = font.getBounds(text);
        float totalSpacing = (padding*2)+(border*2);

        // Border
        batch.setColor(Color.WHITE.cpy());
        batch.draw(bg, x, y, bounds.width+totalSpacing, bounds.height+totalSpacing);

        // Center
        batch.setColor(new Color(0x000000FF));
        batch.draw(bg, x+border, y+border, bounds.width+(padding*2), bounds.height+(padding*2));
        batch.setColor(Color.WHITE.cpy());

        // Text
        font.setColor(Color.WHITE.cpy());
        font.draw(batch, text, x+border+padding, y+bounds.height+border+padding);
    }

    public static void drawButtonCentered(SpriteBatch batch, BitmapFont font, String text, float y) {
        TextBounds bounds = font.getBounds(text);
        float x = (Gdx.graphics.getWidth()/2) - (bounds.width/2) - 12;
        drawButton(batch, font, text, x, y);
    }

    public static Rectangle2D.Float getButtonBounds(BitmapFont font, String text, float x, float y) {
        TextBounds bounds = font.getBounds(text);
        float totalSpacing = (padding*2)+(border*2);

        return new Rectangle2D.Float(x, y, bounds.width+totalSpacing, bounds.height+totalSpacing);
    }

    public static Rectangle2D.Float getButtonBoundsCentered(BitmapFont font, String text, float y) {
        TextBounds bounds = font.getBounds(text);
        float x = (Gdx.graphics.getWidth() / 2) - (bounds.width / 2) - 12;
        return getButtonBounds(font, text, x, y);
    }

}
