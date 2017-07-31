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

import java.util.concurrent.LinkedBlockingDeque;

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

//    private int index;
//    private LinkedBlockingQueue<Integer> inputQueue;
//    private int turnsToWait;

    private LinkedBlockingDeque<Body> linkedBlockingDeque;
    private int snakeLength;

    private boolean snakeIsDead;

    public Snake(PlayScreen screen, int index, Texture texture, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
//        this.index = index;

        moveTimer = 0;
        snakeIsDead = false;

        currentXPosition = x;
        currentYPosition = y;

//        inputQueue = new LinkedBlockingQueue<Integer>();
//        turnsToWait = index;
        direction = 0;

        //addCommandToQueue(90);
        //Gdx.app.log("Snake "+ index, "Polling " + inputQueue.poll());

        snakeLength = 3;

        // Add each body piece to the dequeue
        linkedBlockingDeque = new LinkedBlockingDeque<Body>();

        // Create multiple bodies to match snake length
        for (int i = 0; i < snakeLength; i++) {
            Body body = createBody(currentXPosition - (i * 16), currentYPosition - (i * 16));
            linkedBlockingDeque.addLast(body);
        }

        setBounds(0, 0, 16 / PPM, 16 / PPM);
        setRegion(texture);
    }

    /**
     * Move body and attach sprite.
     */
    public void update(float dt) {

        // Get position of first body
        Body firstBody = linkedBlockingDeque.peekFirst();

        // Current position of body
        float currentx = firstBody.getPosition().x;
        float currenty = firstBody.getPosition().y;

        // Update move timer
        moveTimer += dt;

        // Step
        if (moveTimer >= 1) {
//            if (index == 1) Gdx.app.log("Snake " + index, "Queue = " + inputQueue.toString());
//
//            if (turnsToWait > 0) {
//                turnsToWait--;
//                Gdx.app.log("Snake " + index, "Waiting " + turnsToWait);
//            }

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
//            else {
//                Gdx.app.log("Snake " + index, "Change direction to " + inputQueue.peek());
//                if (!inputQueue.isEmpty()) direction = inputQueue.poll();
//                turnsToWait = index;
//            }

//            if (index == 1)
//                Gdx.app.log("Snake " + index, "Direction = " + direction);



            //Body newBody;
            Body lastBody = linkedBlockingDeque.pollLast();

            // Set position of body
            switch (direction) {
                case RIGHT:
                    lastBody.setTransform(currentx + 16 / PPM, currenty, 0);
                    //newBody = createBody(currentx + 16 / PPM, currenty);
                    break;
                case UP:
                    lastBody.setTransform(currentx, currenty + 16 / PPM, 0);
                    //newBody = createBody(currentx, currenty + 16 / PPM);
                    break;
                case LEFT:
                    lastBody.setTransform(currentx - 16 / PPM, currenty, 0);
                    //newBody = createBody(currentx - 16 / PPM, currenty);
                    break;
                case DOWN:
                    lastBody.setTransform(currentx, currenty - 16 / PPM, 0);
                    //newBody = createBody(currentx, currenty - 16 / PPM);
                    break;
                default:
                    lastBody.setTransform(currentx, currenty - 16 / PPM, 0);
                    //newBody = createBody(currentx, currenty);
                    break;
            }
            linkedBlockingDeque.addFirst(lastBody);
            moveTimer = 0;
            screen.setAvailableForInput(true);
        }

        Body newBody = linkedBlockingDeque.getFirst();
        float newx = newBody.getPosition().x;
        float newy = newBody.getPosition().y;
        if (newx <= 0.1 || newx >= 2.3 || newy <= 0.1 || newy >= 2.3) {
            Gdx.app.log("Game", "Over");
            snakeIsDead = true;
        }

        // Set position of sprite - if not dead
        if (!snakeIsDead) {
            setPosition(newBody.getPosition().x - getWidth() / 2, newBody.getPosition().y - getHeight() / 2);
        }
    }

//    public void addCommandToQueue(int input) {
//
//        inputQueue.add(input);
//
//    }

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
     * This sets the direction for the body to move in update().
     * @param degrees
     */
    public void setDirection(int degrees) {
        direction = degrees;
    }

    /**
     * Create head's body.
     */
    private Body createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);

        return body;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
