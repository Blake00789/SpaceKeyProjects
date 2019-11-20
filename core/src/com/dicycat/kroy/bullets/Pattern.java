package com.dicycat.kroy.bullets;

import com.badlogic.gdx.math.Vector2;

public class Pattern {
	private Bullet[][] bullets;
	private float waitTime;
	private Boolean aim;
	
	public Pattern(int degree, Boolean target, int speed, int range, float timeBetweenBullets, int patternLength, int multi) {
		aim = target;
		waitTime = timeBetweenBullets;
		bullets = new Bullet[patternLength][multi];
		Vector2 direction = Vector2.Zero;
		for (int i = 0; i < patternLength; i++) {
			for (int j = 0; j < multi; j++) {
				if (!aim) {
					direction = new Vector2(1, 1);
					direction.setAngle(degree + (j * 10));
				}
				bullets[i][j] = new Bullet(null, Vector2.Zero, direction, speed, range);
			}
		}
	}
	
	public Boolean Aim() {
		return aim;
	}
	public Bullet[][] Bullets(){return bullets;}
	public float WaitTime(){return waitTime;}
}
