package com.example.sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import org.jbox2d.dynamics.Body;

/**
 * Created by ImanH on 3/24/2019.
 * Seyed Iman Hosseini Zavaraki
 * Github @ https://github.com/ImanHosseini
 * Wordpress @ https://imanhosseini.wordpress.com/
 */
public class GBody {
    Body body;
    int TAG;
    GBody(Body body,int TAG){
        this.body=body;
        this.TAG = TAG;
    }
    public void draw(Canvas canvas){
        switch (TAG){
            case GameCore.BLOCK_TAG:{
                Matrix matrix = new Matrix();
                matrix.postRotate(body.getAngle());
                int mwidth = canvas.getWidth();
                int mheight = canvas.getHeight();
                int xpos = (int)((body.getPosition().x/20.0f)*(float)mwidth);
                int ypos = (int)((body.getPosition().y/10.0f)*(float)mheight);
                int r = (int) ((0.5f/20.0f)*(float)(mwidth));
                System.out.println(xpos);
                Bitmap rotatedBitmap = Bitmap.createBitmap(GameCore.block_bitmap, 0,0, 2*r, 2*r, matrix, true);
                canvas.drawBitmap(rotatedBitmap,xpos, ypos, null);
                break;
            }
            default:{
                break;
            }
        }
    }
}
