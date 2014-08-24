package com.coffeebland.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.coffeebland.util.Maybe;


/**
 * Created by dagothig on 8/23/14.
 */
public enum Control {
    MOVE_LEFT(Input.Keys.LEFT),
    MOVE_RIGHT(Input.Keys.RIGHT),
    RAISE_CELLPHONE(Input.Keys.SPACE),
    ENTER(Input.Keys.ENTER),
    OPEN_MENU(Input.Keys.ESCAPE);

    private static Preferences prefs;

    static {
        prefs = Gdx.app.getPreferences("Controls");

        for (Control control : Control.values()) {
            int keyCode = prefs.getInteger(control.name(), Integer.MIN_VALUE);
            if (keyCode != Integer.MIN_VALUE)
                control.setKeyCode(keyCode);
        }
    }

    public static void savePrefs() {
        for (Control control : Control.values()) {
            prefs.putInteger(control.name(), control.getKeyCode());
        }
        prefs.flush();
    }

    public static Maybe<Control> getControl(int keycode) {
        for (Control control : Control.values()) {
            if (control.getKeyCode() == keycode) {
                return new Maybe<Control>(control);
            }
        }
        return new Maybe<Control>();
    }

    private Control(int keyCode) {
        setKeyCode(keyCode);
    }
    private int keyCode;
    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
