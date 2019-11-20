package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.screens.GameScreen;

public abstract class GameObject {
	protected GameScreen gameScreen;
	protected Texture sprite;
	protected Vector2 position;
	protected Vector2 size;
	protected Boolean remove;
	
	public GameObject(GameScreen gScreen, Vector2 spawnPos, Texture img, Vector2 imSize) {
		gameScreen = gScreen;
		position = spawnPos;
		sprite = img;
		size = imSize;
		remove = false;
	}
	
	public abstract void Update(); //Called every frame
	

	public Texture GetSprite() { return sprite; }
	public Vector2 GetPos() { return position; }
	public Vector2 GetSize() { return size; }
	public Boolean CheckRemove() { return remove; }
	
}
