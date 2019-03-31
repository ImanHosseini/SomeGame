package com.example.sprites;

import java.util.HashMap;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class GameCore {
    public static Bitmap block_bitmap;
    public static final int UNKNOWN_TAG = 100;
    public static final int CANON_TAG = 101;
    public static final int BLOCK_TAG = 102;
    public static final int GROUND_TAG = 103;

    HashMap<Body,GBody> gbodies;

    World world;
    HashSet<Body> blocks;
    Body canon;

    public GameCore(Bitmap block_bitmap){
        GameCore.block_bitmap=block_bitmap;
    }

    public void update(){
        world.step(0.001f, 8, 3);
    }

    public void draw(Canvas canvas){
        for(GBody gbody : gbodies.values()){
            gbody.draw(canvas);
        }
    }

    public int getTag(Body body) {
        if (body == canon) {
            return CANON_TAG;
        }
        if (blocks.contains(body)) {
            return BLOCK_TAG;
        }

        return UNKNOWN_TAG;
    }

    public void initialization() {
        {
            gbodies = new HashMap<>();

            Vec2 gravity = new Vec2(0, -10f);
            world = new World(gravity);

            // make ground
            BodyDef bd = new BodyDef();
            bd.position.set(0.0f, 0.0f);
            Body body = world.createBody(bd);
            gbodies.put(body,new GBody(body,GROUND_TAG));

            EdgeShape edge = new EdgeShape();

            edge.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            body.createFixture(edge, 0.0f);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.2f, 1.0f, new Vec2(0.5f, 1.0f), 0.0f);
            body.createFixture(shape, 0.0f);
        }

        // make blocks
        for (int i = 0; i < 5; i++) {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(8.0f + 1.0f * ((float) i), 0.5f);


            PolygonShape box = new PolygonShape();
            box.setAsBox(0.5f, 0.5f);

            Body body = world.createBody(bd);
            gbodies.put(body,new GBody(body,BLOCK_TAG));
            body.createFixture(box, 1.0f);
        }

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(1.0f, 0.5f);
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);

        bd.setLinearVelocity(new Vec2(20.0f, 0.0f));
        Body body = world.createBody(bd);
        gbodies.put(body,new GBody(body,BLOCK_TAG));
        body.createFixture(box, 1.0f);

    }



}
