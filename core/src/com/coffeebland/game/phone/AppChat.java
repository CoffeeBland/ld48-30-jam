package com.coffeebland.game.phone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.input.ClickManager;
import com.coffeebland.res.Images;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;
import com.coffeebland.util.Tuple;

import java.util.ArrayList;

/**
 * Created by dagothig on 8/24/14.
 */
public class AppChat extends PhoneApp {
    public AppChat() {
        bg = Images.get("sprites/phone/chat_background.png");
        listeners = new ClickManager.OnClickListener[] {

        };
    }

    private Texture bg;
    private BitmapFont font = FontUtil.normalFont(14);
    private ArrayList<Tuple<String, String>> messages = new ArrayList<Tuple<String, String>>();

    public void addMessage(String user, String message) {
        messages.add(new Tuple<String, String>(user, message));
    }

    public void clearMessages() {
        messages = new ArrayList<Tuple<String, String>>();
    }

    @Override
    public void render(SpriteBatch batch, float refX, float refY, float imageScale) {
        batch.setColor(new Color(0xEFEFEFFF));
        batch.draw(bg, refX, refY, bg.getWidth() * imageScale, bg.getHeight() * imageScale);

        int i = 0;
        for (Tuple<String, String> message : messages) {
            float usernameHeight = font.getBounds(message.getA()).height;
            float messageHeight = font.getWrappedBounds(message.getB(), bg.getWidth()-24).height;
            float baseY = refY + (bg.getHeight() * imageScale) - i - 12;
            batch.setColor(new Color(0x333333FF));
            batch.draw(bg, refX + 6, baseY + 4 - 22, (bg.getWidth() * imageScale) - 12, 22);
            batch.setColor(Color.WHITE.cpy());
            font.setColor(Color.WHITE.cpy());
            font.draw(batch, message.getA(), refX + 12, baseY);
            font.setColor(Color.BLACK.cpy());
            font.drawWrapped(batch, message.getB(), refX + 12, baseY - 24, bg.getWidth() - 24);
            i += messageHeight + usernameHeight + (12 * 2);
        }
    }

    @Override
    public void update(float delta) {

    }
}
