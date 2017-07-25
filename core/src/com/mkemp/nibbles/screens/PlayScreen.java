package com.mkemp.nibbles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mkemp.nibbles.Nibbles;
import com.mkemp.nibbles.sprites.Head;

/**
 * Created by mkemp on 7/25/17.
 */

public class PlayScreen implements Screen {

    private Nibbles game;
    private Texture background;
    private Head head;


    public PlayScreen(Nibbles game) {
        this.game = game;

        background = new Texture("background.png");
        head = new Head(new Texture("yoshi.png"), 100f, 1f);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        head.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
