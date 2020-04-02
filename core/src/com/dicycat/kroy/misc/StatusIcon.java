package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

public class StatusIcon extends GameObject{
    private int height;	//Height of the bar

    public StatusIcon(Vector2 spawnPos, String texture, int height) {
        super(spawnPos, new Texture(texture), new Vector2(1,1));
        this.height = height;
    }

    public void setBarDisplay(float x){
        sprite.setScale(x,height);
    }

    @Override
    public void update() {}

}
