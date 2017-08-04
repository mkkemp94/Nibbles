package com.mkemp.nibbles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mkemp.nibbles.sprites.Fruit;
import com.mkemp.nibbles.sprites.Player;
import com.mkemp.nibbles.tools.B2WorldCreator;
import com.mkemp.nibbles.tools.WorldContactListener;

import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.WORLD_HEIGHT;
import static com.mkemp.nibbles.Nibbles.WORLD_WIDTH;

/**
 * Created by mkemp on 7/25/17.
 */

public class PlayScreen implements Screen {

    private Nibbles game;
    private AssetManager assetManager;

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

    private Player player;
    private Fruit fruit;

    private GameOverHud gameOverHud;

    public PlayScreen(Nibbles game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

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
        world.setContactListener(new WorldContactListener());
        worldCreator = new B2WorldCreator(this);

        // Debug rendered to show debug lines
        debugRenderer = new Box2DDebugRenderer();

        // Create player and first fruit
        player = new Player(this);
        fruit = new Fruit(this, 88 / PPM, 88 / PPM);

        // Game over hud that displays when we hit a wall
        // TODO: Maybe add objects that snake can crash into when he gets big enough?
        gameOverHud = new GameOverHud(game.batch);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render tiled map with updated changes.
        tiledMapRenderer.render();

        // Only render what the camera can see.
        game.batch.setProjectionMatrix(gameCam.combined);

        // Render debug lines.
        debugRenderer.render(world, gameCam.combined);

        // Draw objects on screen.
        game.batch.begin();
        player.draw(game.batch);
        fruit.draw(game.batch);
        game.batch.end();

        // Show game over message if snake is dead.
        if (player.snakeIsDead()) {
            gameOverHud.stage.draw();

            // Start new game if we touch the screen.
            if (Gdx.input.justTouched()) {
                // TODO : Move to another method. Reset score etc.
                game.setScreen(new PlayScreen((Nibbles) game, assetManager));
                dispose();
            }
        }
    }

    /**
     * Handles all calculations so render can draw.
     */
    private void update(float dt) {
        handleInput();

        // Update world.
        world.step(1/60f, 6, 2);

        // Update position of player.
        if (!player.snakeIsDead()) {
            player.update(dt);
        }

        // Update fruit status.
        fruit.update();

        // Update camera.
        gameCam.update();
        tiledMapRenderer.setView(gameCam);
    }

    /**
     * Handles input by the user - change snake direction.
     */
    private void handleInput() {

        // TODO: I shouldn't be able to flip direction.
        if (!player.snakeIsDead()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                Gdx.app.log("Key Pressed", "Up");
                player.setDirection(90);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                Gdx.app.log("Key Pressed", "Down");
                player.setDirection(270);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                Gdx.app.log("Key Pressed", "Left");
                player.setDirection(180);

            } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                Gdx.app.log("Key Pressed", "Right");
                player.setDirection(0);

            }
        }
    }

    /**
     * Destroy
     */
    public void addToTail() {
        Gdx.app.log("Screen", "Calling player addToTail()");
        player.addToTail();
    }


    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public AssetManager getAssetManager() { return assetManager; }

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
