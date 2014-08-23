package com.coffeebland.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.util.Maybe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/23/14.
 */
public class StateManager {
    public StateManager() {
        whitePixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixel.drawPixel(0, 0, 0xFFFFFFFF);
        whitePixelText = new Texture(whitePixel);

        switchToState(TempState.class, Color.BLACK.cpy(), State.TRANSITION_SHORT);
    }

    private Map<String, State> states = new HashMap<String, State>();
    private Maybe<State> currentState = new Maybe<State>(), nextState = new Maybe<State>();
    private Maybe<Object> switchArgs;
    private Pixmap whitePixel;
    private Texture whitePixelText;
    private Color transitionColor;
    private long remainingTransitionTime, transitionLength;

    public Color getBackgroundColor() {
        if (currentState.hasValue())
            return currentState.getValue().getBackgroundColor();
        else
            return Color.BLACK.cpy();
    }

    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength, Maybe<Object> switchArgs) {
        if (states.containsKey(stateType.getName())) {
            nextState = new Maybe(states.get(stateType.getName()));
        } else {
            try {
                nextState = new Maybe(stateType.newInstance());
                nextState.getValue().setStateManager(this);
                states.put(stateType.getName(), nextState.getValue());
            } catch (ReflectiveOperationException ex) {
                throw new RuntimeException("The state could not be instantiated");
            }
        }
        this.transitionColor = transitionColor;
        this.remainingTransitionTime = transitionLength;
        this.transitionLength = transitionLength;
        this.switchArgs = switchArgs;
        if (currentState.hasValue())
            currentState.getValue().onTransitionOutStart();
        else
            remainingTransitionTime /= 2;
    }
    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength, Object switchArgs) {
        switchToState(stateType, transitionColor, transitionLength, new Maybe<Object>(switchArgs));
    }
    public void switchToState(Class<? extends State> stateType, Color transitionColor, long transitionLength) {
        switchToState(stateType, transitionColor, transitionLength, new Maybe<Object>());
    }

    private void renderTransition(SpriteBatch batch) {
        if (remainingTransitionTime > 0) {
            long halfLength = (transitionLength / 2);
            transitionColor.a = 1 - (Math.abs(remainingTransitionTime - halfLength) / (float)halfLength);
            batch.setColor(transitionColor);
            batch.draw(whitePixelText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
    public void render(SpriteBatch batch) {
        if (currentState.hasValue())
            currentState.getValue().render(batch);
        renderTransition(batch);
    }

    private void updateTransition(long delta) {
        if (remainingTransitionTime > 0) {
            remainingTransitionTime -= delta;

            if (remainingTransitionTime <= 0 && currentState.hasValue()) {
                currentState.getValue().onTransitionInFinish();
            }

            if (remainingTransitionTime <= transitionLength / 2) {
                if (currentState.hasValue())
                    currentState.getValue().onTransitionOutFinish();
                if (nextState.hasValue()) {
                    if (switchArgs.hasValue()) {
                        nextState.getValue().onTransitionInStart(switchArgs.getValue());
                    } else {
                        nextState.getValue().onTransitionInStart();
                    }
                }
                // Dispose the current state if it shouln'd be reused
                if (currentState.hasValue() && !currentState.getValue().shouldBeReused()) {
                    states.remove(currentState.getValue().getClass().getName());
                }
                currentState = nextState;
            }
        }

    }
    public void update(long delta) {
        updateTransition(delta);
        if (currentState.hasValue())
            currentState.getValue().update(delta);
    }
}
