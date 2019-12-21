package com.dicycat.kroy.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.screens.GameScreen;

public class FireStation extends Entity {

	public FireStation(Vector2 spawnPos) {
		super(spawnPos, new Texture("FireStationTemp.png"), new Vector2(298,175), Integer.MAX_VALUE);
			
	}

	
	private void Die() { // Overwritten die implementation allows for removal from gameObjects List so to remove functionality but to display the broken building graphic
		sprite.setTexture(new Texture("FireStationTempDead.png"));
		setRemove(true);
		displayable = true;
	}

	public void Update(){
		if(playerInRadius()){
			GameScreen.mainGameScreen.GetPlayer().atStation();
		}
		
		if (Gdx.input.isKeyPressed(Keys.L)) { // temp to test broken tester
			Die();
		}
	}

	public Boolean playerInRadius() {
		Vector2 currentCoords = GameScreen.mainGameScreen.GetPlayer().GetCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, GetCentre().x, GetCentre().y) < radius ) { // checks the distance between the two entities
			return true; // returns true if distance between entity and player is less than radius of item
		}else {
			return false; // returns false otherwise
		}
	}
}
