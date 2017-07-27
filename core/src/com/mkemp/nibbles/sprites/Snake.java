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

public class Snake extends Sprite {

    public World world;
    public Body body;
    public PlayScreen screen;
    private float moveTimer;
    private int direction;

    public Snake(World world, PlayScreen screen, Texture texture) {
        super(texture);
        this.world = world;
        this.screen = screen;

        moveTimer = 0;

        defineHead();
        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setRegion(texture);
    }

    /**
     * Attach sprite.
     */
    public void update(float dt) {

        float currentx = body.getPosition().x;
        float currenty = body.getPosition().y;
        float currentAngle = body.getPosition().angle();

        moveTimer += dt;
        if (moveTimer >= 0.5) {
            switch (direction) {
                case 0:
                    body.setTransform(currentx + 16 / PPM, currenty, 0);
                    break;
                case 90:
                    body.setTransform(currentx, currenty + 16 / PPM, 0);
                    break;
                case 180:
                    body.setTransform(currentx - 16 / PPM, currenty, 0);
                    break;
                case 270:
                    body.setTransform(currentx, currenty - 16 / PPM, 0);
                    break;
            }
            moveTimer = 0;
        }

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void setDirection(int degrees) {
        direction = degrees;
    }

    /**
     * Create head's body.
     */
    private void defineHead() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(24 / PPM, 24 / PPM);
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
