package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.*;

public class Collider {

    /*Pretty sure this class is reduntant, need to iterate through bullets
    using if(player.getHitbox().Intersector.overlaps()) in game logic but
    I don't know where to put it */

    public static boolean RectangleOnRectangle(Rectangle r1, Rectangle r2){
        if(Intersector.overlaps(r1,r2)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean RectangleOnCircle(Rectangle r, Circle c){
        if(Intersector.overlaps(c,r)){
            return true;
        }
        else{
            return false;
        }
    }
}
