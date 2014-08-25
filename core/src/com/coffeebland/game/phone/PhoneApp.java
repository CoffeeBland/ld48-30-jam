package com.coffeebland.game.phone;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.input.ClickManager;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/24/14.
 */
public abstract class PhoneApp implements Updateable {
    protected ClickManager.OnClickListener[] listeners;
    protected Phone phone;

    public ClickManager.OnClickListener[] getListeners() {
        return listeners;
    }

    public abstract void render(SpriteBatch batch, float refX, float refY, float imageScale);
    @Override
    public abstract void update(float delta);

    public void open(ClickManager mgr, Phone phone) {
        for (ClickManager.OnClickListener btn : listeners) {
            mgr.addButton(btn);
        }
        this.phone = phone;
    }
    public void close(ClickManager mgr) {
        for (ClickManager.OnClickListener btn : listeners) {
            mgr.removeButton(btn);
        }
    }
}
