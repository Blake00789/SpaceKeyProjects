package com.dicycat.kroy.entities;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;

/**
 * This class is the pipe class which acts like a pipe in the game flappy bird,
 * hence the name.
 * 
 * @author Sam Hutchings
 *
 */
/**
 * @author Sam Hutchings
 *
 */
public class Pipe extends GameObject {
	private float speed = 4f; // The speed the pipe moves across the screen
	private Rectangle[] hitboxes = new Rectangle[2]; // Hitboxes for the pipe
	private boolean gameEnd = false; // Returns true if the pipe collides with the goose
	private Instant startTime; // When the pipe is created

	private int lifeTime = 6; // How long the pipe is on screen for

	/**
	 * Generates a pipe with the texture "pipe.png" and at the coordinates specified
	 * 
	 * @param spawnPos The spawn position
	 */
	public Pipe(Vector2 spawnPos) {
		super(spawnPos, new Texture("pipe.png"), new Vector2(128, 2048));
		hitboxes[0] = new Rectangle(0, 0, 128, 880);
		hitboxes[1] = new Rectangle(1158, 0, 128, 900);
		startTime = Instant.now();
		setPosition(spawnPos);
	}

	/**
	 * Moves the pipe and it's hitboxes along the screen
	 */
	@Override
	public void update() {
		setPosition(new Vector2(getX() - speed, getY()));
		hitboxes[0].x = getX();
		hitboxes[0].y = getY();
		hitboxes[1].x = getX();
		hitboxes[1].y = getY() + 1158;
		// if (Kroy.debug) {
		// debugDraw();
		// }

	}

	/**
	 * @return The hitboxes of the pipe
	 */
	public Rectangle[] getHitboxes() {
		return this.hitboxes;
	}

	/**
	 * Draws debug rectangles (But slows down the game considerably)
	 */
	public void debugDraw() {
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[0].x, hitboxes[0].y),
				new Vector2(hitboxes[0].width, hitboxes[0].height), 2, Color.RED);
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[1].x, hitboxes[1].y),
				new Vector2(hitboxes[1].width, hitboxes[1].height), 2, Color.RED);
	}

	/**
	 * s
	 * 
	 * @param player The goose in the minigame
	 * @return Whether the goose collides with the pipe
	 */
	public boolean gameEnd(Goose player) {
		for (Rectangle hitbox : hitboxes) {
			if (Intersector.overlaps(hitbox, player.getHitbox())) {
				gameEnd = true;
			}
		}
		return gameEnd;
	}

	/**
	 * Change the lifetime of the pipe for testing purposes
	 * 
	 * @param x The lifetime of the pipe
	 */
	public void setLifeTime(int x) {
		lifeTime = x;
	}

	/**
	 * @return Returns true if the pipe can be removed
	 */
	public boolean isRemove() {
		return ((Duration.between(startTime, Instant.now()).getSeconds()) > lifeTime);
	}

}
