package com.example.sprites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;



public class GameCore {

    /*----------Game Parameters----------*/
    public static final float scaleF = 70.0f;
    public static final float W=28.0f;
    public static final float S=3.0f;
    public static final float Hb=9.0f;
    public static final float D=2.0f;
    public static final float M=1.2f;
    public static final float Hs=1.6f;
    public static final float R=1.0f;
    public static final float Cradius = R*0.35f;
    public static final float Pwr = 25.0f;
    public static final float ShootGauge = 4.0f;
    /*----------Game Parameters----------*/
    public static final ArrayList<Vec2> SAMPLE_TOWER_CONF = new ArrayList<>((Arrays.asList(new Vec2(0.0f,R/2.0f), new Vec2(-R,R/2.0f), new Vec2(R,R/2.0f),
            new Vec2(R/2.0f,R/2.0f+R), new Vec2(-R/2.0f,R/2.0f+R), new Vec2(0.0f,R/2.0f+2.0f*R) )));
    public enum GameState
    {
        WAIT_FOR_OPP, WAIT_FOR_INPUT, WAIT_FOR_ANIM, GAME_OVER
    }

    public ArrayList<Vec2> makeBigConf(){
        ArrayList<Vec2> conf = new ArrayList<>();
        for(int i=0;i<7;i++){
            conf.add(new Vec2(-3.0f*R+(float)i*R,R/2.0f));
        }
        for(int i=0;i<6;i++){
            conf.add(new Vec2(-2.6f*R+(float)i*(R*1.1f),R*1.5f));
        }
        for(int i=0;i<4;i++){
            conf.add(new Vec2(-2.0f*R+(float)i*(R*1.2f),R*2.5f));
        }
        for(int i=0;i<4;i++){
            conf.add(new Vec2(-2.0f*R+(float)i*(R*1.2f),R*3.5f));
        }
        return conf;
    }


    public GameState state = GameState.WAIT_FOR_INPUT;
    public static float cameraX=-M;
    public static Bitmap block_bitmap;
    public static final int UNKNOWN_TAG = 100;
    public static final int CANON_TAG = 101;
    public static final int BLOCK_TAG = 102;
    public static final int GROUND_TAG = 103;
    public static float mheight,mwidth;

    HashMap<Body,GBody> gbodies;

    World world;
    HashSet<Body> blocks;
    Body myCanon;
    Body enemyCanon;

    public GameCore(Bitmap block_bitmap){
        GameCore.block_bitmap=block_bitmap;
    }

    // waitTime is in milliseconds
    public void update(long waitTime){
        switch (state){
            case WAIT_FOR_INPUT:{

                break;
            }
            case GAME_OVER:{

                break;
            }
            case WAIT_FOR_OPP:{

                break;
            }
            case WAIT_FOR_ANIM:{
                world.step(0.02f*(((float)waitTime/15.0f)), 8, 3);
                break;
            }
        }

    }

    public void draw(Canvas canvas){
        for(GBody gbody : gbodies.values()){
            gbody.draw(canvas,cameraX);
        }
    }

    public int getTag(Body body) {
        if (body == myCanon) {
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
            gbodies.put(body,new GBody(body,GROUND_TAG,this));

            EdgeShape edge = new EdgeShape();

            edge.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            body.createFixture(edge, 0.0f);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.2f, 1.0f, new Vec2(0.5f, 1.0f), 0.0f);
            body.createFixture(shape, 0.0f);
        }

        // make blocks
        for(Vec2 pos : makeBigConf()){
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(W/2.0f + pos.x, pos.y);


            PolygonShape box = new PolygonShape();
            box.setAsBox(R/2.0f, R/2.0f);

            Body body = world.createBody(bd);
            gbodies.put(body,new GBody(body,BLOCK_TAG,this));
            body.createFixture(box, 1.0f);
        }
//        for (int i = 0; i < 5; i++) {
//            BodyDef bd = new BodyDef();
//            bd.type = BodyType.DYNAMIC;
//            bd.position.set(8.0f + 1.0f * ((float) i), 0.5f);
//
//
//            PolygonShape box = new PolygonShape();
//            box.setAsBox(0.5f, 0.5f);
//
//            Body body = world.createBody(bd);
//            gbodies.put(body,new GBody(body,BLOCK_TAG));
//            body.createFixture(box, 1.0f);
//        }

        spawnMyCanon();
//        BodyDef bd = new BodyDef();
//        bd.type = BodyType.DYNAMIC;
//        bd.position.set(1.0f, 0.5f);
//        PolygonShape box = new PolygonShape();
//        box.setAsBox(0.5f, 0.5f);
//
//        bd.setLinearVelocity(new Vec2(15.0f, 6.0f));
//        bd.setAngularVelocity(5.0f);
//        Body body = world.createBody(bd);
//        gbodies.put(body,new GBody(body,BLOCK_TAG));
//        body.createFixture(box, 3.0f);

    }


    public void spawnMyCanon(){
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(M, Hs);
        CircleShape circle = new CircleShape();
        circle.setRadius(Cradius);


        Body body = world.createBody(bd);
        gbodies.put(body,new GBody(body,CANON_TAG,this));
        body.createFixture(circle, 10.0f);
        myCanon = body;
    }

    public static Vec2 scrn_to_game(Vec2 scrn){
            float x = cameraX+scrn.x/scaleF;
            float y = -S+(mheight-scrn.y)/scaleF;
            return new Vec2(x,y);
    }

    public static Vec2 game_to_scrn(Vec2 game){
        float x =  (game.x-cameraX)*scaleF;
        float y = mheight-((game.y+S)*scaleF);
        return new Vec2(x,y);
    }

    public void shootMyCanon(Vec2 tail,Vec2 head){
        Vec2 gtail = scrn_to_game(tail);
        Vec2 ghead = scrn_to_game(head);
        Vec2 dir = gtail.sub(ghead);

        System.out.println(dir.length());
        if(dir.length()>ShootGauge) dir= dir.mul(ShootGauge/dir.length());

        dir = dir.mul(Pwr/ShootGauge);

        myCanon.setLinearVelocity(dir);
        state = GameState.WAIT_FOR_ANIM;
    }

}
