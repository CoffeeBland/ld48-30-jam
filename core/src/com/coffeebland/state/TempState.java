package com.coffeebland.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by dagothig on 8/23/14.
 */
public class TempState extends State<String> {
    Texture text = new Texture("badlogic.jpg");
    public TempState() {

    }

    @Override
    public boolean shouldBeReused() {
        return false;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.draw(text, 0, 0);
    }

    @Override
    public void onTransitionInStart(String args) {
        System.out.println(args);
    }
    @Override
    public void onTransitionInFinish() {
        switchToState(TempState.class, Color.WHITE.cpy(), TRANSITION_LONG, "lolcats");
    }
}
