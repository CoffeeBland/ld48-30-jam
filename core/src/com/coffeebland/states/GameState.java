package com.coffeebland.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.carto.Street;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.game.UIOverlay;
import com.coffeebland.game.phone.Phone;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.state.State;
import com.coffeebland.util.Maybe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class GameState extends State<GameState.GameStateInfo> {
    public static final int
            MAX_BATTERY = 5,
            MAX_WIFI = 4;

    public GameState() {
        super();

        phone = new Phone();

        getInputManager().listenTo(Control.MOVE_LEFT, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    Pedestrian player = GameState.this.player.getValue();
                    player.runLeft();
                }
            }
            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {
                if (player.hasValue()) {
                    Pedestrian player = GameState.this.player.getValue();
                    player.runLeft();
                }
            }
        });
        getInputManager().listenTo(Control.MOVE_RIGHT, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    Pedestrian player = GameState.this.player.getValue();
                    player.runRight();
                }
            }
            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {
                if (player.hasValue()) {
                    Pedestrian player = GameState.this.player.getValue();
                    player.runRight();
                }
            }
        });
        getInputManager().listenTo(Control.OPEN_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    phone.showPhone(player.getValue());
                }
            }
            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });

        setBackgroundColor(new Color(0x544A40FF));
    }

    private Street currentStreet;
    private Set<Pedestrian> pedestrians;
    private Camera camera = new Camera();
    private Maybe<Pedestrian> player;
    private UIOverlay uiOverlay = new UIOverlay();
    private int remainingWIFI = MAX_WIFI, remainingBattery = MAX_BATTERY;
    private Phone phone;

    @Override
    public boolean shouldBeReused() {
        return false;
    }

    @Override
    public void update(float delta) {
        for (Pedestrian pedestrian : pedestrians) {
            pedestrian.update(delta);
        }
        uiOverlay.update(3, 2);
        if (player.hasValue()) {
            camera.setPosition(player.getValue().getX());
        }
        phone.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Pedestrian pedestrian : pedestrians) {
            pedestrian.render(batch, camera);
        }
        uiOverlay.render(batch);
        phone.render(batch);
    }

    @Override
    public void onTransitionInStart(GameStateInfo info) {
        pedestrians = new HashSet<Pedestrian>();

        this.player = new Maybe<Pedestrian>(info.player);
        pedestrians.add(info.player);
        info.player.setX(info.position);
        info.player.setY((float) (Math.random() * (Street.PEDESTRIANS_START_DISTANCE - Street.PEDESTRIANS_END_DISTANCE) + Street.PEDESTRIANS_END_DISTANCE));
        camera.setPosition(info.position);
        currentStreet = info.street;

        remainingBattery = MAX_BATTERY;
        remainingWIFI = MAX_WIFI;
    }

    @Override
    public void onTransitionInStart() {
        if (!player.hasValue()) {
            throw new RuntimeException("Cannot transition to game state without passing player");
        }
    }

    public static class GameStateInfo {
        public Pedestrian player;
        public Street street;
        public float position;
    }
}
