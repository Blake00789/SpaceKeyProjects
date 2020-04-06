package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;

/**
 * Projectile fired by hostile entities
 * 
 * @author Riju De
 *
 */
public class Bullet extends GameObject {
	private int speed;			//Speed of the bullet
	private Vector2 velocity;	//Direction and amount to travel
	private float maxDist;		//Max distance to travel
	private float travelDist; 	//Distance left to travel
	private Circle hitbox;		//Bullet hit box
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ---
	//Create new attribute for fortressDamage
	private float damage;
	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS]---

	/**
	 * @param spawnPos Position to spawn the bullet
	 * @param direction direction the bullet will travel in
	 * @param speed speed the bullet should travel at
	 * @param range distance the bullet should travel before it is removed
	 */

	// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ---
	//Added fortressDamage as a parameter and set it equal to the new attribute
	public Bullet(Vector2 spawnPos, Vector2 direction, int speed, float range, Texture bulletTexture, float damage) {
		// REFACTOR_6 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Directly passed the bullet texture into the class as accessing the public static class GameTextures
		// would cause issues during testing.
		super(spawnPos, bulletTexture, new Vector2(20,20));
		// REFACTOR_6 - END OF MODIFICATION  - NP STUDIOS
		this.speed = speed;
		changeDirection(direction);
		maxDist = range;
		hitbox = new Circle(spawnPos.x, spawnPos.y, 10);
		this.damage = damage;
		// [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS]---
	}

	/**
	 * Reactivate the bullet and reset position
	 * @param initial position to set at
	 */
	public void fire(Vector2 initial) {
		travelDist = maxDist;
		setPosition(initial);
		changePosition(new Vector2(-getOriginX(), -getOriginY()));
		remove = false;
	}

	/**
	 * Calculate velocity of the bullet (Translation per frame)
	 * @param newDir New direction of the bullet
	 */
	public void changeDirection(Vector2 newDir) {
		velocity = newDir.scl(speed);
	}

	/**
	 *
	 */
	@Override
	public void update() {
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());	//Calculate distance to move
		travelDist -= posChange.len();
		if (travelDist <= 0) {	//Remove if travelled required distance
			remove = true;
			// REFACTOR_7 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
			// Added texture disposal to prevent memory leaks as a new texture is made for each bullet now due to
			// refactoring changes.
			// REFACTOR_7 - END OF MODIFICATION  - NP STUDIOS
		}
		Vector2 currentPos = new Vector2(getX(),getY());
		setPosition(currentPos.add(posChange));

		//Moves hit box according to the sprite.
		hitbox.x = getCentre().x;
		hitbox.y = getCentre().y;

		//Check to see if bullet collides with the players truck.
		FireTruck truck = Kroy.mainGameScreen.getPlayer();
		//POWERUPS - START OF MODIFICATION - NPSTUDIOS - BETHANY GILMORE
		if (truck.isAlive() && !truck.getDefenceUp()) { //the truck doesn't take damage if it has a shield powerup.
		//POWERUPS - END OF MODIFICATION - NPSTUDIOS
			if(Intersector.overlaps(hitbox, truck.getHitbox())){
                // [FORTRESS_IMPROVEMENT] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ---
			    if (Kroy.mainGameScreen.gameTimer == 150){ //If the game is half way through then double the amount of damage applied
                    // [UNIQUE_FORTRESS_HEALTH_DAMAGE] - START OF MODIFICATION  - [NPSTUDIOS] - [CASSIE_LILLYSTONE] ---
			        truck.applyDamage((2* damage)); //Replace hardcoded value with the parameter for fortressDamage
                } else {
			        truck.applyDamage(damage); //Replace hardcoded value with the parameter for fortressDamage
                } // [UNIQUE_FORTRESS_HEALTH_DAMAGE] - END OF MODIFICATION  - [NPSTUDIOS]---
                // [FORTRESS_IMPROVEMENT] - END OF MODIFICATION  - [NPSTUDIOS]---
				remove = true;
				// REFACTOR_8 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
				// Added texture disposal to prevent memory leaks as a new texture is made for each bullet now due to
				// refactoring changes.
				// REFACTOR_8 - END OF MODIFICATION  - NP STUDIOS
			}
		}

	}

	public Circle GetHitbox(){
		return this.hitbox;
	}
}
