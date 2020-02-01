package com.dicycat.kroy.entities;

import java.time.Duration;
import java.time.Instant;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.MinigameScreen;

/**
 * Pipe used in minigame
 * 
 * @author Sam Hutchings
 *
 */
public class Pipe extends GameObject {
	private float speed = 3f;
	private Rectangle[] hitboxes = new Rectangle[2];
	private boolean gameEnd = false;
	private Instant startTime;

	private int lifeTime = 6;

	public Pipe(Vector2 spawnPos) {
		super(spawnPos, new Texture("pipe.png"), new Vector2(128, 2048));
		hitboxes[0] = new Rectangle(0, 0, 128, 900);
		hitboxes[1] = new Rectangle(1148, 0, 128, 900);
		startTime = Instant.now();
		setPosition(spawnPos);
	}

	@Override
	public void update() {
		//System.out.printf("%f, %f%n", getX(), getY());
		setPosition(new Vector2(getX() - speed, getY()));
		hitboxes[0].x = getX();
		hitboxes[0].y = getY();
		hitboxes[1].x = getX();
		hitboxes[1].y = getY() + 1148;

		for (Rectangle hitbox : hitboxes) {
			if (Intersector.overlaps(hitbox, MinigameScreen.getPlayer().getHitbox())) {
				gameEnd = true;
			}
		}
	}

	public Rectangle[] GetHitboxes() {
		return this.hitboxes;
	}

	public boolean gameEnd() {
		return gameEnd;
	}

	public boolean isRemove() {
		return ((Duration.between(startTime, Instant.now()).getSeconds()) > lifeTime);
	}

}
