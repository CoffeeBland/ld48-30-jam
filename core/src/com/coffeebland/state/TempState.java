package com.coffeebland.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by dagothig on 8/23/14.
 */
public class TempState extends State<String> {
    @Override
    public boolean shouldBeReused() {
        return false;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void onTransitionInStart(String args) {
        System.out.println(args);
    }
    @Override
    public void onTransitionInFinish() {
        switchToState(TempState.class, Color.WHITE.cpy(), TRANSITION_SHORT, "lolcats");
    }
}
