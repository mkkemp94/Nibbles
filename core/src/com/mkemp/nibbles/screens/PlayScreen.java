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
import com.mkemp.nibbles.sprites.Snake;
import com.mkemp.nibbles.tools.B2WorldCreator;

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
    private final Texture yoshiTexture;

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
        player = new Snake(world, this, yoshiTexture); //, 72f / PPM, 32f / PPM);
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
        game.batch.end();

    }

    /**
     * Handles all calculations so render can draw.
     */
    private void update(float dt) {
        handleInput();

        // For world movement.
        world.step(1/60f, 6, 2);

        // Update position of sprite.
        player.update(dt);

        gameCam.update();
        tiledMapRenderer.setView(gameCam);
    }

    /**
     * Handles input by the user.
     */
    private void handleInput() {
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) &&
//                player.body.getLinearVelocity().y <= 0.1)
//            player.body.applyLinearImpulse(new Vector2(0, 1f), player.body.getWorldCenter(), true);
//        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) &&
//                player.body.getLinearVelocity().y >= -0.1)
//            player.body.applyLinearImpulse(new Vector2(0, -1f), player.body.getWorldCenter(), true);
//        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) &&
//                player.body.getLinearVelocity().x >= -2)
//            player.body.applyLinearImpulse(new Vector2(-0.4f, 0), player.body.getWorldCenter(), true);
//        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) &&
//                player.body.getLinearVelocity().x <= 2)
//            player.body.applyLinearImpulse(new Vector2(0.4f, 0), player.body.getWorldCenter(), true);

        float currentx = player.body.getPosition().x;
        float currenty = player.body.getPosition().y;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.body.setTransform(currentx, currenty + 16 / PPM, 90);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.body.setTransform(currentx, currenty - 16 / PPM, 270);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.body.setTransform(currentx - 16 / PPM, currenty, 180);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.body.setTransform(currentx + 16 / PPM, currenty, 0);
        }

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
        yoshiTexture.dispose();
    }
}
