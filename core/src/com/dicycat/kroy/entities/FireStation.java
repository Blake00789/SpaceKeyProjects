package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

public class FireStation extends Entity {

	public FireStation(Vector2 spawnPos) {
		super(spawnPos, new Texture("FireStationTemp.png"), new Vector2(298,175), 100);
	}
	
	protected void Die() { // Overwritten die implementation allows for removal from gameObjects List so to remove functionality but to display the broken building graphic
		sprite.setTexture(new Texture("FireStationTempDead.png"));
		setRemove(true);
		displayable = true;
	}

	public void Update(){
		if(playerInRadius()){
			Kroy.mainGameScreen.getPlayer().atStation();
		}
	}
}
