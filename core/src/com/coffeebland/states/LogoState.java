package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class LogoState extends State {
    public static String LOGO_PATH = "coffeebland.png";

    public  LogoState() {
        setBackgroundColor(Color.WHITE.cpy());
    }

    private float imgX = 0;
    private float imgY = 0;
    private long timeIn = 0;
    private Texture img;

    @Override
    public void update(float delta) {
        timeIn += delta;
        if (timeIn > 4000) {
            switchToState(MenuState.class, Color.WHITE.cpy(), TRANSITION_LONG);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (img != null)
            batch.draw(img, imgX, imgY);
    }

    @Override
    public void onTransitionInStart() {
        timeIn = 0;
        if (img == null) {
            img = new Texture(Gdx.files.internal(LOGO_PATH));
            imgX = (Gdx.graphics.getWidth() / 2) - 256; // Half the logo
            imgY = (Gdx.graphics.getHeight() / 2) - 256; // Half the logo
        }
    }

    @Override
    public void onTransitionInFinish() {
    }

}
