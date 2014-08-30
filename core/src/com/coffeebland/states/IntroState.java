package com.coffeebland.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Candidate;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.game.carto.Map;
import com.coffeebland.game.phone.AppChat;
import com.coffeebland.game.phone.AppHome;
import com.coffeebland.game.phone.AppTindun;
import com.coffeebland.game.phone.Phone;
import com.coffeebland.input.ClickManager;
import com.coffeebland.input.Control;
import com.coffeebland.input.InputDispatcher;
import com.coffeebland.state.State;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class IntroState extends State<IntroState.IntroStateInfo> {
    private static final int
        initialDuration = 0,
        msg1Duration = 10000,
        msg2Duration = 2000,
        msg3Duration = 2500,
        appTransitionDuration = 750,
        tinderDuration = -1,
        msg4Duration = 2000,
        msg5Duration = 3000;

    public static abstract class IntroEvent {
        public IntroEvent(int duration) {
            this.duration = duration;
        }

        public abstract void onTrigger();
        public int duration;
    }

    public IntroState() {
        setBackgroundColor(new Color(0x222222FF));
        phone = new Phone();

        InputDispatcher.OnKeyListener skipListener = new InputDispatcher.OnKeyListener() {
            @Override
            public void onKeyDown() {
                if (events.isEmpty()) {
                    currentTime = -1;
                    switchToGame();
                    return;
                } else {
                    IntroEvent event = events.remove();
                    event.onTrigger();
                    if (event.duration == -1) {
                        currentTime = -1;
                        return;
                    } else {
                        currentTime += event.duration;
                    }
                }
            }

            @Override
            public void onKeyUp() {

            }

            @Override
            public void onKeyIsDown() {

            }
        };
        getInputManager().listenTo(Control.SKIP_INTRO1, skipListener);
        getInputManager().listenTo(Control.SKIP_INTRO2, skipListener);
        getInputManager().listenTo(Control.SKIP_INTRO3, skipListener);
    }

    private Queue<IntroEvent> events;

    private Pedestrian character;
    private Phone phone;
    private AppChat appChat;
    private AppTindun appTindun;
    private AppHome appHome;
    private float currentTime;
    private InputDispatcher input;
    private ClickManager clickMgr;

    public void switchToGame() {
        GameState.GameStateInfo info = new GameState.GameStateInfo();
        info.player = character;
        info.map = Map.getMap();
        info.street = info.map.getStreets().get(0);
        info.position = 0;
        switchToState(GameState.class, Color.WHITE.cpy(), TRANSITION_SHORT, info);
    }

    @Override
    public boolean shouldBeReused() {
        return true;
    }

    @Override
    public void render(SpriteBatch batch) {
        phone.render(batch);
    }

    @Override
    public void update(float delta) {
        if (currentTime != -1) {
            currentTime -= delta;
            while (currentTime < 0) {
                if (events.isEmpty()) {
                    currentTime = -1;
                    switchToGame();
                    break;
                } else {
                    IntroEvent event = events.remove();
                    event.onTrigger();
                    if (event.duration == -1) {
                        currentTime = -1;
                        break;
                    } else {
                        currentTime += event.duration;
                    }
                }
            }
        }

        phone.update(delta);
    }

    @Override
    public void onTransitionInStart(IntroStateInfo info) {
        currentTime = 0;
        character = info.character;
        phone.showPhone(character, this);
        appChat = new AppChat();
        Candidate.SELECTED_CANDIDATE = Candidate.POP;
        appTindun = new AppTindun(Candidate.SELECTED_CANDIDATE);
        appHome = new AppHome();
        input = new InputDispatcher();
        clickMgr = new ClickManager(input);

        events = new ArrayDeque<IntroEvent>();

        events.add(new IntroEvent(initialDuration) { public void onTrigger() {
            phone.openApp(appChat);
        }});
        events.add(new IntroEvent(msg1Duration) { public void onTrigger() {
            appChat.addMessage("Frederik", "Hey, dude, today's the big Tindun event, select a complete stranger and meet them at 9:00 P.M. for a date. You HAVE to participate, I'm sick of seeing you alone!");
        }});
        events.add(new IntroEvent(msg2Duration) { public void onTrigger() {
            appChat.addMessage("Frederik", "Waiting...");
        }});
        events.add(new IntroEvent(msg3Duration) { public void onTrigger() {
            appChat.clearMessages();
            appChat.addMessage("Frederik", "Waiting...");
            appChat.addMessage("Me", "Okay okay, getting on it ...");
        }});

        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            appHome.focus(-1);
            phone.openApp(appHome);
        }});
        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            appHome.focus(1);
        }});
        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            appHome.focus(-1);
        }});

        events.add(new IntroEvent(tinderDuration) { public void onTrigger() {
            appTindun.setListener(new AppTindun.OnCandidateSelected() {
                @Override
                public void candidateSelected(Candidate candidate) {
                    currentTime = 0;
                    Candidate.SELECTED_CANDIDATE = candidate;
                }
            });
            for (ClickManager.OnClickListener listener : appTindun.getListeners()) {
                clickMgr.addButton(listener);
            }
            Gdx.input.setInputProcessor(input);
            phone.openApp(appTindun);
        }});

        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            Gdx.input.setInputProcessor(getInputManager());
            phone.openApp(appHome);
        }});
        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            appHome.focus(2);
        }});
        events.add(new IntroEvent(appTransitionDuration) { public void onTrigger() {
            appHome.focus(-1);
        }});

        events.add(new IntroEvent(msg4Duration) { public void onTrigger() {
            appChat.clearMessages();
            phone.openApp(appChat);
            appChat.addMessage("me", "There's this \"" + Candidate.SELECTED_CANDIDATE.getName() + "\" person, think I'm gonna go check it out.");
        }});
        events.add(new IntroEvent(msg5Duration) { public void onTrigger() {
            appHome.focus(-1);
            appChat.addMessage("Frederik", "Nice, wish you good luck.");
        }});
    }

    @Override
    public void onTransitionInStart() {
        if (character == null) {
            throw new RuntimeException("Character must be passed to Intro State");
        }
    }

    @Override
    public String musicRef() {
        return "music/menu.mp3";
    }

    public static class IntroStateInfo {
        public IntroStateInfo(Pedestrian character) {
            this.character = character;
        }

        public Pedestrian character;
    }
}
