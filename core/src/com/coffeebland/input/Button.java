package com.coffeebland.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.util.Renderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/26/14.
 */
public abstract class Button implements Renderable, Updateable {
    public Button(ClickManager clickMgr, float width, float height) {
        clickMgr.addButton(new ClickManager.OnClickListener(clickableRegion) {
            @Override
            public void onClick() {

            }
        });
        clickableRegion.width = width;
        clickableRegion.height = height;
    }

    private Button
            buttonToLeft = this,
            buttonToRight = this,
            buttonToTop = this,
            buttonToBottom = this;
    private final Rectangle clickableRegion = new Rectangle(0, 0, 0, 0);

    @Override
    public void render(SpriteBatch batch) {

    }
    @Override
    public void update(float delta) {
        Input input = Gdx.app.getInput();
        int x = input.getX(), y = Gdx.graphics.getHeight() - input.getY();
    }
}
