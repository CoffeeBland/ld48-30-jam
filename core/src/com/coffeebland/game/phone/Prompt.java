package com.coffeebland.game.phone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.input.ClickManager;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;
import com.coffeebland.util.Renderable;
import com.coffeebland.util.Updateable;

/**
 * Created by dagothig on 8/24/14.
 */
public class Prompt implements Renderable, Updateable {
    public static final int MARGIN = 32;

    public Prompt(String title, OnPromptResultListener listener) {
        this.title = title;
        this.listener = listener;

        whitePixelText = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);
        font.setColor(Color.WHITE);

        boundsTitle = font.getBounds(title, boundsTitle = new BitmapFont.TextBounds());

        boundsYes = font.getBounds(yes, boundsYes = new BitmapFont.TextBounds());

        boundsNo = font.getBounds(no, boundsNo = new BitmapFont.TextBounds());

        width = Math.max(boundsTitle.width, boundsYes.width + MARGIN + boundsNo.width) + MARGIN * 2;
        height = boundsTitle.height + Math.max(boundsYes.height, boundsNo.height) + MARGIN * 3;

        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(inputDispatcher = new InputDispatcher());
        clickMgr = new ClickManager(inputDispatcher);

        inputDispatcher.listenTo(Control.EXIT_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                dismiss();
                Prompt.this.listener.onResult(false);
            }

            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });

        clickMgr.addButton(btnYes = new ClickManager.OnClickListener(new Rectangle(0, 0, boundsYes.width, boundsYes.height)) {
            @Override
            public void onClick() {
                Prompt.this.listener.onResult(true);
            }
        });
        clickMgr.addButton(btnNo = new ClickManager.OnClickListener(new Rectangle(0, 0, boundsNo.width, boundsNo.height)) {
            @Override
            public void onClick() {
                dismiss();
                Prompt.this.listener.onResult(false);
            }
        });
    }

    private String title, yes = "yes", no = "no";
    private Texture whitePixelText;
    private InputProcessor previousInputProcessor;
    private InputDispatcher inputDispatcher;
    private ClickManager clickMgr;
    private BitmapFont font;
    private BitmapFont.TextBounds boundsTitle, boundsYes, boundsNo;
    private float width, height;
    private Color overlayColor = new Color(0, 0, 0, 0.5f);
    private boolean dismissed = false;
    private ClickManager.OnClickListener btnYes, btnNo;
    private OnPromptResultListener listener;

    private void dismiss() {
        Gdx.input.setInputProcessor(previousInputProcessor);
        dismissed = true;
    }
    public boolean dismissed() {
        return dismissed;
    }

    @Override
    public void render(SpriteBatch batch) {
        float x = Gdx.graphics.getWidth() / 2 - width / 2,
                y = Gdx.graphics.getHeight() / 2 - height / 2;

        batch.setColor(overlayColor);
        batch.draw(whitePixelText, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(Color.BLACK);
        batch.draw(whitePixelText, x, y, width, height);
        batch.setColor(Color.WHITE);

        btnYes.region.x = x + width / 4 - boundsYes.width / 2;
        btnYes.region.y = y + height / 4 + boundsYes.height / 2;
        font.setColor(btnYes.buttonStatus ? Color.LIGHT_GRAY: Color.WHITE);
        font.draw(batch, yes, btnYes.region.x, btnYes.region.y);
        btnYes.region.y -= boundsYes.height;

        btnNo.region.x = x + (3 * width) / 4 - boundsYes.width / 2;
        btnNo.region.y = y + height / 4 + boundsYes.height / 2;
        font.setColor(btnNo.buttonStatus ? Color.LIGHT_GRAY: Color.WHITE);
        font.draw(batch, no,  btnNo.region.x, btnNo.region.y);
        btnNo.region.y -= boundsNo.height;

        font.setColor(Color.WHITE);
        font.draw(batch, title, x + width / 2 - boundsTitle.width / 2, y + (3 * height) / 4 + boundsTitle.height / 2);
    }

    @Override
    public void update(float delta) {
        inputDispatcher.update(delta);
    }

    public static interface OnPromptResultListener {
        public void onResult(boolean success);
    }
}
