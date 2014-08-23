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
    private Pixmap blackPixel;
    private Texture blackPixelText;

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(blackPixelText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void onTransitionInStart() {
        blackPixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        blackPixel.drawPixel(0, 0, 0x000000);
        blackPixelText = new Texture(blackPixel);
    }
    @Override
    public void onTransitionInFinish() {
        switchToState(LogoState.class, Color.BLACK.cpy(), TRANSITION_LONG);
    }

}
