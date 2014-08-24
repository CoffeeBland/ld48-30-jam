package com.coffeebland.game.phone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.input.ClickManager;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.res.ImageSheet;
import com.coffeebland.res.Images;
import com.coffeebland.util.Maybe;
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
        hand = Images.get("sprites/phone/phone_hand.png");

        homeButton = new ImageSheet("sprites/phone/phone_btn_home.png", 64, 64, false);
        clickManager.addButton(homeButtonClick = new ClickManager.OnClickListener(new Rectangle(0, 0, 64, 64)) {
            @Override
            public void onClick() {
                openApp(homeApp);
            }
        });

        powerButton = new ImageSheet("sprites/phone/phone_btn_power.png", 32, 32, false);
        clickManager.addButton(powerButtonClick = new ClickManager.OnClickListener(new Rectangle(0, 0, 32, 32))  {
            @Override
            public void onClick() {

            }
        });

        overlayColor = new Color(0, 0, 0, 0);

        inputDispatcher.listenTo(Control.EXIT_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                hidePhone();
            }
            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });
    }

    private Texture phoneCase, hand, whitePixelText;
    private ImageSheet homeButton, powerButton;
    private Pixmap whitePixel;
    private boolean renderPhone;
    private float transition = 0, overlayOpacity = 0.5f, sincePowerButton = 0;
    private ClickManager.OnClickListener homeButtonClick, powerButtonClick;
    private Color overlayColor, skinColor;
    private InputProcessor previousInputProcessor;
    private InputDispatcher inputDispatcher = new InputDispatcher();
    private ClickManager clickManager = new ClickManager(inputDispatcher);
    private Maybe<PhoneApp> currentApp = new Maybe<PhoneApp>();
    private PhoneApp homeApp = new AppHome();

    public boolean isShown() {
        return renderPhone;
    }

    @Override
    public void update(float delta) {
        if (renderPhone) {
            inputDispatcher.update(delta);
            if (currentApp.hasValue())
                currentApp.getValue().update(delta);
        }
        if (transition > 0) {
            transition = Math.max(transition - delta, 0);
        } else {
            transition = 0;
        }

        if (powerButtonClick.buttonStatus) {
            sincePowerButton += delta;
            if (sincePowerButton > 3000) {
                Gdx.app.exit();
            }
        } else {
            sincePowerButton = 0;
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

        homeButtonClick.region.x = phoneBackX + BTN_HOME_DECAL_X * imageScale;
        homeButtonClick.region.y = phoneBackY + BTN_HOME_DECAL_Y * imageScale;

        powerButtonClick.region.x = phoneBackX + BTN_POWER_DECAL_X * imageScale;
        powerButtonClick.region.y = phoneBackY + BTN_POWER_DECAL_Y * imageScale;

        Color phoneColor = new Color(colorScale, colorScale, colorScale, 1);

        // Overlay
        overlayColor.a = (1 - transitionScale) * overlayOpacity;
        batch.setColor(overlayColor);
        batch.draw(whitePixelText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Phone
        batch.setColor(phoneColor);

        batch.draw(phoneCase, phoneCaseX, phoneCaseY, phoneCase.getWidth() * imageScale, phoneCase.getHeight() * imageScale);

        if (currentApp.hasValue())
            currentApp.getValue().render(batch, phoneBackX, phoneBackY, imageScale);

        homeButton.setScale(imageScale);
        homeButton.render(batch, homeButtonClick.region.x, homeButtonClick.region.y, homeButtonClick.buttonStatus ? 1 : 0, 0, false);

        powerButton.setScale(imageScale);
        powerButton.render(batch, powerButtonClick.region.x, powerButtonClick.region.y, powerButtonClick.buttonStatus ? 1 : 0, 0, false);

        // Hand
        batch.setColor(skinColor.cpy().mul(colorScale, colorScale, colorScale, 1));
        batch.draw(hand, handX, handY, hand.getWidth() * imageScale, hand.getHeight() * imageScale);
        batch.setColor(Color.WHITE);
    }

    public void showPhone(Pedestrian player) {
        renderPhone = true;
        skinColor = player.getSkinColor();
        if (transition <= 0) {
            transition = TRANSITION_DURATION;
            openApp(homeApp);
        } else {
            transition = TRANSITION_DURATION - transition;
        }

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

    public void openApp(PhoneApp app) {
        if (currentApp.hasValue())
            currentApp.getValue().close(clickManager);
        app.open(clickManager, this);
        currentApp = new Maybe<PhoneApp>(app);
    }
}
