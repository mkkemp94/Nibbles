package com.mkemp.nibbles.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;
import com.mkemp.nibbles.sprites.Wall;

import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.SNAKE_BIT;
import static com.mkemp.nibbles.Nibbles.WALL_BIT;

/**
 * Assigns objects to each tile.
 */

public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        Body body;

        Gdx.app.log("Creating", "Walls");

        // Walls
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            BodyDef bodyDef = new BodyDef();
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2,
                    rectangle.getY() + rectangle.getHeight() / 2);
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            body = world.createBody(bodyDef);
            body.setUserData(Wall.class);

            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((rectangle.getWidth() / 2) / PPM, (rectangle.getHeight() / 2) / PPM);
            fixtureDef.filter.categoryBits = WALL_BIT;
            fixtureDef.filter.maskBits = SNAKE_BIT;

            fixtureDef.shape = shape;
            body.createFixture(fixtureDef).setUserData(Wall.class);
        }
    }
}
