package com.coffeebland;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.coffeebland.util.StateManager;

public class HotSpot extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    long time;
    StateManager stateManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
        time = System.currentTimeMillis();
        stateManager = new StateManager();
	}

	@Override
	public void render () {
        long current = System.currentTimeMillis();
        stateManager.update((current - time));
        time = current;

        Color col = stateManager.getBackgroundColor();
        Gdx.gl.glClearColor(col.r, col.g, col.b, col.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stateManager.render();
        batch.end();
    }
}
