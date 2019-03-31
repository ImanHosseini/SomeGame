import Lib.StdDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.HashMap;

/**
 * Created by ImanH on 3/24/2019.
 * Seyed Iman Hosseini Zavaraki
 * Github @ https://github.com/ImanHosseini
 * Wordpress @ https://imanhosseini.wordpress.com/
 */
public class BodyDrawer {
    public void initDraw(){
        StdDraw.setCanvasSize(800,400);
        StdDraw.setXscale(0.0,20.0);
        StdDraw.setYscale(0.0,10.0);
    }
    public void bodyDraw(World world,HashMap<Body,GBody> gbodies){
        StdDraw.clear();

       Body bod = world.getBodyList();
       while(bod!= null){

           if(gbodies.containsKey(bod)){
               System.out.println("BODY: "+gbodies.get(bod).TAG);
           }else {
               // ERROR!
               System.out.println(bod.getType());
               }
               //
           switch (gbodies.get(bod).TAG){
               case Main.BLOCK_TAG:{
                   Vec2 pos = bod.getPosition();
                   StdDraw.square(pos.x,pos.y,0.5);
                   break;
               }

               case Main.GROUND_TAG:{
                   break;
               }

               default:{

               }
           }


           bod = bod.getNext();

       }
        StdDraw.show(50);
    }
}
