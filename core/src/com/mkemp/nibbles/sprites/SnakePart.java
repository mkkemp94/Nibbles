package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.Gdx;
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

import static com.mkemp.nibbles.Nibbles.FRUIT_BIT;
import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.SNAKE_BIT;

/**
 * Created by mkemp on 7/31/17.
 */

public class SnakePart {

    private PlayScreen screen;
    public World world;
    private Sprite sprite;
    private Body body;

    public SnakePart(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();

        // Create this body piece
        createBody(x, y);

        Texture texture = screen.getAssetManager().get("yoshi.png", Texture.class);
        sprite = new Sprite(texture);
        sprite.setBounds(0, 0, 16 / PPM, 16 / PPM);
        sprite.setRegion(texture);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
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

    public void setSpriteRotation(float direction) {
        sprite.setRotation(direction);
    }

    /**
     * Create the snake piece's body.
     */
    private void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);
        fixtureDef.filter.categoryBits = SNAKE_BIT;
        fixtureDef.filter.maskBits = FRUIT_BIT;

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public void addToTail() {
        Gdx.app.log("SnakePart", "Calling screen addToTail()");
        screen.addToTail();
    }
}
