package com.coffeebland.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class ColorUtil {

    public static Texture whitePixel() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.drawPixel(0, 0, 0xFFFFFFFF);
        return new Texture(bgPixmap);
    }

}
