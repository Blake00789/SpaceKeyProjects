package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public class Bullet extends GameObject {
	private Vector2 velocity;
	private float maxDist;
	private float travelDist; //Max distance to travel
	
	
	public Bullet(GameScreen gScreen, Vector2 spawnPos, Vector2 direction, int speed, float range) {
		super(gScreen, spawnPos, new Texture("singleP.png"), new Vector2(20,20));
		velocity = direction.scl(speed);
		maxDist = range;
	}
	
	public void Fire(Vector2 initial) {
		travelDist = maxDist;
		position = initial;
		remove = false;
	}
	
	@Override
	public void Update() { //Called every frame
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());
		travelDist -= posChange.len();
		if (travelDist <= 0) {
			remove = true;
		}
		position.add(posChange);
		System.out.println(posChange);
	}

}

