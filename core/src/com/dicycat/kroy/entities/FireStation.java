package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

public class FireStation extends Entity {

	private static Texture texture = new Texture("FireStationTemp.png");

	public FireStation() {
		super(new Vector2(Kroy.mainGameScreen.getSpawnPosition().add(-(texture.getWidth()/2), 100)), texture, new Vector2(texture.getWidth(),texture.getHeight()), 100);
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
