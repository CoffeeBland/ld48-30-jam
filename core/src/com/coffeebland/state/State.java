package com.coffeebland.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by dagothig on 8/23/14.
 */
public abstract class State<StateArgs> {
    public static final long TRANSITION_SHORT = 250;
    public static final long TRANSITION_MEDIUM = 500;
    public static final long TRANSITION_LONG = 1000;

    public State() {}

    private StateManager stateManager;
    private Color backgroundColor;

    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength, Object switchArgs) {
        stateManager.switchToState(stateType, transitionColor, transitionLength, switchArgs);
    }
    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength) {
        stateManager.switchToState(stateType, transitionColor, transitionLength);
    }

    public Color getBackgroundColor() {
        if (backgroundColor == null) {
            backgroundColor = Color.BLACK.cpy();
        }
        return backgroundColor;
    }
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public boolean shouldBeReused() { return false; }
    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public void onTransitionInStart(StateArgs args) {}
    public void onTransitionInStart() {}
    public void onTransitionInFinish() {}
    public void onTransitionOutStart() {}
    public void onTransitionOutFinish() {}
}
