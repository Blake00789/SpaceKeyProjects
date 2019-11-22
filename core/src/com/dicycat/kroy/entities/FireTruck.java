package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.screens.GameScreen;

public class FireTruck extends Entity{
	private int speed = 200;	//How fast the truck can move
	
	public FireTruck(GameScreen gScreen, Vector2 spawnPos) {	//Constructor
		super(gScreen, spawnPos, new Texture("fireTruck.png"), new Vector2(50,100));
	}

	@Override
	public void Update() 
	{
		float posChange = speed * Gdx.graphics.getDeltaTime();	//Get how far the truck can move this frame
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {	//Check all inputs for player movement
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
		
		gameScreen.DrawRect(position, size, 2, Color.FIREBRICK);
	}
}
