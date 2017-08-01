package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Fruit extends Sprite {

    private World world;
    private Body body;

    public Fruit(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();

        // Create this body piece
        createFruit(x, y);

        setBounds(0, 0, 16 / PPM, 16 / PPM);
        Texture texture = screen.getAssetManager().get("yoshi.png", Texture.class);
        setRegion(texture);
    }

    /**
     * Create this fruit.
     */
    private void createFruit(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);

        setPosition(body.getPosition().x, body.getPosition().y);
    }
}
