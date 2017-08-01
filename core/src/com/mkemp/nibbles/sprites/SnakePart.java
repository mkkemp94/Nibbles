package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/31/17.
 */

public class SnakePart {

    public World world;
    private Sprite sprite;
    private Body body;

    public SnakePart(PlayScreen screen, Texture texture, float x, float y) {
        this.world = screen.getWorld();

        // Create this body piece
        createBody(x, y);

        sprite = new Sprite(texture);
        sprite.setBounds(0, 0, 16 / PPM, 16 / PPM);
        sprite.setRegion(texture);

    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void moveTo(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setSpritePosition(float x, float y) {
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    /**
     * Create the snake piece's body.
     */
    private void createBody(float x, float y) {
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

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
