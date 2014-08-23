package com.coffeebland.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class ButtonUtil {

    public static void drawButton(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        Texture bg = ColorUtil.whitePixel();
        TextBounds bounds = font.getBounds(text);
        float border = 2;
        float padding = 10;
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

}
