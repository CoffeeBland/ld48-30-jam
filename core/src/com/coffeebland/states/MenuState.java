package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class MenuState extends State {
    public MenuState() {
        setBackgroundColor(Color.WHITE.cpy());
        bg = ColorUtil.whitePixel();
        bigFont = FontUtil.normalFont(72);
    }

    private Texture bg;
    private BitmapFont font;
    private BitmapFont bigFont;

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(new Color(0x75858AFF));
        batch.draw(bg, 12, 12, Gdx.graphics.getWidth()-24, Gdx.graphics.getHeight()-24);
        batch.setColor(Color.WHITE.cpy());
        bigFont.setColor(Color.WHITE.cpy());
        bigFont.draw(batch, "HotSpot", (Gdx.graphics.getWidth()/2)-165, Gdx.graphics.getHeight()-172);
    }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void onTransitionInStart() {
    }
    @Override
    public void onTransitionInFinish() {
    }

}