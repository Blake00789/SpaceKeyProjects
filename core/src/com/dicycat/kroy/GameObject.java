package com.dicycat.kroy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.screens.GameScreen;

public abstract class GameObject {	//Basic object for all displayable objects
	protected GameScreen gameScreen;	//Reference to the gameScreen to be displayed on
	protected Sprite sprite;			//Sprite of the object
	protected Boolean remove;			//Should this GameObject be removed?
	protected float rotation = 0;		//Current angle the truck is facing in degrees
	
	public GameObject(GameScreen gScreen, Vector2 spawnPos, Texture image, Vector2 imSize, TiledGameMap map) {	//Constructor; takes the screen to be put on, spawn position vector, image and a vector for its size
		gameScreen = gScreen;
		sprite = new Sprite(image,(int) spawnPos.x ,(int) spawnPos.y ,(int) imSize.x,(int) imSize.y); // sprite class stores the texture position and size of the object
		remove = false;
		
	}
	
	public abstract void Update(); //Called every frame | Update the gameobject
	public abstract void Render(SpriteBatch batch); //Called every frame | Render the object
	
	
	
	public void changePosition(Vector2 v) { // changes current position by vector x
		sprite.setX(getX() + v.x);
		sprite.setY(getY() + v.y);
		}
	
	public void addX(float x) {
		sprite.setX(sprite.getX() + x);
	}
	
	public void addY(float y) {
		sprite.setY(sprite.getY() + y);
	}
	
	//Getters
	public Texture getTexture() { return sprite.getTexture(); }
	public float getHeight() { return sprite.getHeight(); }
	public float getWidth() {return sprite.getWidth(); }
	public float getX() { return sprite.getX(); }
	public float getY() { return sprite.getY(); }
	public Boolean CheckRemove() { return remove; }
	public float getOriginX () { return sprite.getOriginX(); }
	public float getOriginY() {return sprite.getOriginY(); }
	public  float getXScale() { return 1; }
	public float getYScale() { return 1; }
	public float getRotation() { return rotation; }	
	public int getTextureWidth() { return sprite.getTexture().getWidth(); }
	public int getTextureHeight() {	return sprite.getTexture().getHeight(); }
	public Sprite getSprite() { return sprite; }
	
	public Vector2 GetCentre() {	//Return centre of GameObject
		return new Vector2(sprite.getX() + (sprite.getX()/2), sprite.getY() + (sprite.getY()/2));
	}
	
	//Setters
	public void setPosition(Vector2 pos) {
		sprite.setX(pos.x);
		sprite.setY(pos.y);	
		}


	public void setRotation(float degrees) { // sets direction "degrees" to the direction currently facing
		rotation = degrees;
	}
	
	
}
