package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class LogoState extends State {
    public static String LOGO_PATH = "coffeebland.png";

    public  LogoState() {
        setBackgroundColor(Color.WHITE.cpy());
    }

    private float imgX = 0;
    private float imgY = 0;
    private long timeIn = 0;
    private Texture img;
    private Texture bg;
    private BitmapFont font;

    @Override
    public boolean shouldBeReused() { return true; }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void update(float delta) {
        timeIn += delta;
        if (timeIn > 3000) {
            switchToState(MenuState.class, Color.WHITE.cpy(), TRANSITION_MEDIUM);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(new Color(117, 133, 138, 255));
        batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        batch.draw(img, imgX, imgY);
        font.setColor(Color.BLACK.cpy());
        font.draw(batch, "Myriam - Dagothig - Mey - kiasaki", (Gdx.graphics.getWidth()/2)-192, 48);
    }

    @Override
    public void onTransitionInStart() {
        timeIn = 0;
        if (img == null) {
            img = new Texture(Gdx.files.internal(LOGO_PATH));
            imgX = (Gdx.graphics.getWidth() / 2) - (img.getWidth() / 2); // Half the logo
            imgY = (Gdx.graphics.getHeight() / 2) - (img.getHeight() / 2); // Half the logo
        }
        if (bg == null) {
            Pixmap bgPixel = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            bgPixel.drawPixel(0, 0, 0x75858A);
            bg = new Texture(bgPixel);
        }
        if (font == null) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("mono.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 18;
            font = generator.generateFont(parameter);
            generator.dispose();
        }
    }

    @Override
    public void onTransitionInFinish() {
    }

}
