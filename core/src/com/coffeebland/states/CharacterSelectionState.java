package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;

/**
 * Created by dagothig on 8/23/14.
 */
public class CharacterSelectionState extends State {
    public CharacterSelectionState() {
        super();

        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);

        camera = new Camera();
        camera.setPosition(0);
        caracter = new Pedestrian("sprites/CharacterWalk.png", Color.MAGENTA.cpy(),
            "sprites/CharacterWalk.png", "sprites/CharacterWalk.png", Color.MAGENTA.cpy(), 0, Gdx.graphics.getHeight()-300);
        caracter.setAnimationY(Pedestrian.FRAME_WALK);

        // Bind on click, verify for mouse in change arrays
    }

    private Texture bg;
    private BitmapFont font;
    private Camera camera;
    private Pedestrian caracter;

    @Override
    public boolean shouldBeReused() { return false; }

    @Override
    public void render(SpriteBatch batch) {
        font.setColor(Color.WHITE.cpy());
        String text = "Character Selection";
        float halfTextWidth = font.getBounds(text).width/2;
        font.draw(batch, text, (Gdx.graphics.getWidth()/2) - halfTextWidth, Gdx.graphics.getHeight()-60);

        caracter.render(batch, camera);

        // Render select option for:

        // Hair
        // Clothes
        // Skin

        // Change arrows
    }

    @Override
    public void update(float delta) {
        caracter.update(delta);
    }

}
