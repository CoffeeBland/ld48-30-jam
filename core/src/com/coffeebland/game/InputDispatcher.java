package com.coffeebland.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.coffeebland.util.Maybe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/23/14.
 */
public class InputDispatcher implements InputProcessor {
    private Map<Control, OnKeyListener> keyListeners = new HashMap<Control, OnKeyListener>();
    private Map<Control, OnMouseListener> mouseListeners = new HashMap<Control, OnMouseListener>();

    @Override
    public boolean keyDown(int keycode) {
        Maybe<Control> maybe = Control.getControl(keycode);
        if (!maybe.hasValue())
            return false;

        Control control = maybe.getValue();
        if (keyListeners.containsKey(control)) {
            keyListeners.get(control).onKeyDown();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Maybe<Control> maybe = Control.getControl(keycode);
        if (!maybe.hasValue())
            return false;

        Control control = maybe.getValue();
        if (keyListeners.containsKey(control)) {
            keyListeners.get(control).onKeyUp();
            return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void update(float delta) {
        //Gdx.app.getInput().isKey
    }

    public void listenTo(Control control, OnKeyListener listener) {

    }
    public void listenTo(Control control, OnMouseListener listener) {

    }

    public static interface OnKeyListener {
        public void onKeyDown();
        public void onKeyUp();
        public void onKeyIsDown();
    }
    public static interface OnMouseListener {
        public void OnMouseDown(int x, int y);
        public void OnMouseUp(int x, int y);
        public void OnMouseIsDown(int x, int y);
    }
}
