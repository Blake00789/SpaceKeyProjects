package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Collider {

    //TODO: Not going to be void, need to work out representation.
    public static void getRectangleHitbox(Rectangle x, float angle){

    }

    public static boolean CircleOnCircle(Circle c1, Circle c2){
        if(c1.overlaps(c2)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean RectangleOnRectangle(){

    }
}
