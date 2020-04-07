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
	private int xOffset = 1050;
	private int yOffset = 0;
	private int yOffset2 = 720;

	/**
	 * Generates a pipe with the texture "pipe.png" and at the coordinates specified
	 * 
	 * @param spawnPos The spawn position
	 */
	public Pipe(Vector2 spawnPos, Texture pipeTexture) {
		super(spawnPos, pipeTexture, new Vector2(2000, 1200));
		Vector2 tempVector =  spawnPos;
		tempVector.x += -1400;
		tempVector.y += 500;
		hitboxes[0] = new Rectangle(tempVector.x + 1000, 0, 30, 500);
		hitboxes[1] = new Rectangle(tempVector.x + 1000, 0, 30, 500); // 1158
		startTime = Instant.now();
		setPosition(tempVector);
	}


	/**
	 * Moves the pipe and it's hitboxes along the screen
	 */
	@Override
	public void update() {
		setPosition(new Vector2(getX() - speed, getY()));
		hitboxes[0].x = getX() + xOffset;
		hitboxes[0].y = getY() + yOffset;
		hitboxes[1].x = getX() + xOffset;
		hitboxes[1].y = getY() + yOffset2;

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
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[0].x + xOffset, hitboxes[0].y + yOffset),
				new Vector2(hitboxes[0].width, hitboxes[0].height), 5, Color.GREEN);
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[1].x + xOffset, hitboxes[1].y + yOffset2),
				new Vector2(hitboxes[1].width, hitboxes[1].height), 5, Color.GREEN);
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
