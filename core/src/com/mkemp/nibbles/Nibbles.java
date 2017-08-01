package com.mkemp.nibbles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkemp.nibbles.screens.PlayScreen;

public class Nibbles extends Game {

	public static final int WORLD_WIDTH = 242;
	public static final int WORLD_HEIGHT = 242;
	public static final float PPM = 100;

	public SpriteBatch batch;

	private AssetManager assetManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("yoshi.png", Texture.class);
		assetManager.finishLoading();

		setScreen(new PlayScreen(this, assetManager));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
		batch.dispose();
	}
}
