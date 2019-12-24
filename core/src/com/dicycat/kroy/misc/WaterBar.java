package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

public class WaterBar extends GameObject{
	
	public WaterBar(Vector2 spawnPos) {
		super(spawnPos, new Texture("Blue.png"), new Vector2(1,1));
	}
	
	public void setTankDisplay(float x){
		sprite.setScale(x,2);
	}

	public void Update() {
		// TODO Auto-generated method stub
		
	}
	
	public void Render(SpriteBatch batch) {
		super.Render(batch);
		
	}

}
