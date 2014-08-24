package com.coffeebland.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class IntroState extends State<IntroState.IntroStateInfo> {
    Pedestrian character;

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
    public void onTransitionInStart(IntroStateInfo info) {
        character = info.character;
    }
    @Override
    public void onTransitionInStart() {
        if (character == null) {
            throw new RuntimeException("Character must be passed to Intro State");
        }
    }
    @Override
    public void onTransitionInFinish() {
        switchToState(IntroState.class, Color.WHITE.cpy(), TRANSITION_SHORT, "lolcats");
    }

    public static class IntroStateInfo {
        public IntroStateInfo(Pedestrian character) {
            this.character = character;
        }

        public Pedestrian character;
    }
}
