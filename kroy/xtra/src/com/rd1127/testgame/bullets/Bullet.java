package com.rd1127.testgame.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.rd1127.testgame.GameObject;

public class Bullet extends GameObject {
	private Vector2 velocity;
	private float travelDist; //Max distance to travel
	
	
	public Bullet(Vector2 spawnPos, Vector2 direction, int speed, float maxDist) {
		super(spawnPos, new Texture("singleP.png"), new Vector2(20,20));
		velocity = direction.scl(speed);
		travelDist = maxDist;
	}
	
	@Override
	public void Update() { //Called every frame
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());
		travelDist -= posChange.len();
		if (travelDist <= 0) {
			remove = true;
		}
		position.add(posChange);
		System.out.println(travelDist);
	}

}
