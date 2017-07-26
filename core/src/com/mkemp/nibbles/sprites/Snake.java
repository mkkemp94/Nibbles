package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by mkemp on 7/25/17.
 */

public abstract class Snake extends Sprite {

    /** Constructor takes in a texture to be passed to super */
    public Snake(Texture texture, float x, float y) {
        super(texture);
        setPosition(x, y);
    }
}
