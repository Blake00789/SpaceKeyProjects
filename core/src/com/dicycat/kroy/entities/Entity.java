package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.gamemap.TiledGameMap;

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
	public void Update() {}	//Called every frame

	public Boolean isAlive() {
		return (healthPoints > 0) && !remove;
	}

	public void ApplyDamage(float damage) {	//Apply x amount of damage to the entity
		healthPoints -= damage;
		if (healthPoints <= 0) {
			Die();
		}
	}

	protected Boolean playerInRadius() {
		Vector2 currentCoords = Kroy.mainGameScreen.getPlayer().getCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, getCentre().x, getCentre().y) < radius ) { // checks the distance between the two entities
			return true; // returns true if distance between entity and player is less than radius of item
		}else {
			return false; // returns false otherwise
		}
	}
	
	public Integer getHealthPoints(){
		return healthPoints;
	}

}
