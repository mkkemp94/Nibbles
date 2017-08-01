package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import java.util.ArrayList;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Player {

    public static final int RIGHT = 0;
    public static final int UP = 90;
    public static final int LEFT = 180;
    public static final int DOWN = 270;

    public World world;
    public PlayScreen screen;
    private float moveTimer;
    private int direction;

    public ArrayList<SnakePart> snakeBody;
    private int snakeLength;

    private boolean snakeIsDead;

    public Player(PlayScreen screen, Texture texture) {
        this.world = screen.getWorld();
        this.screen = screen;

        moveTimer = 0;
        snakeIsDead = false;

        float x = 72 / PPM;
        float y = 40 / PPM;
        direction = 0;

        snakeLength = 3;

        // Add each body piece to the list
        snakeBody = new ArrayList<SnakePart>();

        for (int i = 0; i < snakeLength; i++)
            snakeBody.add(new SnakePart(screen, texture, x - (16 * i), y));
    }

    /**
     * Move body and attach sprite.
     */
    public void update(float dt) {

        // Update move timer
        moveTimer += dt;

        // Move tail to new position. Become head.
        moveSnake();

        // Check for game over
        float headXPos = snakeBody.get(0).getPosition().x;
        float headYPos = snakeBody.get(0).getPosition().y;
        if (headXPos <= 0.1 || headXPos >= 2.3 || headYPos <= 0.1 || headYPos >= 2.3) {
            Gdx.app.log("Game", "Over");
            snakeIsDead = true;
        }

        // Set sprite to each body part.
        for (SnakePart part : snakeBody) {
            part.setSpritePosition(part.getPosition().x, part.getPosition().y);
        }

    }

    /**
     * Move tail to front of snake.
     */
    private void moveSnake() {

        // Get position of first body
        SnakePart head = snakeBody.get(0);

        // Current position of body
        float headXPos = head.getPosition().x;
        float headYPos = head.getPosition().y;

        // Step
        if (moveTimer >= 1) {
            int lastPiece = snakeBody.size() - 1;
            SnakePart newPiece = snakeBody.get(lastPiece);

            // Set position of body
            switch (direction) {
                case RIGHT:
                    newPiece.moveTo(headXPos + 16 / PPM, headYPos, 0);
                    break;
                case LEFT:
                    newPiece.moveTo(headXPos - 16 / PPM, headYPos, 0);
                    break;
                case UP:
                    newPiece.moveTo(headXPos, headYPos + 16 / PPM, 0);
                    break;
                case DOWN:
                    newPiece.moveTo(headXPos, headYPos - 16 / PPM, 0);
                    break;
                default:
                    newPiece.moveTo(headXPos, headYPos - 16 / PPM, 0);
                    break;
            }
            snakeBody.remove(lastPiece);
            snakeBody.add(0, newPiece);
            moveTimer = 0;
            screen.setAvailableForInput(true);
        }
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
     * Draw each body part's sprite.
     */
    public void draw(Batch batch) {
        for (SnakePart snakeBody : this.snakeBody)
            snakeBody.draw(batch);
    }
}
