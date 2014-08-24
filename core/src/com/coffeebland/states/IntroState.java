package com.coffeebland.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.game.Pedestrian;
import com.coffeebland.game.carto.Street;
import com.coffeebland.game.phone.AppChat;
import com.coffeebland.game.phone.AppHome;
import com.coffeebland.game.phone.Phone;
import com.coffeebland.state.State;

/**
 * Created by kiasaki on 23/08/2014.
 */
public class IntroState extends State<IntroState.IntroStateInfo> {
    public IntroState() {
        phone = new Phone();
    }

    private Pedestrian character;
    private Phone phone;
    private AppChat app;
    private float timeIn = 0;
    private boolean[] messagesSent = { false, false, false, false, false };

    public void switchToGame() {
        GameState.GameStateInfo info = new GameState.GameStateInfo();
        info.player = character;
        info.street = new Street(0, 0, Street.TILE_SIZE * 32, true, 1337);
        info.position = info.street.getStart();
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
        timeIn += delta;

        if (timeIn > 5000 && messagesSent[0] == false) {
            messagesSent[0] = true;
            app.addMessage("Frederik", "Hey, dude, just NEED to download this app called Tindoune. Text me what you think!");
        }
        if (timeIn > 15000 && messagesSent[1] == false) {
            messagesSent[1] = true;
            app.addMessage("Frederik", "Waiting...");
        }
        if (timeIn > 20000 && messagesSent[2] == false) {
            messagesSent[2] = true;
            app.addMessage("Me", "Okay okay, getting it ...");
        }

        if (timeIn > 25000 && messagesSent[3] == false) {
            messagesSent[3] = true;
            phone.openApp(new AppHome());
            //app = new TinderApp();
        }

        phone.update(delta);
    }

    @Override
    public void onTransitionInStart(IntroStateInfo info) {
        timeIn = 0;

        character = info.character;
        phone.setHandColor(character.getSkinColor());
        phone.showPhone(character, this);
        app = new AppChat();
        phone.openApp(app);
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
