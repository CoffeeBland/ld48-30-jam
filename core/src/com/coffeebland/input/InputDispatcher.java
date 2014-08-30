package com.coffeebland.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.coffeebland.util.Updateable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dagothig on 8/23/14.
 */
public class InputDispatcher implements InputProcessor, Updateable {
    private Map<Control, OnKeyListener> keyListeners = new HashMap<Control, OnKeyListener>();
    private Map<Control, OnMouseListener> mouseListeners = new HashMap<Control, OnMouseListener>();

    @Override
    public boolean keyDown(int keycode) {
        Collection<Control> controls = Control.getControls(keycode);

        boolean returnValue = false;
        for (Control control: controls) {
            if (keyListeners.containsKey(control) && control.getKeyCode() == keycode) {
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
            if (keyListeners.containsKey(control) && control.getKeyCode() == keycode) {
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
        int y = Gdx.graphics.getHeight() - screenY;
        Collection<Control> controls = Control.getControls(button);

        boolean returnValue = false;
        Collection<Control> controlsToDown = new ArrayList<Control>();

        for (Control control: controls) {
            if (mouseListeners.containsKey(control)) {
                controlsToDown.add(control);
            }
        }

        for (Control control : controlsToDown) {
            mouseListeners.get(control).onMouseDown(screenX, y);
            returnValue = true;
        }

        return returnValue;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        int y = Gdx.graphics.getHeight() - screenY;
        Collection<Control> controls = Control.getControls(button);

        boolean returnValue = false;
        Collection<Control> controlsToUp = new ArrayList<Control>();

        for (Control control: controls) {
            if (mouseListeners.containsKey(control) && control.getKeyCode() == button) {
                controlsToUp.add(control);
            }
        }

        for (Control control : controlsToUp) {
            mouseListeners.get(control).onMouseUp(screenX, y);
            returnValue = true;
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

    @Override
    public void update(float delta) {
        if (this != Gdx.input.getInputProcessor())
            return;

        Input input = Gdx.app.getInput();

        // Keys
        Collection<Map.Entry<Control, OnKeyListener>> keysToDown = new ArrayList<Map.Entry<Control, OnKeyListener>>();
        for (Map.Entry<Control, OnKeyListener> entry : keyListeners.entrySet()) {
            if (input.isKeyPressed(entry.getKey().getKeyCode())) {
                keysToDown.add(entry);
            }
        }
        for (Map.Entry<Control, OnKeyListener> entry : keysToDown) {
            entry.getValue().onKeyIsDown();
        }

        // Mouse
        Collection<Map.Entry<Control, OnMouseListener>> mouseToDown = new ArrayList<Map.Entry<Control, OnMouseListener>>();
        for (Map.Entry<Control, OnMouseListener> entry : mouseListeners.entrySet()) {
            if (input.isButtonPressed(entry.getKey().getKeyCode())) {
                mouseToDown.add(entry);
            }
        }
        int x = input.getX(), y = Gdx.graphics.getHeight() - input.getY();
        for (Map.Entry<Control, OnMouseListener> entry : mouseToDown) {
            entry.getValue().onMouseIsDown(x, y);
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
