package com.example.sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import org.jbox2d.common.Vec2;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    boolean touch_down = false;
    Vec2 touch_vec;
    private GameThread gameThread;
    private GameCore gcore;

    private Block chibi1;
    float canvasTop = 0.0f;

    public GameSurface(Context context)  {
        super(context);
        gcore = new GameCore(BitmapFactory.decodeResource(this.getResources(),R.drawable.block));
        gcore.initialization();
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update(long waitTime)  {
        gcore.update(waitTime);
    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        gcore.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.block);
        this.chibi1 = new Block(this,chibiBitmap1,100,50);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (gcore.state== GameCore.GameState.WAIT_FOR_INPUT && event.getAction() == MotionEvent.ACTION_DOWN) {
            if(gcore.gbodies.get(gcore.myCanon).inDelta(new Vec2(event.getX(),event.getY()),110.0f)){
                touch_down = true;
                touch_vec = new Vec2(event.getX(),event.getY());
                return true;
            }
        }

        if (touch_down && event.getAction() == MotionEvent.ACTION_UP) {
           touch_down = false;
           System.out.println(touch_vec.x+"&&"+touch_vec.y+"**"+event.getX()+"&&"+event.getY());
           gcore.shootMyCanon(touch_vec,new Vec2(event.getX(),event.getY()));
        }

        return false;
    }

}