package com.dicycat.kroy.entities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Collider {

    //TODO: Maths to construct polygons from rectangle and it's rotation angle.
    public static Polygon getRectangleHitbox(Rectangle x, float angle){
        return new Polygon();   //Stupid return statement to stop errors while we implement.
    }

    public static boolean RectangleOnRectangle(RotatingRectangle r1, RotatingRectangle r2){
        return false;
    }

    public static boolean RectangleOnCircle(RotatingRectangle x, Circle y){
        return false;
    }
}
