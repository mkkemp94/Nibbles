package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Head extends Snake {

    public World world;
    public Body body;

    private boolean collided;


    public Head(World world, Texture texture, float x, float y) {
        super(texture, x, y);

        this.world = world;
        defineHead();
    }

    private void defineHead() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / PPM, 32 / PPM);
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
    }
}
