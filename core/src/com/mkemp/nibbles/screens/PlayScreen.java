package com.mkemp.nibbles.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.nibbles.Nibbles;
import com.mkemp.nibbles.sprites.Head;
import com.mkemp.nibbles.tools.B2WorldCreator;

import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.WORLD_HEIGHT;
import static com.mkemp.nibbles.Nibbles.WORLD_WIDTH;

/**
 * Created by mkemp on 7/25/17.
 */

public class PlayScreen implements Screen {

    private Nibbles game;
    private Texture background;

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

    private Head player;

    public PlayScreen(Nibbles game) {
        this.game = game;

        //background = new Texture("background.png");

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
//        worldCreator = new B2WorldCreator(this);

        player = new Head(world, new Texture("yoshi.png"), 32f, 32f);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2,
                    rectangle.getY() + rectangle.getHeight() / 2);
            body = world.createBody(bodyDef);

            shape.setAsBox((rectangle.getWidth() / 2) / PPM, (rectangle.getHeight() / 2) / PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render tiled map with updated changes.
        tiledMapRenderer.render();

        // This should be called whenever the camera is altered.
        // Combine the camera's view and projection matrices.
        // Convert world coordinates to camera screen coordinates.
        // --- Render what the camera can see only.
        //game.batch.setProjectionMatrix(gameCam.combined);

        debugRenderer.render(world, gameCam.combined);
        game.batch.begin();
        //game.batch.draw(background, 0, 0);
        player.draw(game.batch);
        game.batch.end();
    }

    /**
     * Handles all calculations so render can draw.
     */
    private void update() {
        handleInput();

        world.step(1/60f, 6, 2);

        gameCam.update();
        tiledMapRenderer.setView(gameCam);
    }

    /**
     * Handles input by the user.
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) &&
                player.body.getLinearVelocity().y <= 0.1)
            player.body.applyLinearImpulse(new Vector2(0, 1f), player.body.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) &&
        player.body.getLinearVelocity().y >= -0.1)
            player.body.applyLinearImpulse(new Vector2(0, -1f), player.body.getWorldCenter(), true);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) &&
        player.body.getLinearVelocity().x >= -2)
            player.body.applyLinearImpulse(new Vector2(-0.4f, 0), player.body.getWorldCenter(), true);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) &&
                player.body.getLinearVelocity().x <= 2)
            player.body.applyLinearImpulse(new Vector2(0.4f, 0), player.body.getWorldCenter(), true);

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        debugRenderer.dispose();
        world.dispose();
    }
}
