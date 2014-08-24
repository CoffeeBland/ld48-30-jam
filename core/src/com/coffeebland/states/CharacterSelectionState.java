package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.HotSpot;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.FontUtil;
import com.coffeebland.util.Tuple;

import java.lang.reflect.Array;

/**
 * Created by dagothig on 8/23/14.
 */
public class CharacterSelectionState extends State {
    public static int CTL_HAIR = 0,
                      CTL_CLOTHES = 1,
                      CTL_SKIN = 2,
                      CTL_PLAY = 3;
    public static int[] SKINS = new int[] { 0xA5793CFF, 0x5D451FFF, 0xFFC27FFF, 0xFFD3A4FF };
    public static Tuple<String, Integer>[] HAIRS;

    public CharacterSelectionState() {
        super();

        setBackgroundColor(new Color(0x666666FF));

        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);
        arrow = new Texture("sprites/arrow.png");

        camera = new Camera();
        camera.setPosition(0);
        character = new Pedestrian(
            "sprites/character/char_male.png", new Color(SKINS[SKINS.length-1]),
            "sprites/character/char_male_clothes1.png",
            "sprites/character/char_hair_short.png",
            Color.WHITE.cpy(), 0, (Gdx.graphics.getHeight()-300) / HotSpot.UPSCALE_RATE);

        character.setFrameY(Pedestrian.FRAME_WALK);
        character.setFps(15);

        if (HAIRS == null) {
            Class exempleTupleClass = new Tuple<String, Integer>("", 1).getClass();
            HAIRS = (Tuple<String, Integer>[])Array.newInstance(exempleTupleClass, 2);
            HAIRS[0] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xFFFFFFFF);
            HAIRS[1] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0x000000FF);
        }

        selectedButton = CTL_HAIR;
        selectedSkin = SKINS.length-1;
        selectedHair = 0;

        // Bind on arrows
        getInputManager().listenTo(Control.ENTER, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                if (selectedButton == CTL_PLAY) {
                    switchingState = true;
                    IntroState.IntroStateInfo info = new IntroState.IntroStateInfo(character);
                    switchToState(IntroState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM, info);
                }
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.MOVE_UP, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                selectedButton--;
                if (selectedButton == -1) selectedButton = 3;
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.MOVE_DOWN, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                selectedButton++;
                if (selectedButton == 4) selectedButton = 0;
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.MOVE_LEFT, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                change(-1);
            }

            @Override
            public void onKeyIsDown() {
            }
        });
        getInputManager().listenTo(Control.MOVE_RIGHT, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
            }

            @Override
            public void onKeyUp() {
                change(1);
            }

            @Override
            public void onKeyIsDown() {
            }
        });
    }

    private Texture bg;
    private Texture arrow;
    private BitmapFont font;
    private Camera camera;
    private Pedestrian character;
    private float timeIn;
    private boolean switchingState = false;
    private int selectedButton;
    private int selectedSkin;
    private int selectedHair;

    @Override
    public boolean shouldBeReused() { return false; }

    private void change(int direction) {
        if (selectedButton == CTL_SKIN) {
            selectedSkin = selectedSkin + direction;
            selectedSkin = selectedSkin == SKINS.length ? 0 : selectedSkin;
            selectedSkin = selectedSkin == -1 ? SKINS.length-1 : selectedSkin;

            character.setSkin("sprites/character/char_male.png", new Color(SKINS[selectedSkin]));
        } else if (selectedButton == CTL_HAIR) {
            selectedHair = selectedHair + direction;
            selectedHair = selectedHair == HAIRS.length ? 0 : selectedHair;
            selectedHair = selectedHair == -1 ? HAIRS.length-1 : selectedHair;

            character.setHair(HAIRS[selectedHair].getA(), new Color(HAIRS[selectedHair].getB()));
        }
        character.setFps(15);
        character.setFrameX(0);
    }

    @Override
    public void render(SpriteBatch batch) {
        float wHeight = Gdx.graphics.getHeight();
        float wWidth = Gdx.graphics.getWidth();

        batch.setColor(new Color(0x222222FF));
        batch.draw(bg, (wWidth / 2) - 200, 0, 400, wHeight);
        batch.setColor(Color.WHITE.cpy());

        String text = "Character Selection";
        float halfTextWidth = font.getBounds(text).width / 2;
        font.setColor(Color.WHITE.cpy());
        font.draw(batch, text, (wWidth / 2) - halfTextWidth, wHeight-60);

        character.render(batch, camera);

        // Render select option for:

        // Hair
        text = "Hair";
        halfTextWidth = font.getBounds(text).width / 2;
        font.draw(batch, text, (wWidth / 2) - halfTextWidth, wHeight-330);

        // Clothes
        text = "Clothes";
        halfTextWidth = font.getBounds(text).width / 2;
        font.draw(batch, text, (wWidth / 2) - halfTextWidth, wHeight-430);

        // Skin
        text = "Skin";
        halfTextWidth = font.getBounds(text).width / 2;
        font.draw(batch, text, (wWidth / 2) - halfTextWidth, wHeight-530);

        // Change arrows
        batch.setColor(Color.WHITE.cpy());
        float textHeight = font.getBounds(text).height;
        batch.draw(arrow, (wWidth / 2) - 180, wHeight-330-textHeight-12, 32f, 32f, 0, 0, 32, 32, false, false);
        batch.draw(arrow, (wWidth / 2) + 148, wHeight-330-textHeight-12, 32f, 32f, 0, 0, 32, 32, true, false);
        batch.draw(arrow, (wWidth / 2) - 180, wHeight-430-textHeight-12, 32f, 32f, 0, 0, 32, 32, false, false);
        batch.draw(arrow, (wWidth / 2) + 148, wHeight-430-textHeight-12, 32f, 32f, 0, 0, 32, 32, true, false);
        batch.draw(arrow, (wWidth / 2) - 180, wHeight-530-textHeight-12, 32f, 32f, 0, 0, 32, 32, false, false);
        batch.draw(arrow, (wWidth / 2) + 148, wHeight-530-textHeight-12, 32f, 32f, 0, 0, 32, 32, true, false);

        // Highlight current CTL
        if (selectedButton != CTL_PLAY) {
            int ctlOffset = selectedButton == CTL_HAIR ? 330 : (selectedButton == CTL_CLOTHES ? 430 : 530);
            batch.draw(bg, (wWidth / 2) - 100, wHeight - ctlOffset - textHeight - 12, 200, 2);
        }

        // Select button
        text = "Play! Play the game!";
        halfTextWidth = font.getBounds(text).width / 2;
        batch.setColor(selectedButton == CTL_PLAY ? Color.BLACK.cpy() : Color.WHITE.cpy());
        batch.draw(bg,
                (wWidth / 2) - halfTextWidth - 12,
                (wHeight - 630 - 12 - font.getBounds(text).height),
                (halfTextWidth * 2) + 24, font.getBounds(text).height + 24);
        font.setColor(selectedButton == CTL_PLAY ? Color.WHITE.cpy() : Color.BLACK.cpy());
        font.draw(batch, text, (wWidth / 2) - halfTextWidth, wHeight-630);

    }

    @Override
    public void update(float delta) {
        // Make the character walk, then show phone, then run with phone
        timeIn += delta;
        if ((timeIn % 7000) < 2000) {
            character.setFrameY(Pedestrian.FRAME_WALK);
        } else if ((timeIn % 7000) < 3000) {
            character.setFrameY(Pedestrian.FRAME_WALK_CELL);
        } else {
            character.setFrameY(Pedestrian.FRAME_RUN_CELL);
        }

        character.updateAnims(delta);
    }

    @Override
    public void onTransitionInStart() { switchingState = false; }

}
