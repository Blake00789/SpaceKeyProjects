package com.dicycat.kroy.bullets;

import com.badlogic.gdx.math.Vector2;

public class Pattern {
	private Bullet[][] bullets;	//Bullets to fire
	private float waitTime;		//Time between bullets
	private Boolean aim;		//Should the bullets be targeted towards the player
	private float cooldown; 		//Time to wait after firing pattern
	int offset;
	int xtra;

	//Static directional
	public Pattern(int degree, int speed, int range, float timeBetweenBullets, int patternLength, int multi, float cooldown) {	//Constructor
		aim = false;
		waitTime = timeBetweenBullets;
		bullets = new Bullet[patternLength][multi];
		this.cooldown = cooldown;
		offset = (multi - (multi % 2)) / 2;
		xtra = (1-(multi % 2)) * 5;
		degree = 90 - degree;	//Convert normal bearings (0 is up, clockwise) to libgdx Vector2 degrees (0 is right, anticlockwise)

		Vector2 direction = Vector2.Zero;
		for (int i = 0; i < patternLength; i++) {
			for (int j = 0; j < multi; j++) {
				direction = new Vector2(1, 1);
				direction.setAngle(degree + ((j - offset) * 10) + xtra);
				bullets[i][j] = new Bullet(Vector2.Zero, direction, speed, range); //Create bullet
			}
		}
	}
	
	//Aimed shot
	public Pattern(int speed, int range, float timeBetweenBullets, int patternLength, int multi, float cooldown) {	//Constructor
		this.aim = true;
		waitTime = timeBetweenBullets;
		bullets = new Bullet[patternLength][multi];
		this.cooldown = cooldown;
		offset = (multi - (multi % 2)) / 2;
		xtra = (1-(multi % 2)) * 5;

		Vector2 direction = Vector2.Zero;
		for (int i = 0; i < patternLength; i++) {
			for (int j = 0; j < multi; j++) {
				bullets[i][j] = new Bullet(Vector2.Zero, direction, speed, range); //Create bullet
			}
		}
	}

	//Spiral
	public Pattern(Boolean clockwise, int startAngle, int rotations, int speed, int range, float timeBetweenBullets, int multi, float cooldown) {
		aim = false;
		waitTime = timeBetweenBullets;
		int patternLength = rotations * 36;
		bullets = new Bullet[patternLength][multi];
		this.cooldown = cooldown;
		offset = (multi - (multi % 2)) / 2;
		xtra = (1-(multi % 2)) * 5;

		int degree;	//Convert normal bearings (0 is up, clockwise) to libgdx Vector2 degrees (0 is right, anticlockwise)

		Vector2 direction = Vector2.Zero;
		for (int i = 0; i < patternLength; i++) {
			degree = (clockwise) ? 10 : -10;
			degree = 90 - (i*degree) + startAngle;
			for (int j = 0; j < multi; j++) {
				direction = new Vector2(1, 1);
				direction.setAngle(degree + ((j - offset) * 10) + xtra);
				bullets[i][j] = new Bullet(Vector2.Zero, direction, speed, range); //Create bullet
			}
		}
	}

	public Bullet[] BulletSet(int set) {
		return bullets[set];
	}

	public Bullet[] AimedSet(int set, Vector2 aimDir) {
		Vector2 direction;
		for (int i = 0; i < bullets[set].length; i++) {
			direction = new Vector2(1, 1);
			direction.setAngle(aimDir.angle() + ((i - offset) * 10) + xtra);
			bullets[set][i].ChangeDirection(direction);
		}
		return bullets[set];
	}

	public Boolean Aim() { return aim; }	//Getters
	public Bullet[][] Bullets(){return bullets;}
	public float WaitTime(){return waitTime;}
	public float Cooldown(){return cooldown;}
}
