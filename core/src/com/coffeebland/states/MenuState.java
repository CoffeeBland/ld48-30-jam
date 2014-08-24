package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.state.State;
import com.coffeebland.util.ButtonUtil;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class MenuState extends State {
    public MenuState() {
        super();

        setBackgroundColor(Color.WHITE.cpy());
        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);
        bigFont = FontUtil.normalFont(72);

        getInputManager().listenTo(Control.EXIT_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                System.out.println("out");
                Gdx.app.exit();
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.ENTER, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                switchingState = true;
                switchToState(CharacterSelectionState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM);
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.LEFT_CLICK, new InputDispatcher.OnMouseListener() {
            @Override
            public void onMouseDown(int x, int y) {
            }

            @Override
            public void onMouseUp(int x, int y) {
                Rectangle2D.Float bounds = ButtonUtil.getButtonBoundsCentered(font, "New game", Gdx.graphics.getHeight()-400);
                if (bounds.contains(x, y)) {
                    switchingState = true;
                    switchToState(CharacterSelectionState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM);
                }
            }

            @Override
            public void onMouseIsDown(int x, int y) {
            }
        });
    }

    private Texture bg;
    private BitmapFont font;
    private BitmapFont bigFont;
    private boolean switchingState;

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        //if (switchingState) return;
        int wWidth = Gdx.graphics.getWidth();
        int wHeight = Gdx.graphics.getHeight();

        // Bg
        batch.setColor(new Color(0x75858AFF));
        batch.draw(bg, 12, 12, wWidth-24, wHeight-24);
        batch.setColor(Color.WHITE.cpy());

        // Game title
        bigFont.setColor(Color.WHITE.cpy());
        bigFont.draw(batch, "HotSpot", (wWidth/2)-165, wHeight-172);

        // Draw new game btn
        ButtonUtil.drawButtonCentered(batch, font, "New game", wHeight-400);
    }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void onTransitionInStart() {
        switchingState = false;
    }
    @Override
    public void onTransitionInFinish() {
    }

}