package com.dicycat.kroy.bullets;

import com.badlogic.gdx.math.Vector2;

public class Pattern {
	private Bullet[][] bullets;	//Bullets to fire
	private float waitTime;		//Time between bullets
	private Boolean aim;		//Should the bullets be targeted towards the player
	
	public Pattern(int degree, Boolean target, int speed, int range, float timeBetweenBullets, int patternLength, int multi) {	//Constructor
		aim = target;
		waitTime = timeBetweenBullets;
		bullets = new Bullet[patternLength][multi];
		int offset = (multi - (multi % 2)) / 2;
		int xtra = (1-(multi % 2)) * 5;
		
		Vector2 direction = Vector2.Zero;
		for (int i = 0; i < patternLength; i++) {
			for (int j = 0; j < multi; j++) {
				if (!aim) {							//Get direction
					direction = new Vector2(1, 1);
					direction.setAngle(degree + ((j - offset) * 10) + xtra);
				}
				bullets[i][j] = new Bullet(null, Vector2.Zero, direction, speed, range); //Create bullet
			}
		}
	}
	
	public Boolean Aim() { return aim; }	//Getters
	public Bullet[][] Bullets(){return bullets;}
	public float WaitTime(){return waitTime;}
}
