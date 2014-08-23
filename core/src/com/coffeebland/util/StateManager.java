package com.coffeebland.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/23/14.
 */
public class StateManager {
    private Map<String, State> states = new HashMap<String, State>();
    private State currentState, nextState;
    private ShapeRenderer overlayRenderer = new ShapeRenderer();
    private Color transitionColor;
    private long remainingTransitionTime, transitionLength;

    public Color getBackgroundColor() {
        return currentState.getBackgroundColor();
    }

    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength) {
        nextState = states.get(stateType.getName());
        this.transitionColor = transitionColor;
        this.remainingTransitionTime = transitionLength;
        this.transitionLength = transitionLength;
        currentState.onTransitionOutStart();
    }

    public void addState(State state) {
        states.put(state.getClass().getName(), state);
    }

    private void renderTransition() {
        if (remainingTransitionTime > 0) {
            overlayRenderer.begin();
            long halfLength = (transitionLength / 2);
            transitionColor.a = 1 - (Math.abs(remainingTransitionTime - halfLength) / halfLength);
            overlayRenderer.setColor(transitionColor);
            overlayRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            overlayRenderer.end();
        }
    }
    public void render() {
        currentState.render();
        renderTransition();
    }

    private void updateTransition(long delta) {
        if (remainingTransitionTime > 0) {
            remainingTransitionTime -= delta;

            if (remainingTransitionTime <= 0) {
                currentState.onTransitionInFinish();
            }
        }

        if (remainingTransitionTime <= transitionLength / 2) {
            currentState.onTransitionOutFinish();
            nextState.onTransitionInStart();
            currentState = nextState;
        }
    }
    public void update(long delta) {
        updateTransition(delta);
        currentState.update(delta);
    }
}
