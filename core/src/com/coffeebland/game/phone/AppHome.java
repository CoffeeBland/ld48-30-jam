package com.coffeebland.game.phone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.game.Candidate;
import com.coffeebland.input.ClickManager;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.res.Images;

/**
 * Created by dagothig on 8/24/14.
 */
public class AppHome extends PhoneApp {
    public static final int APP_SIZE = 66;

    public AppHome() {
        phoneApps = new ImageSheet("sprites/phone/phone_btns_apps.png", APP_SIZE, APP_SIZE, false);
        phoneBack = Images.get("sprites/phone/phone_background.png");

        listeners = new ClickManager.OnClickListener[] {
            // Snapcat
            new ClickManager.OnClickListener(new Rectangle(0, 0, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppSnapcat());
                }
            },
            // Tindun
            new ClickManager.OnClickListener(new Rectangle(1, 1, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppTindun(Candidate.SELECTED_CANDIDATE));
                }
            },
            // Chat
            new ClickManager.OnClickListener(new Rectangle(2, 2, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppChat());
                }
            },
            // Map
            new ClickManager.OnClickListener(new Rectangle(3, 3, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppMap());
                }
            },
            // Stats
            new ClickManager.OnClickListener(new Rectangle(4, 4, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppStats());
                }
            },
            // Settings
            new ClickManager.OnClickListener(new Rectangle(5, 5, APP_SIZE, APP_SIZE)) {
                @Override
                public void onClick() {
                    phone.openApp(new AppSettings());
                }
            }
        };
    }

    private Texture phoneBack;
    private ImageSheet phoneApps;
    private int focus = -1;

    public void focus(int appNum) {
        focus = appNum;
    }

    @Override
    public void render(SpriteBatch batch, float refX, float refY, float imageScale) {
        batch.draw(phoneBack, refX, refY, phoneBack.getWidth() * imageScale, phoneBack.getHeight() * imageScale);
        phoneApps.setScale(imageScale);

        float width = phoneBack.getWidth() * imageScale;
        float height = phoneBack.getHeight() * imageScale;
        float frame = phoneApps.getFrameWidth() * imageScale;

        float marginHorizontal = (width - (frame * 2f)) / 3f;
        float marginVertical = (height - (frame * 3f)) / 4f;

        float initX = refX + marginHorizontal;
        float initY = refY + height - marginVertical - frame;

        for (int i = 0; i < 6; i++) {
            float x = initX + (i % 2) * (frame + marginHorizontal);
            float y = initY - (i / 2) * (frame + marginVertical);
            listeners[i].region.x = x;
            listeners[i].region.y = y;
            phoneApps.render(batch, (int)x, (int)y, 0, i + 1, false);
            if (listeners[i].buttonStatus || focus == i) {
                phoneApps.render(batch, (int) x, (int) y, 0, 0, false);
            }
        }
    }

    @Override
    public void update(float delta) {

    }
}
