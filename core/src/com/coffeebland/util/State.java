package com.coffeebland.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by dagothig on 8/23/14.
 */
public abstract class State {
    public State(StateManager stateManager) {
        this.stateManager = stateManager;
        stateManager.addState(this);
    }

    private StateManager stateManager;

    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength) {
        stateManager.switchToState(stateType, transitionColor, transitionLength);
    }

    public abstract Color getBackgroundColor();
    public abstract void update(float delta);
    public abstract void render();

    public abstract void onTransitionInStart();
    public abstract void onTransitionInFinish();
    public abstract void onTransitionOutStart();
    public abstract void onTransitionOutFinish();
}
