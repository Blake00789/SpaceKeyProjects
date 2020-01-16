package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {	//Basic object for all displayable objects
	//protected GameScreen gameScreen;				//Reference to the gameScreen to be displayed on
	protected Sprite sprite;						//Sprite of the object
	protected Boolean remove, displayable;			//Should this GameObject be removed? Should this item be displayed?
	protected float rotation = 0;	//Current angle the truck is facing in degrees

	public GameObject(Vector2 spawnPos, Texture image, Vector2 imSize) {	//Constructor; takes the screen to be put on, spawn position vector, image and a vector for its size
		sprite = new Sprite(image,(int) spawnPos.x ,(int) spawnPos.y ,(int) imSize.x,(int) imSize.y); // sprite class stores the texture position and size of the object
		remove = false;
		displayable = false;
	}

	public abstract void Update(); //Called every frame | Update the game object

	public void Render(SpriteBatch batch) { //Called every frame | Render the object
		batch.draw(getTexture(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getXScale(), getYScale(), getRotation(), 0, 0, getTextureWidth(), getTextureHeight(), false, false);
	}

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
	public float getOriginX () { return sprite.getOriginX(); } // returns centre of sprite (25, 50) Use GetCentre for position on screen
	public float getOriginY() {return sprite.getOriginY(); }
	public  float getXScale() { return sprite.getScaleX(); }
	public float getYScale() { return sprite.getScaleY(); }
	public float getRotation() { return rotation; }
	public int getTextureWidth() { return sprite.getTexture().getWidth(); }
	public int getTextureHeight() {	return sprite.getTexture().getHeight(); }
	public Sprite getSprite() { return sprite; }
	public Vector2 getPosition() { return new Vector2(getX(), getY());  }
	public boolean checkDisplayable() { return displayable; }

	public Vector2 getCentre() {	//Return centre of GameObject
		return new Vector2(getOriginX()+getX(), getOriginY()+getY());
	}

	//Setters
	public void setPosition(Vector2 pos) {
		sprite.setX(pos.x);
		sprite.setY(pos.y);
	}


	public void setRotation(float degrees) { // sets direction "degrees" to the direction currently facing
		rotation = degrees;
	}
	

	public void setRemove(Boolean x){
		remove = x;
	}
	
	protected void Die() {
		remove = true;
	}

}
