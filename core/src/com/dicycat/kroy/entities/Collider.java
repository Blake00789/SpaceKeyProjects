package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.*;

public class Collider {

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
