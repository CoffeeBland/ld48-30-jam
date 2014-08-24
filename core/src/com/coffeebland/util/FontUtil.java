package com.coffeebland.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class FontUtil {

  public static BitmapFont normalFont(int size) {
    FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal("sprites/font.ttf"));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = size;
    BitmapFont font = ftfg.generateFont(parameter);
    ftfg.dispose();
    return font;
  }

  public static BitmapFont normalFont() {
    return normalFont(18);
  }

}
