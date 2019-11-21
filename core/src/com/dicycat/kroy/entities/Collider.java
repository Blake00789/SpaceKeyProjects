package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.Circle;

public class Collider {

    public static boolean CircleOnCircle(Circle c1, Circle c2){
        if(c1.overlaps(c2)){
            return True;
        }
        else{
            return False;
        }
    }

}
