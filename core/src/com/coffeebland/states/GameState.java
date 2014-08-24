package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.HotSpot;
import com.coffeebland.game.carto.Map;
import com.coffeebland.game.carto.Street;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.game.UIOverlay;
import com.coffeebland.game.phone.Phone;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.res.Images;
import com.coffeebland.state.State;
import com.coffeebland.util.ColorUtil;
import com.coffeebland.util.Maybe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dagothig on 8/23/14.
 */
public class GameState extends State<GameState.GameStateInfo> {
    public static final int
            MAX_BATTERY = 5,
            BATTERY_DURATION = 1500,
            MAX_WIFI = 4,
            WIFI_DURATION = 1000,
            STREET_CHANGE_TRANSITION_DURATION = 250,
            WIFI_RANGE = 250,
            WIFI_EXTENDED_RANGE = 500,
            NO_WIFI_DECAL_X = 12,
            NO_WIFI_DECAL_Y = -12;

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
            public void onKeyUp() {
            }
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
            public void onKeyUp() {
            }
            @Override
            public void onKeyIsDown() {
                if (player.hasValue()) {
                    Pedestrian player = GameState.this.player.getValue();
                    player.runRight();
                }
            }
        });

        getInputManager().listenTo(Control.RAISE_CELLPHONE, new InputDispatcher.OnKeyListener() {

            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    player.getValue().raiseCell();
                }
            }

            @Override
            public void onKeyUp() {
                if (player.hasValue()) {
                    player.getValue().lowerCell();
                }
            }

            @Override
            public void onKeyIsDown() {

            }
        });

        getInputManager().listenTo(Control.ENTER, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    float x = player.getValue().getX();
                    Maybe<Street> street = currentStreet.getCurrentTile(x);
                    if (street.hasValue()) {
                        switchToStreet(street.getValue());
                    }
                }
            }

            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });

        getInputManager().listenTo(Control.OPEN_MENU, new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (player.hasValue()) {
                    phone.showPhone(player.getValue(), GameState.this);
                }
            }
            @Override
            public void onKeyUp() {}
            @Override
            public void onKeyIsDown() {}
        });

        setBackgroundColor(new Color(0x7FE6FFFF));
    }

    private Map map;
    private Street currentStreet;
    private Maybe<Street> nextStreet = new Maybe<Street>();
    private Set<Pedestrian> pedestrians = new HashSet<Pedestrian>();
    private Camera camera = new Camera();
    private Maybe<Pedestrian> player = new Maybe<Pedestrian>();
    private UIOverlay uiOverlay = new UIOverlay();
    private int
            remainingWIFI = MAX_WIFI,
            wifiDuration = WIFI_DURATION,
            remainingBattery = MAX_BATTERY,
            batteryDuration = BATTERY_DURATION,
            transitionDurationRemaining;
    private boolean transitionLeaving, hasWifi = false;
    private Phone phone;
    private Texture whitePixel = ColorUtil.whitePixel(), noWifi = Images.get("sprites/character/no_wifi.png");
    private Color overlayColor = Color.BLACK.cpy();

    public void setCurrentStreet(Street street) {
        currentStreet = street;
        currentStreet.initTexture();
        camera.setCurrentStreet(street);
    }

    public void switchToStreet(Street street) {
        nextStreet = new Maybe<Street>(street);
        leaveStreet();
    }
    public void leaveStreet() {
        if (player.hasValue()) {
            player.getValue().animLeave(STREET_CHANGE_TRANSITION_DURATION);
        }
        transitionDurationRemaining = STREET_CHANGE_TRANSITION_DURATION;
        transitionLeaving = true;
        Gdx.input.setInputProcessor(null);
    }
    public void enterStreet() {
        if (player.hasValue()) {
            player.getValue().animEnter(STREET_CHANGE_TRANSITION_DURATION);
        }
        transitionDurationRemaining = STREET_CHANGE_TRANSITION_DURATION;
        transitionLeaving = false;
    }

    @Override
    public boolean shouldBeReused() {
        return false;
    }

    @Override
    public void update(float delta) {
        for (Pedestrian pedestrian : pedestrians) {
            pedestrian.update(delta);
            // Cap the position to the street
            pedestrian.setX(
                    Math.max(currentStreet.getStart() + Pedestrian.FRAME_WIDTH / 2,
                            Math.min(currentStreet.getEnd() - Pedestrian.FRAME_WIDTH / 2,
                                    pedestrian.getX()
                            )
                    )
            );
        }
        uiOverlay.update(remainingWIFI, remainingBattery);

        if (player.hasValue()) {
            Pedestrian player = this.player.getValue();
            camera.setPosition(player.getX());

            if (transitionDurationRemaining != 0 && nextStreet.hasValue()) {
                if (transitionDurationRemaining > 0) {
                    transitionDurationRemaining -= delta;
                }
                if (transitionDurationRemaining <= 0) {
                    transitionDurationRemaining = 0;
                    if (transitionLeaving) {
                        player.setX(nextStreet.getValue().getPosComingFrom(currentStreet));
                        setCurrentStreet(nextStreet.getValue());
                        enterStreet();
                    } else {
                        nextStreet = new Maybe<Street>();
                        Gdx.input.setInputProcessor(getInputManager());
                    }
                }
            }

            if (batteryDuration > 0) {
                batteryDuration -= delta;
            }
            while (batteryDuration <= 0) {
                batteryDuration += BATTERY_DURATION;
                remainingBattery--;
            }

            hasWifi = currentStreet.getDistanceToClosestWifi(player.getX()) < (player.isHoldingCell() ? WIFI_EXTENDED_RANGE : WIFI_RANGE);
            if (wifiDuration > 0) {
                wifiDuration -= delta;
            }
            while (wifiDuration <= 0) {
                wifiDuration += WIFI_DURATION;
                if (hasWifi)
                    remainingWIFI = Math.min((remainingWIFI + 1), MAX_WIFI);
                else
                    remainingWIFI--;

                if (remainingWIFI <= 0) {
                    switchToState(GameOverState.class, Color.BLACK.cpy(), TRANSITION_MEDIUM);
                }
            }
        }

        phone.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (currentStreet != null)
            currentStreet.render(batch, camera);
        for (Pedestrian pedestrian : pedestrians) {
            pedestrian.render(batch, camera);
        }

        if (!hasWifi && player.hasValue()) {
            Pedestrian player = this.player.getValue();
            batch.draw(noWifi, (player.getX() - camera.getPosition() + NO_WIFI_DECAL_X) * HotSpot.UPSCALE_RATE, (player.getY() + Pedestrian.FRAME_HEIGHT + NO_WIFI_DECAL_Y) * HotSpot.UPSCALE_RATE, noWifi.getWidth() * HotSpot.UPSCALE_RATE, noWifi.getHeight() * HotSpot.UPSCALE_RATE);
        }

        if (transitionDurationRemaining > 0) {
            overlayColor.a = transitionDurationRemaining / (float)STREET_CHANGE_TRANSITION_DURATION;
            if (transitionLeaving)
                overlayColor.a = 1 - overlayColor.a;
            batch.setColor(overlayColor);
            batch.draw(whitePixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.setColor(Color.WHITE);
        }

        uiOverlay.render(batch);
        phone.render(batch);
    }

    @Override
    public void onTransitionInStart(GameStateInfo info) {
        pedestrians = new HashSet<Pedestrian>();

        player = new Maybe<Pedestrian>(info.player);
        pedestrians.add(info.player);
        info.player.setX(info.position);
        info.player.setY((Street.PEDESTRIANS_START_DISTANCE + Street.PEDESTRIANS_END_DISTANCE) / 2);
        //info.player.setY((float) (Math.random() * (Street.PEDESTRIANS_END_DISTANCE - Street.PEDESTRIANS_START_DISTANCE) + Street.PEDESTRIANS_START_DISTANCE));
        camera.setCurrentStreet(info.street);
        camera.setPosition(info.position);
        map = info.map;
        setCurrentStreet(info.street);
        System.out.println("initialized");

        remainingBattery = MAX_BATTERY;
        batteryDuration = BATTERY_DURATION;
        remainingWIFI = MAX_WIFI;
        wifiDuration = WIFI_DURATION;
    }

    @Override
    public void onTransitionInStart() {
        System.out.println("booya");
        if (!player.hasValue()) {
            throw new RuntimeException("Cannot transition to game state without passing player");
        }
    }

    @Override
    public String musicRef() {
        return "music/game.mp3";
    }

    public static class GameStateInfo {
        public Pedestrian player;
        public Map map;
        public Street street;
        public float position;
    }
}
