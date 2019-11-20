package com.rd1127.testgame.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.rd1127.testgame.GameObject;

public class Entity extends GameObject{

	protected int healthPoints;
	protected int radius;
	
	public Entity(Vector2 spawnPos, Texture img, Vector2 imSize) {
		super(spawnPos, img, imSize);
		healthPoints = 10;
		radius = 50;
	}

}
