package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;

/**
 * Class for interactive gameObjects
 * 
 * @author Riju De
 *
 */
public abstract class Entity extends GameObject{
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ----
	// Changed the type of healthPoints and maxHealthPoints from int to float
	protected float healthPoints;
	protected int radius;
	protected float maxHealthPoints;
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS]----
	/**
	 * @param spawnPos The position the entity will spawn at.
	 * @param img The texture of the entity.
	 * @param imSize Size of the entity. Can be used to resize large/small textures
	 * @param health Hit points of the entity
	 */
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ----
	public Entity(Vector2 spawnPos, Texture img, Vector2 imSize, float health) { //Changed the type of health from int to float
		// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS]----
		super(spawnPos, img, imSize);
		healthPoints = health;
		maxHealthPoints = health;
		radius = 500;
		changePosition(spawnPos);
	}
 
	/**
	 * Method is called every frame (If added to the gameobjects list in GameScreen)
	 */
	@Override
	public void update() {}	//Called every frame

	/**
	 * Checks if the Entity still has health and is not marked for removal
	 * @return alive Is health above 0 and is not marked for removal
	 */
	public Boolean isAlive() {
		return (healthPoints > 0) && !remove;
	}

	/**
	 * Apply x amount of damage to the entity
	 * @param damage Amount of damage to inflict on the Entity
	 */
	public void applyDamage(float damage) {	
		healthPoints -= damage;
		if (healthPoints <= 0) { 
			die();
		}
	}

	/**
	 * Checks if the player is within the radius of the Entity
	 * @return playerInRadius
	 */
	public Boolean playerInRadius() {
		Vector2 currentCoords = Kroy.mainGameScreen.getPlayer().getCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, getCentre().x, getCentre().y) < radius ) { // checks the distance between the two entities
			return true; // returns true if distance between entity and player is less than radius of item
		}else {
			return false; // returns false otherwise
		}
	}
	
	/**
	 * new
	 * @return healthPoints
	 */
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ----
	// Changed return type for getters or healthPoints and maxHealthPoints from int to float
	public float getHealthPoints(){
		return healthPoints; 
	}
	
	/**
	 * new
	 * increase the HealthPoints by x
	 */
	public void setHealthPoints(int x){
		if(!(getHealthPoints() >= maxHealthPoints)){
			healthPoints+=x;
		}
	}

	// STATBAR_REFACTOR_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Added new getter required for the statbar refactor.
	public float getMaxHealthPoints() {
		return maxHealthPoints;
	}
	// STATBAR_REFACTOR_1 - END OF MODIFICATION  - NP STUDIOS

	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS] ----
}
