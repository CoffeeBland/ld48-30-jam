package com.coffeebland;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.state.StateManager;
import com.coffeebland.states.LogoState;

public class HotSpot extends ApplicationAdapter {
    public static int UPSCALE_RATE = 1;

	SpriteBatch batch;
    long time;
    StateManager stateManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        batch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		batch.enableBlending();
        time = System.nanoTime();
        stateManager = new StateManager(LogoState.class);
	    updateScaleRate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

	@Override
	public void render () {
        long current = System.nanoTime();
        stateManager.update((current - time) / 1000000f);
        time = current;

        Color col = stateManager.getBackgroundColor();
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stateManager.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        batch = new SpriteBatch();
        batch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        batch.enableBlending();
        updateScaleRate(width, height);
    }

    public void updateScaleRate(int width, int height) {
        UPSCALE_RATE = Math.max(Math.min(width, height) / 320, 1);
    }
}
