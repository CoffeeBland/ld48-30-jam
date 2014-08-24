package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class LogoState extends State {
    public static String LOGO_PATH = "sprites/coffeebland.png";

    public LogoState() {
        setBackgroundColor(Color.WHITE.cpy());
        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont();

        img = new Texture(Gdx.files.internal(LOGO_PATH));
        imgX = (Gdx.graphics.getWidth() / 2) - (img.getWidth() / 2); // Half the logo
        imgY = (Gdx.graphics.getHeight() / 2) - (img.getHeight() / 2); // Half the logo
    }

    private float imgX = 0;
    private float imgY = 0;
    private long timeIn = 0;
    private boolean inMenu = false;
    private Texture img;
    private Texture bg;
    private BitmapFont font;

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void update(float delta) {
        timeIn += delta;
        if (timeIn > 2000 && !inMenu) {
            inMenu = true;
            switchToState(MenuState.class, Color.WHITE.cpy(), TRANSITION_LONG);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(new Color(0x75858AFF));
        batch.draw(bg, 12, 12, Gdx.graphics.getWidth()-24, Gdx.graphics.getHeight()-24);
        batch.setColor(Color.WHITE.cpy());
        batch.draw(img, imgX, imgY);
        font.setColor(Color.BLACK.cpy());
        font.draw(batch, "Myriam - Dagothig - Mey - kiasaki", (Gdx.graphics.getWidth()/2)-192, 48);
    }

    @Override
    public void onTransitionInStart() {
        timeIn = 0;
        inMenu = false;
    }

    @Override
    public void onTransitionInFinish() {
    }

}