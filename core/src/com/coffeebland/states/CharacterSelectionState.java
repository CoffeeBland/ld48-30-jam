package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;

/**
 * Created by dagothig on 8/23/14.
 */
public class CharacterSelectionState extends State {
    public CharacterSelectionState() {
        super();

        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);
    }

    private Texture bg;
    private BitmapFont font;

    @Override
    public boolean shouldBeReused() { return false; }

    @Override
    public void render(SpriteBatch batch) {
        font.setColor(Color.WHITE.cpy());
        String text = "Character Selection";
        float halfTextWidth = font.getBounds(text).width/2;
        font.draw(batch, text, (Gdx.graphics.getWidth()/2) - halfTextWidth, Gdx.graphics.getHeight()-60);

    }

    @Override
    public void update(float delta) {

    }

}
