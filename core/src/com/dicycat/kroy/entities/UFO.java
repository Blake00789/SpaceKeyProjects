package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;

public class UFO extends Entity {
	
	BulletDispenser dispenser;

	public UFO(GameScreen gScreen, Vector2 spawnPos) {
		super(gScreen, spawnPos, new Texture("ufo.png"), new Vector2(80, 80));
		dispenser = new BulletDispenser(this);
		dispenser.AddPattern(new Pattern(250, false, 100, 500, 0.5f, 8, 5));
	}

	@Override
	public void Update() {
		//movement
		
		//weapons
		Bullet[] toShoot = dispenser.Update(true);
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.Fire(GetCentre());
				gameScreen.addGameObject(bullet);
			}
		}
	}
	
}
