package com.rd1127.testgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	protected Texture sprite;
	protected Vector2 position;
	protected Vector2 size;
	protected Boolean remove;
	
	public GameObject(Vector2 spawnPos, Texture img, Vector2 imSize) {
		position = spawnPos;
		sprite = img;
		size = imSize;
		remove = false;
	}
	
	public void Update() {	//Called every Frame
		
	}
	

	public Texture GetSprite() { return sprite; }
	public Vector2 GetPos() { return position; }
	public Vector2 GetSize() { return size; }
	public Boolean CheckRemove() { return remove; }
	
}
