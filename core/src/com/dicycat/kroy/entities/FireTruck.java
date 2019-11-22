package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public class FireTruck extends GameObject{
	private int speed = 200;	//How fast the truck can move
	private Rectangle hitbox = new Rectangle(50, 100, 5, 5);
	
	public FireTruck(GameScreen gScreen, Vector2 spawnPos) {	//Constructor
		super(gScreen, spawnPos, new Texture("fireTruck.png"), new Vector2(50,100));
	}

	@Override
	public void Update() 
	{
		float posChange = speed * Gdx.graphics.getDeltaTime();	//Get how far the truck can move this frame

		if (Gdx.input.isKeyPressed(Keys.UP)) {	//Check all inputs for player movement
			position.y += posChange;
			hitbox.setY(hitbox.y += posChange);
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			position.y -= posChange;
			hitbox.setY(hitbox.y -= posChange);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			position.x += posChange;
			hitbox.setX(hitbox.x += posChange);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			position.x -= posChange;
			hitbox.setX(hitbox.x -= posChange);
		}
		
	}
}
