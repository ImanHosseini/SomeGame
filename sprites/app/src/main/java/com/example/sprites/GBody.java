package com.example.sprites;

import android.graphics.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Created by ImanH on 3/24/2019.
 * Seyed Iman Hosseini Zavaraki
 * Github @ https://github.com/ImanHosseini
 * Wordpress @ https://imanhosseini.wordpress.com/
 */
public class GBody {
    GameCore gcore;
    Body body;
    int TAG;
    int mwidth,mheight;
    GBody(Body body,int TAG,GameCore gcore){
        this.body=body;
        this.TAG = TAG;
        this.gcore = gcore;
    }
    public void draw(Canvas canvas,float camX){
        switch (TAG){
            case GameCore.BLOCK_TAG:{
                Matrix matrix = new Matrix();
                matrix.postRotate(body.getAngle()*180/(float)Math.PI);
                 mwidth = canvas.getWidth();
                 mheight = canvas.getHeight();
                int xpos = (int)( (body.getPosition().x-camX)*GameCore.scaleF);
                int ypos = (int)(mheight-((body.getPosition().y+GameCore.S)*GameCore.scaleF));
                int r = (int) (GameCore.R*GameCore.scaleF);
                // System.out.println(xpos);
                Bitmap rotatedBitmap = Bitmap.createBitmap(GameCore.block_bitmap, 0,0, r, r, matrix, true);
                canvas.drawBitmap(rotatedBitmap,xpos, ypos, null);
                break;
            }
            case GameCore.CANON_TAG:{
                Matrix matrix = new Matrix();
                matrix.postRotate(body.getAngle()*180/(float)Math.PI);
                 mwidth = canvas.getWidth();
                 mheight = canvas.getHeight();
                int xpos = (int)( (body.getPosition().x-camX)*GameCore.scaleF);
                int ypos = (int)(mheight-((body.getPosition().y+GameCore.S)*GameCore.scaleF));
                // System.out.println("YPOS DRAWN AT "+ypos);
                int r = (int) (GameCore.Cradius*2.0f*GameCore.scaleF);
                // System.out.println(xpos);
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);
                canvas.drawCircle(xpos, ypos, r,paint);
                break;
            }
            default:{
                break;
            }
        }
        GameCore.mheight = (float)mheight;
        GameCore.mwidth = (float) mwidth;
    }

    public Vec2 pos_to_scrn(){
        float x =  (body.getPosition().x-GameCore.cameraX)*GameCore.scaleF;
        float y = mheight-((body.getPosition().y+GameCore.S)*GameCore.scaleF);
        return new Vec2(x,y);
    }

    public boolean inDelta(Vec2 scrn_coords,float delta){
        float val = scrn_coords.sub(pos_to_scrn()).length();
        System.out.println("DELTA IS "+val);
        // System.out.println("Y IS "+scrn_coords.y+" POS_TO_S "+pos_to_scrn().y);
        return val<delta;
    }

}
