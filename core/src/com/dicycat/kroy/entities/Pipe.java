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
 * Pipe used in minigame
 * 
 * @author Sam Hutchings
 *
 */
public class Pipe extends GameObject {
	private float speed = 4f;
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
		setPosition(new Vector2(getX() - speed, getY()));
		hitboxes[0].x = getX();
		hitboxes[0].y = getY();
		hitboxes[1].x = getX();
		hitboxes[1].y = getY() + 1148;
		//if (Kroy.debug) {
		//	debugDraw();
		//}
		
	}

	public Rectangle[] getHitboxes() {
		return this.hitboxes;
	}
	
	public void debugDraw() {
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[0].x, hitboxes[0].y), new Vector2(hitboxes[0].width, hitboxes[0].height), 2,
				Color.RED);
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitboxes[1].x, hitboxes[1].y), new Vector2(hitboxes[1].width, hitboxes[1].height), 2,
				Color.RED);
	}
	

	public boolean gameEnd(Goose player) {
		for (Rectangle hitbox : hitboxes) {
			if (Intersector.overlaps(hitbox, player.getHitbox())) {
				gameEnd = true;
			}
		}
		return gameEnd;
	}
	
	void setLifeTime(int x) {
		lifeTime = x;
	}

	public boolean isRemove() {
		return ((Duration.between(startTime, Instant.now()).getSeconds()) > lifeTime);
	}

}
