package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public class Bullet extends GameObject {
	private Vector2 velocity;	//Direction and distance to travel
	private float maxDist;		//Max distance to travel
	private float travelDist; 	//Distance left to travel
	
	
	public Bullet(GameScreen gScreen, Vector2 spawnPos, Vector2 direction, int speed, float range) {	//Constructor
		super(gScreen, spawnPos, new Texture("singleP.png"), new Vector2(20,20));
		velocity = direction.scl(speed);
		maxDist = range;
	}
	
	public void Fire(Vector2 initial) {	//Reset bullet
		travelDist = maxDist;
		position = initial;
		remove = false;
	}
	
	@Override
	public void Update() { //Called every frame
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());	//Calculate distance to move
		travelDist -= posChange.len();
		if (travelDist <= 0) {		//Remove if travelled required distance
			remove = true;
		}
		position.add(posChange);	//Update position
	}

}

