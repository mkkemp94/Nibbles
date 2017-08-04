package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
 * Created by mkemp on 7/25/17.
 */

public class Fruit extends Sprite {

    private PlayScreen screen;
    private World world;
    private Body body;
    private boolean setToMove;
    private boolean setToDestroy;
    private boolean destroyed;

    public Fruit(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;

        // Create this body piece
        createFruit(x, y);

        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setPosition(body.getPosition().x - 8 / PPM, body.getPosition().y - 8 / PPM);

        Texture texture = screen.getAssetManager().get("yoshi.png", Texture.class);
        setRegion(texture);
    }

    boolean startingPosition = true;
    /**
     * This gets called every time the screen's render() method updates.
     */
    public void update() {
        if (setToMove) {
            if (startingPosition) {
                body.setTransform(88 / PPM, 88 / PPM, 0);
                setPosition(80 / PPM, 80 / PPM);
            }
            else {
                body.setTransform(168 / PPM, 168 / PPM, 0);
                setPosition(160 / PPM, 160 / PPM);
            }
        }
    }

    /**
     * Move the fruit to a new position.
     * Invoke the screen's addToTail() method.
     */
    public void eatFruit() {
        setToMove = true;
        startingPosition = !startingPosition;

        screen.addToTail();
    }

    /**
     * Create this fruit.
     */
    private void createFruit(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);
        fixtureDef.filter.categoryBits = FRUIT_BIT;
        fixtureDef.filter.maskBits = SNAKE_BIT;

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(this);
    }
}
