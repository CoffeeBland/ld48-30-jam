package com.coffeebland.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.carto.Street;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.state.State;

import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class GameState extends State {
    public GameState() {
        super();

        getInputManager().listenTo(Control.MOVE_LEFT, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {

            }

            @Override
            public void onKeyUp() {

            }

            @Override
            public void onKeyIsDown() {

            }
        });
    }

    private Street currentStreet;
    private Set<Pedestrian> pedestrians;

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
}
