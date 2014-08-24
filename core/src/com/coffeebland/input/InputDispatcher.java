package com.coffeebland.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.coffeebland.util.Maybe;

import java.util.Collection;
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
        Collection<Control> controls = Control.getControls(keycode);

        boolean returnValue = false;
        for (Control control: controls) {
            if (keyListeners.containsKey(control)) {
                keyListeners.get(control).onKeyDown();
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean keyUp(int keycode) {
        Collection<Control> controls = Control.getControls(keycode);

        boolean returnValue = false;
        for (Control control: controls) {
            if (keyListeners.containsKey(control)) {
                keyListeners.get(control).onKeyUp();
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Collection<Control> controls = Control.getControls(button);

        boolean returnValue = false;
        for (Control control: controls) {
            if (mouseListeners.containsKey(control)) {
                mouseListeners.get(control).onMouseDown(screenX, screenY);
                returnValue = true;
            }
        }

        return returnValue;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Collection<Control> controls = Control.getControls(button);

        boolean returnValue = false;
        for (Control control: controls) {
            if (mouseListeners.containsKey(control)) {
                mouseListeners.get(control).onMouseUp(screenX, screenY);
                returnValue = true;
            }
        }

        return returnValue;
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
        Input input = Gdx.app.getInput();
        for (Map.Entry<Control, OnKeyListener> entry : keyListeners.entrySet()) {
            if (input.isKeyPressed(entry.getKey().getKeyCode())) {
                entry.getValue().onKeyIsDown();
            }
        }

        for (Map.Entry<Control, OnMouseListener> entry : mouseListeners.entrySet()) {
            int x = input.getX(), y = input.getY();
            if (input.isButtonPressed(entry.getKey().getKeyCode())) {
                entry.getValue().onMouseIsDown(x, y);
            }
        }
    }

    public void listenTo(Control control, OnKeyListener listener) {
        keyListeners.put(control, listener);
    }
    public void listenTo(Control[] controls, OnKeyListener listener) {
        for (Control control: controls) {
            listenTo(controls, listener);
        }
    }
    public void listenTo(Control control, OnMouseListener listener) {
        mouseListeners.put(control, listener);
    }
    public void listenTo(Control[] controls, OnMouseListener listener) {
        for (Control control: controls) {
            listenTo(controls, listener);
        }
    }

    public static interface OnKeyListener {
        public void onKeyDown();
        public void onKeyUp();
        public void onKeyIsDown();
    }
    public static interface OnMouseListener {
        public void onMouseDown(int x, int y);
        public void onMouseUp(int x, int y);
        public void onMouseIsDown(int x, int y);
    }
}
