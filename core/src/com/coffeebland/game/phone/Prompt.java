package com.coffeebland.game.phone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.input.ClickManager;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.util.Renderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/24/14.
 */
public class Prompt implements Renderable, Updateable {
    public void Prompt(String title) {
        this.title = title;

        whitePixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixel.drawPixel(0, 0, 0xFFFFFFFF);
        whitePixelText = new Texture(whitePixel);

        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(inputDispatcher = new InputDispatcher());
        clickMgr = new ClickManager(inputDispatcher);

        inputDispatcher.listenTo(Control.EXIT_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                dismiss();
            }

            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });
    }

    private String title;
    private Texture whitePixelText;
    private Pixmap whitePixel;
    private InputProcessor previousInputProcessor;
    private InputDispatcher inputDispatcher;
    private ClickManager clickMgr;

    private void dismiss() {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }
}
