package com.coffeebland.game.phone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.res.Images;
import com.coffeebland.util.Renderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/23/14.
 */
public class Phone implements Updateable, Renderable {
    public static final int
            PHONE_BACK_DECAL_X = 21,
            PHONE_BACK_DECAL_Y = 89,
            PHONE_CASE_DECAL_X = 82,
            PHONE_CASE_DECAL_Y = 83,
            BTN_HOME_DECAL_X = 86,
            BTN_HOME_DECAL_Y = -75,
            BTN_POWER_DECAL_X = 204,
            BTN_POWER_DECAL_Y = 393,
            TRANSITION_DURATION = 250;

    public Phone() {
        whitePixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        whitePixel.drawPixel(0, 0, 0xFFFFFFFF);
        whitePixelText = new Texture(whitePixel);

        phoneCase = Images.get("sprites/phone/phone_case.png");
        phoneBack = Images.get("sprites/phone/phone_background.png");
        hand = Images.get("sprites/phone/phone_hand.png");

        phoneApps = new ImageSheet("sprites/phone/phone_btns_apps.png", 66, 66, false);
        homeButton = new ImageSheet("sprites/phone/phone_btn_home.png", 64, 64, false);
        powerButton = new ImageSheet("sprites/phone/phone_btn_power.png", 32, 32, false);

        overlayColor = new Color(0, 0, 0, 0);

        inputDispatcher.listenTo(Control.OPEN_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                hidePhone();
            }

            @Override
            public void onKeyUp() {

            }

            @Override
            public void onKeyIsDown() {

            }
        });
    }

    private Texture phoneCase, phoneBack, hand, whitePixelText;
    private ImageSheet phoneApps, homeButton, powerButton;
    private Pixmap whitePixel;
    private boolean renderPhone, homeButtonPressed, powerButtonPressed;
    private float transition = 0, overlayOpacity = 0.5f;
    private Color overlayColor, skinColor;
    private InputProcessor previousInputProcessor;
    private InputDispatcher inputDispatcher = new InputDispatcher();

    public boolean isShown() {
        return renderPhone;
    }


    @Override
    public void update(float delta) {
        if (transition > 0) {
            transition = Math.max(transition - delta, 0);
        } else {
            transition = 0;
        }
    }

    private void renderApps(SpriteBatch batch, float refX, float refY, float imageScale) {
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
            phoneApps.render(batch, (int)x, (int)y, 0, i + 1, false);
        }
    }
    @Override
    public void render(SpriteBatch batch) {
        if (!renderPhone && transition <= 0)
            return;

        float transitionScale = transition / (float)TRANSITION_DURATION;
        if (!renderPhone) {
            transitionScale = 1 - transitionScale;
        }
        float imageScale = 1 - transitionScale * 0.5f;
        float colorScale = (1 - transitionScale) * 1.25f - 0.25f;

        float overallHeight = phoneCase.getHeight() + PHONE_CASE_DECAL_Y;
        float globalDecal = -transitionScale * overallHeight;

        float phoneCaseX = Gdx.graphics.getWidth() / 2 - (phoneCase.getWidth() * imageScale) / 2;
        float phoneCaseY = globalDecal + PHONE_CASE_DECAL_Y * imageScale;

        float handX = phoneCaseX - PHONE_CASE_DECAL_X * imageScale;
        float handY = globalDecal;

        float phoneBackX = phoneCaseX + PHONE_BACK_DECAL_X * imageScale;
        float phoneBackY = phoneCaseY + PHONE_BACK_DECAL_Y * imageScale;

        float homeButtonX = phoneBackX + BTN_HOME_DECAL_X * imageScale;
        float homeButtonY = phoneBackY + BTN_HOME_DECAL_Y * imageScale;

        float powerButtonX = phoneBackX + BTN_POWER_DECAL_X * imageScale;
        float powerButtonY = phoneBackY + BTN_POWER_DECAL_Y * imageScale;

        Color phoneColor = new Color(colorScale, colorScale, colorScale, 1);

        overlayColor.a = (1 - transitionScale) * overlayOpacity;
        batch.setColor(overlayColor);
        batch.draw(whitePixelText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(phoneColor);
        batch.draw(phoneCase, phoneCaseX, phoneCaseY, phoneCase.getWidth() * imageScale, phoneCase.getHeight() * imageScale);
        batch.draw(phoneBack, phoneBackX, phoneBackY, phoneBack.getWidth() * imageScale, phoneBack.getHeight() * imageScale);
        homeButton.setScale(imageScale);
        homeButton.render(batch, homeButtonX, homeButtonY, homeButtonPressed ? 1 : 0, 0, false);
        powerButton.setScale(imageScale);
        powerButton.render(batch, powerButtonX, powerButtonY, powerButtonPressed ? 1 : 0, 0, false);
        batch.setColor(skinColor.cpy().mul(colorScale, colorScale, colorScale, 1));
        batch.draw(hand, handX, handY, hand.getWidth() * imageScale, hand.getHeight() * imageScale);

        batch.setColor(phoneColor);
        renderApps(batch, phoneBackX, phoneBackY, imageScale);
        batch.setColor(Color.WHITE);
    }

    public void showPhone(Pedestrian player) {
        renderPhone = true;
        skinColor = player.getSkinColor();
        if (transition <= 0)
            transition = TRANSITION_DURATION;
        else
            transition = TRANSITION_DURATION - transition;

        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(inputDispatcher);
    }

    public void hidePhone() {
        renderPhone = false;
        if (transition <= 0)
            transition = TRANSITION_DURATION;
        else
            transition = TRANSITION_DURATION - transition;

        Gdx.input.setInputProcessor(previousInputProcessor);
    }

}
