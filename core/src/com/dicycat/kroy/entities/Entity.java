package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public abstract class Entity extends GameObject{

	protected int healthPoints;
	protected int radius;
	
	public Entity(GameScreen gScreen, Vector2 spawnPos, Texture img, Vector2 imSize) {
		super(gScreen, spawnPos, img, imSize);
		healthPoints = 10;
		radius = 50;
	}

}

