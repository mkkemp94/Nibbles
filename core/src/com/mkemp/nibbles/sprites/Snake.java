package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import java.util.concurrent.LinkedBlockingQueue;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Snake extends Sprite {

    public static final int RIGHT = 0;
    public static final int UP = 90;
    public static final int LEFT = 180;
    public static final int DOWN = 270;

    private float currentXPosition;
    private float currentYPosition;
    private float lastXPosition;
    private float lastYPosition;

    public World world;
    public Body body;
    public PlayScreen screen;
    private float moveTimer;
    private int direction;

    private int index;
    private LinkedBlockingQueue<Integer> inputQueue;
    private int turnsToWait;

    private boolean snakeIsDead;

    public Snake(PlayScreen screen, int index, Texture texture, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.index = index;

        moveTimer = 0;
        snakeIsDead = false;

        currentXPosition = x;
        currentYPosition = y;

        inputQueue = new LinkedBlockingQueue<Integer>();
        turnsToWait = index;
        direction = 0;

        //addCommandToQueue(90);
        //Gdx.app.log("Snake "+ index, "Polling " + inputQueue.poll());

        defineHead(currentXPosition, currentYPosition);
        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setRegion(texture);
    }

    /**
     * Move body and attach sprite.
     */
    public void updateSprite(float dt) {

        // Current position of body
        float currentx = body.getPosition().x;
        float currenty = body.getPosition().y;

        // Update move timer
        moveTimer += dt;

        // Step
        if (moveTimer >= 1) {
            if (index == 1) Gdx.app.log("Snake " + index, "Queue = " + inputQueue.toString());

            if (turnsToWait > 0) {
                turnsToWait--;
                Gdx.app.log("Snake " + index, "Waiting " + turnsToWait);
            }

//            if (inputQueue.peek() == null) {
//                turnsToWait = index;
//                if (index == 1)
//                    Gdx.app.log("Snake " + index, "Turns to wait = " + turnsToWait);
//
//            } else if (turnsToWait > 0) {
//                turnsToWait--;
//                if (index == 1)
//                    Gdx.app.log("Snake " + index, "Turns to wait: " + turnsToWait);
//            }
            else {
                Gdx.app.log("Snake " + index, "Change direction to " + inputQueue.peek());
                if (!inputQueue.isEmpty()) direction = inputQueue.poll();
                turnsToWait = index;
            }

//            if (index == 1)
//                Gdx.app.log("Snake " + index, "Direction = " + direction);
            // Set position of body
            switch (direction) {
                case RIGHT:
                    body.setTransform(currentx + 16 / PPM, currenty, 0);
                    break;
                case UP:
                    body.setTransform(currentx, currenty + 16 / PPM, 0);
                    break;
                case LEFT:
                    body.setTransform(currentx - 16 / PPM, currenty, 0);
                    break;
                case DOWN:
                    body.setTransform(currentx, currenty - 16 / PPM, 0);
                    break;
                default:
                    body.setTransform(currentx, currenty - 16 / PPM, 0);
                    break;

            }
            moveTimer = 0;
            screen.setAvailableForInput(true);
        }

        float newx = body.getPosition().x;
        float newy = body.getPosition().y;
        if (newx <= 0.1 || newx >= 2.3 || newy <= 0.1 || newy >= 2.3) {
            Gdx.app.log("Game", "Over");
            snakeIsDead = true;
        }

        // Set position of sprite - if not dead
        if (!snakeIsDead)
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void addCommandToQueue(int input) {

        inputQueue.add(input);

    }

    public float getMoveTimer() {
        return moveTimer;
    }

    /**
     * Gets whether or not snake is dead.
     * @return true or false
     */
    public boolean snakeIsDead() {
        return snakeIsDead;
    }

    /**
     * Called by handleInput() in PlayScreen.
     * This sets the direction for the body to move in updateSprite().
     * @param degrees
     */
    public void setDirection(int degrees) {
        direction = degrees;
    }

    /**
     * Create head's body.
     */
    private void defineHead(float x, float y) {
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
    }
}
