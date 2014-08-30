package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.coffeebland.HotSpot;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.input.ClickManager;
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

        setBackgroundColor(new Color(0x222222FF));

        bg = ColorUtil.whitePixel();
        font = FontUtil.normalFont(18);
        arrow = new Texture("sprites/arrow.png");

        camera = new Camera();
        camera.setPosition(0);

        if (HAIRS == null) {
            Class tupleClass = new Tuple<String, Integer>("", 1).getClass();
            HAIRS = (Tuple<String, Integer>[])Array.newInstance(tupleClass, 8);
            HAIRS[0] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0x050510FF);
            HAIRS[1] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0x888888FF);
            HAIRS[2] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xFFFFFFFF);
            HAIRS[3] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xAA8833FF);
            HAIRS[4] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xEEAA11FF);
            HAIRS[5] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xFFEE88FF);
            HAIRS[6] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0xFF8800FF);
            HAIRS[7] = new Tuple<String, Integer>("sprites/character/char_hair_short.png", 0x4477DDFF);
        }

        character = new Pedestrian(
                "sprites/character/char_male.png", new Color(SKINS[SKINS.length-1]),
                "sprites/character/char_male_clothes1.png",
                "sprites/character/char_hair_short.png", new Color(HAIRS[0].getB()),
                0, (Gdx.graphics.getHeight()-250) / HotSpot.UPSCALE_RATE
        );

        character.setFrameY(Pedestrian.FRAME_WALK);
        character.setFps(15);

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

        clickManager = new ClickManager(getInputManager());
        clickManager.addButton(new ClickManager.OnClickListener(arrowHairLeft = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 0;
                change(-1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(arrowHairRight = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 0;
                change(1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(arrowClothesLeft = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 1;
                change(-1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(arrowClothesRight = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 1;
                change(1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(arrowSkinLeft = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 2;
                change(-1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(arrowSkinRight = new Rectangle(0, 0, arrow.getWidth(), arrow.getHeight())) {
            @Override
            public void onClick() {
                selectedButton = 2;
                change(1);
            }
        });
        clickManager.addButton(new ClickManager.OnClickListener(start = new Rectangle(0, 0, 24, 24)) {
            @Override
            public void onClick() {
                selectedButton = 3;
                IntroState.IntroStateInfo info = new IntroState.IntroStateInfo(character);
                switchToState(IntroState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM, info);
            }
        });
    }

    private Texture bg;
    private Texture arrow;
    private BitmapFont font;
    private Camera camera;
    private Pedestrian character;
    private float timeIn;
    private int selectedButton;
    private int selectedSkin;
    private int selectedHair;
    private ClickManager clickManager;
    private Rectangle arrowHairLeft, arrowHairRight, arrowClothesLeft, arrowClothesRight, arrowSkinLeft, arrowSkinRight, start;

    @Override
    public boolean shouldBeReused() { return false; }

    @Override
    public String musicRef() {
        return "music/menu.mp3";
    }

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
        HotSpot.UPSCALE_RATE = 2;

        float wHeight = Gdx.graphics.getHeight();
        float wWidth = Gdx.graphics.getWidth();

        camera.setPosition(0);
        character.setY((wHeight-250) / HotSpot.UPSCALE_RATE);

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
        float aW = arrow.getWidth(), aH = arrow.getHeight();
        float x = (wWidth / 2) - 180,
                x2 = (wWidth / 2) + 148,
                y = wHeight-330-textHeight-12;
        arrowHairLeft.x = x;
        arrowHairLeft.y = y;
        batch.draw(arrow, x, y, aW, aH, 0, 0, (int)aW, (int)aH, false, false);

        arrowHairRight.x = x2;
        arrowHairRight.y = y;
        batch.draw(arrow, x2, y, aW, aH, 0, 0, (int)aW, (int)aH, true, false);

        y = wHeight-430-textHeight-12;
        arrowClothesLeft.x = x;
        arrowClothesLeft.y = y;
        batch.draw(arrow, x, y, aW, aH, 0, 0, (int)aW, (int)aH, false, false);

        arrowClothesRight.x = x2;
        arrowClothesRight.y = y;
        batch.draw(arrow, x2, y, aW, aH, 0, 0, (int)aW, (int)aH, true, false);

        y = wHeight-530-textHeight-12;
        arrowSkinLeft.x = x;
        arrowSkinLeft.y = y;
        batch.draw(arrow, x, y, aW, aH, 0, 0, (int)aW, (int)aH, false, false);

        arrowSkinRight.x = x2;
        arrowSkinRight.y = y;
        batch.draw(arrow, x2, y, aW, aH, 0, 0, (int)aW, (int)aH, true, false);

        // Highlight current CTL
        if (selectedButton != CTL_PLAY) {
            int ctlOffset = selectedButton == CTL_HAIR ? 330 : (selectedButton == CTL_CLOTHES ? 430 : 530);
            batch.draw(bg, (wWidth / 2) - 100, wHeight - ctlOffset - textHeight - 12, 200, 2);
        }

        // Select button
        text = "Play! Play the game!";
        halfTextWidth = font.getBounds(text).width / 2;
        batch.setColor(selectedButton == CTL_PLAY ? Color.BLACK.cpy() : Color.WHITE.cpy());
        float btnPlayX = (wWidth / 2) - halfTextWidth - 12,
                btnPlayY = (wHeight - 630 - 12 - font.getBounds(text).height),
                btnPlayW = (halfTextWidth * 2) + 24,
                btnPlayH = font.getBounds(text).height + 24;
        start.x = btnPlayX;
        start.y = btnPlayY;
        start.width = btnPlayW;
        start.height = btnPlayH;
        batch.draw(bg,
                btnPlayX,
                btnPlayY,
                btnPlayW,
                btnPlayH
        );
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
}
