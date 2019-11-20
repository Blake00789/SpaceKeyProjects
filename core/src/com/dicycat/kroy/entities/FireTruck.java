package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public class FireTruck extends GameObject{
	private int speed = 200;
	
	public FireTruck(GameScreen gScreen, Vector2 spawnPos) {
		super(gScreen, spawnPos, new Texture("fireTruck.png"), new Vector2(50,100));
	}

	@Override
	public void Update() 
	{
		float posChange = speed * Gdx.graphics.getDeltaTime();
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			position.y += posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			position.y -= posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			position.x += posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			position.x -= posChange;
		}
		
	}
}
