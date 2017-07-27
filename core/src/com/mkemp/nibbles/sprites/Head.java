//package com.mkemp.nibbles.sprites;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.BodyDef;
//import com.badlogic.gdx.physics.box2d.CircleShape;
//import com.badlogic.gdx.physics.box2d.FixtureDef;
//import com.badlogic.gdx.physics.box2d.World;
//import com.mkemp.nibbles.screens.PlayScreen;
//
//import static com.mkemp.nibbles.Nibbles.PPM;
//
///**
// * Created by mkemp on 7/25/17.
// */
//
//public class Head extends Snake {
//
//    public World world;
//    public Body body;
//
//    public Texture texture;
//
//    private float x;
//    private float y;
//
//    private boolean collided;
//
//
//    public Head(World world, PlayScreen screen, Texture texture, float x, float y) {
//        super(world, screen, texture);
//
//        this.world = world;
//        this.texture = texture;
//        this.x = x;
//        this.y = y;
//        defineHead();
//
//        setPosition(x, y);
//        setRegion(this.texture);
//    }
//
//    /**
//     * Attach sprite.
//     */
//    public void update() {
//        setRegion(texture);
//    }
//
//    /**
//     * Create head's body.
//     */
//    private void defineHead() {
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.position.set(x / PPM, y / PPM);
//        bodyDef.type = BodyDef.BodyType.DynamicBody;
//        body = world.createBody(bodyDef);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        CircleShape circleShape = new CircleShape();
//        circleShape.setRadius(6 / PPM);
//
//        fixtureDef.shape = circleShape;
//        body.createFixture(fixtureDef);
//    }
//
//    @Override
//    public void draw(Batch batch) {
//        super.draw(batch);
//    }
//
//}
