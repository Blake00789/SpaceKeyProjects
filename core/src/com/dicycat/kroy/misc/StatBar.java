package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

public class StatBar extends GameObject{
	
	public StatBar(Vector2 spawnPos, String texture) {
		super(spawnPos, new Texture(texture), new Vector2(1,1));
	}
	
	public void setBarDisplay(float x){
		sprite.setScale(x,3);
	}

	public void Update() {
		// TODO Auto-generated method stub
		
	}
	
	public void Render(SpriteBatch batch) {
		super.Render(batch);
	}

}
