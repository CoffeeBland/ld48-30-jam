package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.HotSpot;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.res.Images;
import com.coffeebland.state.State;

/**
 * Created by dagothig on 8/24/14.
 */
public class GameOverState extends State<String> {
    public GameOverState() {
        InputDispatcher.OnKeyListener listener = new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                switchToState(LogoState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM);
            }

            @Override
            public void onKeyUp() {

            }

            @Override
            public void onKeyIsDown() {

            }
        };
        getInputManager().listenTo(Control.SKIP_INTRO1, listener);
        getInputManager().listenTo(Control.SKIP_INTRO2, listener);
        getInputManager().listenTo(Control.SKIP_INTRO3, listener);
    }

    private Texture bg;
    private int beforeSwitch = 0;

    @Override
    public boolean shouldBeReused() {
        return false;
    }

    @Override
    public String musicRef() {
        return "music/gameover.mp3";
    }

    @Override
    public void render(SpriteBatch batch) {
        float x = Gdx.graphics.getWidth() / 2 - (bg.getWidth() * HotSpot.UPSCALE_RATE) / 2;
        float y = Gdx.graphics.getHeight() / 2 - (bg.getHeight() * HotSpot.UPSCALE_RATE) / 2;

        batch.draw(bg, x, y, bg.getWidth() * HotSpot.UPSCALE_RATE, bg.getHeight() * HotSpot.UPSCALE_RATE);
    }

    @Override
    public void update(float delta) {
        if (beforeSwitch > 0) {
            beforeSwitch -= delta;

            if (beforeSwitch <= 0) {
                beforeSwitch = 0;
                switchToState(LogoState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM);
            }
        }
    }

    @Override
    public void onTransitionInStart() {
        this.onTransitionInStart("sprites/gameover/generic.png");
    }
    @Override
    public void onTransitionInStart(String backRef) {
        bg = Images.get(backRef);
        beforeSwitch = 14000;
    }
}
