package com.dicycat.kroy.bullets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.dicycat.kroy.entities.Entity;

public class BulletDispenser {

	List<Pattern> patterns;
	Entity owner;
	
	int currentPattern;
	int currentBullet;
	float patternTime;
	float patternTimer;
	float bulletTimer;
	
	public BulletDispenser(Entity creator) 
	{
		owner = creator;
		patterns = new ArrayList<Pattern>();
		currentPattern = 0;
		bulletTimer = 0;
		patternTime = 2;
		patternTimer = 0;
	}
	
	public void AddPattern(Pattern pattern) {
		patterns.add(pattern);
	}
	
	public Bullet[] Update(Boolean fire) {
		if (patterns.size() == 0) {
			return null;
		}
		patternTimer += Gdx.graphics.getDeltaTime();
		bulletTimer += Gdx.graphics.getDeltaTime();
		if (fire && patternTimer >= patternTime) {
			if (bulletTimer >= patterns.get(currentPattern).WaitTime()) {
				bulletTimer = 0;
				Bullet[] toFire = patterns.get(currentPattern).Bullets()[currentBullet];
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
		return null;
	}
}

