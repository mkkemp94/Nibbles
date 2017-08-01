package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import java.util.ArrayList;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Snake {

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

    //private LinkedBlockingDeque<Body> linkedBlockingDeque;

    public ArrayList<BodyPiece> snakeBody;
    private int snakeLength;

    private boolean snakeIsDead;
    private Sprite sprite;

    private ArrayList<Sprite> sprites;

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

        //snakeLength = 3;

        // Add each body piece to the dequeue
        //linkedBlockingDeque = new LinkedBlockingDeque<Body>();
        snakeBody = new ArrayList<BodyPiece>();

        snakeBody.add(new BodyPiece(screen, texture, x, y));
        snakeBody.add(new BodyPiece(screen, texture, x - 16, y));
        snakeBody.add(new BodyPiece(screen, texture, x - 32, y));


        // Create multiple bodies to match snake length
//        for (int i = 0; i < snakeLength; i++) {
//            Body body = createBody(currentXPosition - (i * 16), currentYPosition - (i * 16));
////            Sprite sprite = new Sprite(texture);
////            sprite.setBounds(0, 0, 16 / PPM, 16 / PPM);
////            sprite.setRegion(texture);
//
//            //linkedBlockingDeque.addLast(body);
//            snakeBody.add(body);
//            //sprites.add(sprite);
//        }

//        sprite = new Sprite(texture);
//        sprite.setBounds(0, 0, 16 / PPM, 16 / PPM);
//        sprite.setRegion(texture);
    }

//    public ArrayList<Body> getSnakeBody() {
//        return snakeBody;
//    }

    /**
     * Move body and attach sprite.
     */
    public void update(float dt) {

        // Get position of first body
//        Body firstBody = linkedBlockingDeque.peekFirst();
        BodyPiece firstBody = snakeBody.get(0);

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
            //Body lastBody = linkedBlockingDeque.pollLast();
            BodyPiece lastBody = snakeBody.get(snakeBody.size() - 1);

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
            //linkedBlockingDeque.addFirst(lastBody);
            snakeBody.remove(snakeBody.size() - 1);
            snakeBody.add(0, lastBody);
            moveTimer = 0;
            screen.setAvailableForInput(true);
        }

        //Body newBody = linkedBlockingDeque.getFirst();
        BodyPiece newBody = snakeBody.get(0);
        float newx = newBody.getPosition().x;
        float newy = newBody.getPosition().y;
        if (newx <= 0.1 || newx >= 2.3 || newy <= 0.1 || newy >= 2.3) {
            Gdx.app.log("Game", "Over");
            snakeIsDead = true;
        }

        // Set position of sprite - if not dead
        if (!snakeIsDead) {
            //sprite.setPosition(newBody.getPosition().x - sprite.getWidth() / 2, newBody.getPosition().y - sprite.getHeight() / 2);
            for (BodyPiece body : snakeBody) {
                body.setSpritePosition(body.getPosition().x, body.getPosition().y);
            }
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

    public void draw(Batch batch) {
        //super.draw(batch);
//        for (int i = 0; i < snakeBody.size(); i++) {
//            float bodyX = snakeBody.get(i).getPosition().x;
//            float bodyy = snakeBody.get(i).getPosition().y;
//
//            sprite.draw(batch);
//        }
        for (BodyPiece bodyPiece : snakeBody)
            bodyPiece.draw(batch);
    }
}
