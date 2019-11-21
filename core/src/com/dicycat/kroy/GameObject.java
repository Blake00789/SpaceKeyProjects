package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.screens.GameScreen;

public abstract class GameObject {	//Basic object for all displayable objects
	protected GameScreen gameScreen;	//Reference to the gameScreen to be displayed on
	protected Texture sprite;			//Texture of the
	protected Vector2 position;			//Coordinate to be displayed at
	protected Vector2 size;				//Size of the image
	protected Boolean remove;			//Should this GameObject be removed?
	
	public GameObject(GameScreen gScreen, Vector2 spawnPos, Texture img, Vector2 imSize) {	//Constructor
		gameScreen = gScreen;
		position = spawnPos;
		sprite = img;
		size = imSize;
		remove = false;
	}
	
	public abstract void Update(); //Called every frame
	
	//Getters
	public Texture GetSprite() { return sprite; }	
	public Vector2 GetPos() { return position; }
	public Vector2 GetSize() { return size; }
	public Boolean CheckRemove() { return remove; }
	
	public Vector2 GetCentre() {	//Return centre of GameObject
		return new Vector2(position.x + (size.x/2), position.y + (size.y/2));
	}
	
}
