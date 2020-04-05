package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;

/**
 * Mobile hostile entity.
 * Fires at the player when within range.
 * 
 * @author Riju De
 *
 */
public class UFO extends Entity {

	BulletDispenser dispenser;

	/**
	 * @param spawnPos Where we spawn
	 * @param speed	How fast our UFO moves
	 * @param direction Where our UFO moves towards
	 * @param movetimer How long since we last changed direction
	 * @param bulletDir Where our bullet is shooting
	 */
	Vector2 spawnPos;
	private float speed = 300f;
	protected int direction = 0;
	private float movetimer = 0;
	private Boolean frozen;

	/**
	 *
	 * @param spawnPos Where we spawn
	 */
	public UFO(Vector2 spawnPos) {
		super(spawnPos, Kroy.mainGameScreen.textures.getUFO(), new Vector2(80, 80), 100);
		this.spawnPos = spawnPos;
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		this.frozen = false;

	}

	/**
	 *
	 */
	@Override
	public void update() {
		//movement
		//POWERUPS - START OF MODIFICATION - NPSTUDIOS - BETHANY GILMORE
		if (!frozen) { //the patrols do not move when they have been frozen by the freeze patrols powerup.
			moveInDirection();
		}
		//POWERUPS - END OF MODIFICATION - NPSTUDIOS
		movetimer += Gdx.graphics.getDeltaTime();
		if (movetimer >= 2) {
			movetimer = 0;
			direction += 90;
			if (direction > 270) {
				direction = 0;
			}
		}
		//weapons
		Bullet[] toShoot = dispenser.update(true);
		if (toShoot != null) {
			//we don't want to shoot if the player isn't nearby, even though we are still moving around in a square.
			if (playerInRadius() && !frozen) {

				for (Bullet bullet : toShoot) {
					bullet.changeDirection(Kroy.mainGameScreen.getPlayer().getCentre().sub(this.getCentre()).nor());
					bullet.fire(getCentre());
					Kroy.mainGameScreen.addGameObject(bullet);
				}
			}
		}
	}

	/**
	 * TODO move this to entity
	 */
	public void moveInDirection() {

		Vector2 movement = new Vector2(1,0); // movement represents where the truck is moving to. Initially set to (1,0) as this represents a unit vector

		movement.setAngle(direction+90); // rotates the vector to whatever angle it needs to face. 90 is added in order to get the keys matching up to movement in the right direction

		float posChange = speed * Gdx.graphics.getDeltaTime();	//Sets how far the truck can move this frame in the x and y direction
		Matrix3 distance = new Matrix3().setToScaling(posChange,posChange); // Matrix to scale the final normalised vector to the correct distance

		movement.nor(); // Normalises the vector to be a unit vector
		movement.mul(distance); // Multiplies the directional vector by the correct amount to make sure the truck moves the right amount

		Vector2 newPos = new Vector2(getPosition());
		newPos.add(movement.x,0); // Checks whether changing updating x direction puts truck on a collidable tile
		newPos.add(0,movement.y); // Checks whether changing updating y direction puts truck on a collidable tile
		setPosition(newPos); // updates y direction
		setRotation(direction);// updates truck direction
	}
	//POWERUPS - START OF MODIFICATION - NPSTUDIOS - BETHANY GILMORE
	/**
	 * A method to freeze and unfreeze a patrol.
	 * @param flag
	 */
	public void setFrozen(Boolean flag){
		this.frozen = flag;
	}
	//POWERUPS - END OF MODIFICATION - NPSTUDIOS
}
