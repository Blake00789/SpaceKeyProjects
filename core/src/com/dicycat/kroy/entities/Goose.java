package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

/**
 * Goose class for minigame, acts like the bird in flappy bird
 * 
 * @author Sam Hutchings
 *
 */
public class Goose extends Entity {
	private float deceleration = 0.5f; // Gravity on the goose
	private float velocity = 0; // Speed of the goose falling or flapping
	private Rectangle hitbox = new Rectangle(0, 0, 46 * scale, 54 * scale); // Hitbox of the goose
	private static float scale = 1.5f; // Allows the goose to be scaled up in size

	/**
	 * Instantiate the default goose
	 */
	public Goose() {
		super(new Vector2(-32 * scale, -32 * scale), new Texture("goose2.png"), new Vector2(64 * scale, 64 * scale), 1);
	}

	/**
	 * Update the movement of the goose and it's hitbox
	 */
	@Override
	public void update() {
		if (getY() < -400) {
			setVelocity(0);
		} else if (velocity > -5f) {
			velocity -= deceleration;
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			setVelocity(10);
		}
		setPosition(new Vector2(getX(), getY() + velocity));
		hitbox.setCenter(getCentre().x, getCentre().y);
		if (Kroy.debug) {
			debugDraw();
		}
	}

	/**
	 * Draw debug rectangle of the hitbox 
	 */
	public void debugDraw() {
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2,
				Color.GREEN);
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * @return the hitbox
	 */
	public Rectangle getHitbox() {
		return this.hitbox;
	}

	/**
	 * @return the deceleration
	 */
	public float getDeceleration() {
		return deceleration;
	}

	/**
	 * @return the velocity
	 */
	public float getVelocity() {
		return velocity;
	}

}
