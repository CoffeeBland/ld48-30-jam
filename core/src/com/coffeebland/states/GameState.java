package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.carto.Map;
import com.coffeebland.game.carto.Street;
import com.coffeebland.game.Camera;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.game.UIOverlay;
import com.coffeebland.game.phone.Phone;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
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
            MAX_WIFI = 4,
            STREET_CHANGE_TRANSITION_DURATION = 250;

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

        setBackgroundColor(new Color(0x544A40FF));
    }

    private Map map;
    private Street currentStreet;
    private Maybe<Street> nextStreet = new Maybe<Street>();
    private Set<Pedestrian> pedestrians;
    private Camera camera = new Camera();
    private Maybe<Pedestrian> player;
    private UIOverlay uiOverlay = new UIOverlay();
    private int remainingWIFI = MAX_WIFI, remainingBattery = MAX_BATTERY, transitionDurationRemaining;
    private boolean transitionLeaving;
    private Phone phone;
    private Texture whitePixel = ColorUtil.whitePixel();
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
        }


        phone.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        currentStreet.render(batch, camera);
        for (Pedestrian pedestrian : pedestrians) {
            pedestrian.render(batch, camera);
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
        public Map map;
        public Street street;
        public float position;
    }
}
