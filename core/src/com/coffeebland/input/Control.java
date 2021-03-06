package com.coffeebland.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by dagothig on 8/23/14.
 */
public enum Control {
    MOVE_LEFT(Input.Keys.LEFT),
    MOVE_RIGHT(Input.Keys.RIGHT),
    MOVE_UP(Input.Keys.UP),
    MOVE_DOWN(Input.Keys.DOWN),
    RAISE_CELLPHONE(Input.Keys.SPACE),
    ENTER(Input.Keys.ENTER),
    EXIT_MENU(Input.Keys.ESCAPE),
    OPEN_MENU(Input.Keys.ESCAPE),
    LEFT_CLICK(Input.Buttons.LEFT),
    SKIP_INTRO1(Input.Keys.ESCAPE),
    SKIP_INTRO2(Input.Keys.SPACE),
    SKIP_INTRO3(Input.Keys.ENTER);

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

    public static Collection<Control> getControls(int keycode) {
        Set<Control> controls = new HashSet<Control>();
        for (Control control : Control.values()) {
            if (control.getKeyCode() == keycode) {
                controls.add(control);
            }
        }
        return controls;
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
