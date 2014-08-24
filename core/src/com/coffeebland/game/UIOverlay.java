package com.coffeebland.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.states.GameState;
import com.coffeebland.util.Renderable;

/**
 * Created by dagothig on 8/23/14.
 */
public class UIOverlay implements Renderable {
    public static final int
            ICO_SIZE = 128,
            FRAME_3G = 0,
            FRAME_WIFI = 1,
            FRAME_BATTERY = 2;

    public UIOverlay() {

    }

    private ImageSheet icos = new ImageSheet("sprites/ui_icons.png", ICO_SIZE, ICO_SIZE);

    private int remainingWifi, remainingBattery;

    @Override
    public void render(SpriteBatch batch) {
        int left = Gdx.graphics.getWidth() - ICO_SIZE;
        int top = Gdx.graphics.getHeight() - ICO_SIZE;

        icos.render(batch, left, top, GameState.MAX_BATTERY - remainingBattery, FRAME_BATTERY, false);
        left -= ICO_SIZE;
        icos.render(batch, left, top, 0, FRAME_3G, false);
        left -= ICO_SIZE;
        icos.render(batch, left, top, GameState.MAX_WIFI - remainingWifi, FRAME_WIFI, false);
    }

    public void update(int remainingWifi, int remainingBattery) {
        this.remainingWifi = remainingWifi;
        this.remainingBattery = remainingBattery;
    }
}
