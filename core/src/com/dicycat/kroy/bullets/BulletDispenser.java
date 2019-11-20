package com.dicycat.kroy.bullets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.dicycat.kroy.entities.Entity;

public class BulletDispenser {

	List<Pattern> patterns;	//Stores all patterns
	Entity owner;			//Entity the bulletDispnser is attached to
	
	int currentPattern;		//Current pattern to fire
	int currentBullet;		//Current bullet to fire
	float patternTime;		//Time between firing patterns
	float patternTimer;		//Time since last pattern
	float bulletTimer;		//Time since last bullet
	
	public BulletDispenser(Entity creator) 		//Constructor
	{
		owner = creator;
		patterns = new ArrayList<Pattern>();
		currentPattern = 0;
		bulletTimer = 0;
		patternTime = 2;
		patternTimer = 0;
	}
	
	public void AddPattern(Pattern pattern) {	//Add a pattern to the bullet dispensers arsenal
		patterns.add(pattern);
	}
	
	public Bullet[] Update(Boolean fire) {		//
		if (patterns.size() == 0) {	//No patterns -> no checks required
			return null;
		}
		patternTimer += Gdx.graphics.getDeltaTime();	//Increment timers by time passed
		bulletTimer += Gdx.graphics.getDeltaTime();
		
		//If should be firing, find any bullets that should be fired this frame
		//Then reset and timers and increment bullet/pattern as needed
		if (fire && patternTimer >= patternTime) {		
			if (bulletTimer >= patterns.get(currentPattern).WaitTime()) {
				bulletTimer = 0;
				Bullet[] toFire = patterns.get(currentPattern).Bullets()[currentBullet];	//Get bullets to fire
				currentBullet++;
				if (currentBullet >= patterns.get(currentPattern).Bullets().length) {
					currentPattern++;
					currentBullet = 0;
					patternTimer = 0;
					if (currentPattern >= patterns.size()) {
						currentPattern = 0;
					}
				}
				return toFire;
				
			}
		}
		
		return null;	//Not firing/no bullets
	}
}

