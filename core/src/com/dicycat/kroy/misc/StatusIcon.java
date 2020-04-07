package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

/**
 * ADD DESCRIPTION HERE - NEW CLASS
 *
 * @author Alasdair Pilmore-Bedford - NP STUDIOS
 *
 */
public class StatusIcon extends GameObject{
    private boolean enabled;

    public StatusIcon(Vector2 spawnPos, String texture) {
        super(spawnPos, new Texture(texture), new Vector2(25,25));
        enabled = false;
    }

    // returns isEnabled
    public boolean isEnabled(){
        return enabled;
    }

    // Moves the icon to an off map position if it is not enabled
    public void removeIcon(){
        enabled = false;
        Vector2 aVeryFarAwayLocation = new Vector2(10000000,100000000);
        setPosition(aVeryFarAwayLocation);
    }

    // sets enabled to true
    public void addIcon(){
        enabled = true;
    }

    @Override
    public void update() {}

}
