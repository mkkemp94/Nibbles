package com.mkemp.nibbles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkemp.nibbles.screens.PlayScreen;

public class Nibbles extends Game {

	public static final int WORLD_WIDTH = 242;
	public static final int WORLD_HEIGHT = 242;
	public static final float PPM = 100;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
