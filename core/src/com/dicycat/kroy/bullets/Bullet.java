package com.dicycat.kroy.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.screens.GameScreen;

public class Bullet extends GameObject {
	private int speed;			//Speed of the bullet
	private Vector2 velocity;	//Direction and amount to travel
	private float maxDist;		//Max distance to travel
	private float travelDist; 	//Distance left to travel
	private Circle hitbox;


	public Bullet(Vector2 spawnPos, Vector2 direction, int _speed, float range) {	//Constructor
		super(spawnPos, GameScreen.mainGameScreen.textures.Bullet(), new Vector2(20,20));
		speed = _speed;
		velocity = direction.scl(speed);
		maxDist = range;
		hitbox = new Circle(spawnPos.x, spawnPos.y, 10);
	}

	public void Fire(Vector2 initial) {	//Reset bullet
		travelDist = maxDist;
		setPosition(initial);
		changePosition(new Vector2(-getOriginX(), -getOriginY()));
		remove = false;
	}

	public void ChangeDirection(Vector2 newDir) {
		velocity = newDir.scl(speed);
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

		//Moves hitbox according to the sprite.
		hitbox.x = GetCentre().x;
		hitbox.y = GetCentre().y;
		//Debug to draw the hitbox.
		//gameScreen.DrawCircle(new Vector2(hitbox.x, hitbox.y), hitbox.radius, 2, Color.RED);	//No, big lag
		//Check to see if bullet collides with the players truck.
		FireTruck truck = GameScreen.mainGameScreen.GetPlayer();
		if (truck.isAlive()) {
			if(Intersector.overlaps(hitbox, truck.getHitbox())){
				truck.ApplyDamage(1);
				remove = true;
			}
		}

	}

	public Circle GetHitbox(){
		return this.hitbox;
	}

}
