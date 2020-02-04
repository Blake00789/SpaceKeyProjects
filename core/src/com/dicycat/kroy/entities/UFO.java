package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;

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
	 * @param spawnPos
	 */
	Vector2 spawnPos;
	int xDirection = 1;
	int yDirection = 1;
	private float speed = 500f;
	protected int direction = 0;
	private float movetimer = 0;
	public UFO(Vector2 spawnPos) {
		super(spawnPos, Kroy.mainGameScreen.textures.getUFO(), new Vector2(80, 80), 100);
		this.spawnPos = spawnPos;
		dispenser = new BulletDispenser(this);
		dispenser.addPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		movetimer = Gdx.graphics.getDeltaTime();

	}

	/**
	 *
	 */
	@Override
	public void update() {
		//movement
		moveInDirection();
		if (Gdx.graphics.getDeltaTime() >= movetimer + 2) {
			movetimer = Gdx.graphics.getDeltaTime();
			direction += 90;
			if (direction < 270) {
				direction = 0;
			}
		}
		//weapons
		Bullet[] toShoot = dispenser.update(true);
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.fire(getCentre());
				Kroy.mainGameScreen.addGameObject(bullet);
			}
		}
	}

	public void moveInDirection() {

		Vector2 movement = new Vector2(1,0); // movement represents where the truck is moving to. Initially set to (1,0) as this represents a unit vector

		movement.setAngle(direction+90); // rotates the vector to whatever angle it needs to face. 90 is added in order to get the keys matching up to movement in the right direction

		float posChange = speed * Gdx.graphics.getDeltaTime();	//Sets how far the truck can move this frame in the x and y direction
		Matrix3 distance = new Matrix3().setToScaling(posChange,posChange); // Matrix to scale the final normalised vector to the correct distance

		movement.nor(); // Normalises the vector to be a unit vector
		movement.mul(distance); // Multiplies the directional vector by the correct amount to make sure the truck moves the right amount

		Vector2 newPos = new Vector2(getPosition());
		if (!isOnCollidableTile(newPos.add(movement.x,0))) { // Checks whether changing updating x direction puts truck on a collidable tile
			setPosition(newPos); // updates x direction
		}
		newPos = new Vector2(getPosition());
		if (!isOnCollidableTile(newPos.add(0,movement.y))) { // Checks whether changing updating y direction puts truck on a collidable tile
			setPosition(newPos); // updates y direction
		}

		setRotation(direction);// updates truck direction
	}

	public boolean isOnCollidableTile(Vector2 pos) {
		if(GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x + this.getWidth(), pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y+this.getHeight()).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x+this.getWidth(), pos.y+this.getHeight()).isCollidable()) {
			return true;
		}
		return false;
	}
}
