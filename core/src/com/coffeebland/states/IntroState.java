package com.coffeebland.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class IntroState extends State {

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void onTransitionInStart() {
    }
    @Override
    public void onTransitionInFinish() {
        switchToState(IntroState.class, Color.WHITE.cpy(), TRANSITION_SHORT, "lolcats");
    }

}
