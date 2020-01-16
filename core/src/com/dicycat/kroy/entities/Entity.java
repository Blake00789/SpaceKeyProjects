package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.screens.GameScreen;

public abstract class Entity extends GameObject{

	protected int healthPoints;
	protected int radius;
	protected TiledGameMap map;
	protected int maxHealthPoints;

	public Entity(Vector2 spawnPos, Texture img, Vector2 imSize,int health) {
		super(spawnPos, img, imSize);
		healthPoints = health;
		maxHealthPoints = health;
		radius = 500;
		changePosition(spawnPos);
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub

	}

	public Boolean isAlive() {
		return (healthPoints > 0) && !remove;
	}

	public void ApplyDamage(float damage) {
		healthPoints -= damage;
		if (healthPoints <= 0) {
			Die();
		}
	}

	public Integer getHealthPoints(){
		return healthPoints;
	}

}
