package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by mkemp on 7/25/17.
 */

public class Head extends Snake {

    private boolean collided;



    public Head(Texture texture, float x, float y) {
        super(texture, x, y);


    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
