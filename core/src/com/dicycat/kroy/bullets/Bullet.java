package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

public class Bullet extends GameObject {
	private Vector2 velocity;	//Direction and distance to travel
	private float maxDist;		//Max distance to travel
	private float travelDist; 	//Distance left to travel
	
	
	public Bullet(GameScreen gScreen, Vector2 spawnPos, Vector2 direction, int speed, float range) {	//Constructor
		super(gScreen, spawnPos, new Texture("singleP.png"), new Vector2(20,20));
		velocity = direction.scl(speed);
		maxDist = range;
	}
	
	public void Fire(Vector2 initial) {	//Reset bullet
		travelDist = maxDist;
		setPosition(initial);
		remove = false;
	}
	
	public void move(Vector2 change) { // bullet movement (vector addition)
		Vector2 currentPos = new Vector2(getX(),getY());
		setPosition(currentPos.add(change));
	}
	
	public void Update() { //Called every frame
		Vector2 posChange = velocity.cpy().scl(Gdx.graphics.getDeltaTime());	//Calculate distance to move
		travelDist -= posChange.len();
		if (travelDist <= 0) {		//Remove if travelled required distance
			remove = true;
		}
		move(posChange); // update bullet position
		gameScreen.DrawCircle(GetCentre(), 8, 1, Color.PINK);
	}

	@Override
	public void Render(SpriteBatch batch) {
		//batch.draw(GetSprite(), GetPos().x, GetPos().y, GetSize().x, GetSize().y);
	}

}

