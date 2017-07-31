package com.mkemp.nibbles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.nibbles.Nibbles;
import com.mkemp.nibbles.scenes.GameOverHud;
import com.mkemp.nibbles.sprites.Snake;
import com.mkemp.nibbles.tools.B2WorldCreator;

import java.util.ArrayList;

import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.WORLD_HEIGHT;
import static com.mkemp.nibbles.Nibbles.WORLD_WIDTH;

/**
 * Created by mkemp on 7/25/17.
 */

public class PlayScreen implements Screen {

    private Nibbles game;

    // Camera
    private OrthographicCamera gameCam;
    private Viewport gamePort;

    // Tiled Map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private B2WorldCreator worldCreator;

    private Snake player;
    private Texture yoshiTexture;

    private GameOverHud gameOverHud;

    private ArrayList<Snake> body;
    boolean availableForInput;

    public PlayScreen(Nibbles game) {
        this.game = game;

        // Create camera
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(WORLD_WIDTH / PPM, WORLD_HEIGHT / PPM, gameCam);

        // Render tile map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("snake_world.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        // By default this centers around 0, 0... make it world center.
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Construct a world
        world = new World(new Vector2(0, 0), false);
        //world.setContactListener(new WorldContactListener());

        debugRenderer = new Box2DDebugRenderer();
        worldCreator = new B2WorldCreator(this);

        yoshiTexture = new Texture("yoshi.png");
        player = new Snake(this, 0, yoshiTexture, 72 / PPM, 40 / PPM);

//        Texture eggTexture = new Texture("egg.png");
//        body = new ArrayList<Snake>();
//        body.add(player);
//        body.add(new Snake(this, 1, eggTexture, 56 / PPM, 40 / PPM));
        //body.add(new Snake(this, 2, eggTexture, 40 / PPM, 40 / PPM));

        gameOverHud = new GameOverHud(game.batch);
        availableForInput = true;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render tiled map with updated changes.
        tiledMapRenderer.render();

        // Only render what the camera can see only.
        game.batch.setProjectionMatrix(gameCam.combined);

        // Render debug lines.
        debugRenderer.render(world, gameCam.combined);

        game.batch.begin();
        player.draw(game.batch);

//        for (Snake snake : body)
//            snake.draw(game.batch);
        game.batch.end();

        // Set game over message if snake is dead.
        if (player.snakeIsDead()) {
            gameOverHud.stage.draw();
            if (Gdx.input.justTouched()) {
                game.setScreen(new PlayScreen((Nibbles) game));
                dispose();
            }
        }
    }

    /**
     * Handles all calculations so render can draw.
     */
    private void update(float dt) {
        handleInput();

        // For world movement.
        world.step(1/60f, 6, 2);

        // Update position of sprite.
        if (!player.snakeIsDead()) {
            player.update(dt);
//            for (Snake snake : body) {
//                snake.update(dt);
//            }
        }

        gameCam.update();
        tiledMapRenderer.setView(gameCam);
    }

    int moveTimer;

    /**
     * Handles input by the user - change snake direction.
     */
    private void handleInput() {


        if (!player.snakeIsDead() && availableForInput) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                Gdx.app.log("Key Pressed", "Up");
                player.setDirection(90);
//                for (Snake snake : body) {
//                    snake.addCommandToQueue(90);
//                }
                setAvailableForInput(false);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                Gdx.app.log("Key Pressed", "Down");
                player.setDirection(270);
//                for (Snake snake : body) {
//                    snake.addCommandToQueue(270);
//                }
                setAvailableForInput(false);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                Gdx.app.log("Key Pressed", "Left");
                player.setDirection(180);
//                for (Snake snake : body) {
//                    snake.addCommandToQueue(180);
//                }
                setAvailableForInput(false);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                Gdx.app.log("Key Pressed", "Right");
                player.setDirection(0);
//                for (Snake snake : body) {
//                    snake.addCommandToQueue(0);
//                }
                setAvailableForInput(false);

            }
//            else {
//                //Gdx.app.log("No Key Pressed", "Just go straight");
//
//                for (Snake snake : body) {
//                    if (snake.getMoveTimer() >= 1)
//                        snake.addCommandToQueue(-1);
//                }
//                setAvailableForInput(false);
//            }
        }
    }

    public void setAvailableForInput(boolean status) {
        availableForInput = status;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
    }
}
