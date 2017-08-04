package com.mkemp.nibbles.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mkemp.nibbles.Nibbles.SCREEN_HEIGHT;
import static com.mkemp.nibbles.Nibbles.SCREEN_WIDTH;

/**
 * Created by mkemp on 7/25/17.
 */

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    static Label scoreLabel;
    private Integer score;

    public Hud(SpriteBatch batch) {

        score = 0;

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        // TODO : Add high scores
        scoreLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(scoreLabel);

        stage.addActor(table);
    }

    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%05d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
