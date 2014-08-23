package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class MenuState extends State {
    private Texture bg;

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(new Color(117, 133, 138, 255));
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void onTransitionInStart() {
        if (bg == null) {
            Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            bgPixmap.drawPixel(0, 0, 0xFFFFFF);
            bg = new Texture(bgPixmap);
        }
    }
    @Override
    public void onTransitionInFinish() {
    }

}