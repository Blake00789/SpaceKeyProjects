package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;

/**
 * Static friendly Entity.
 * Player heals and replenishes water when within its radius.
 * 
 * @author 
 *
 */
public class FireStation extends Entity {
	private static Texture texture = new Texture("FireStationTemp.png");

	/**
	 * 
	 */
	public FireStation() {
		super(new Vector2(Kroy.mainGameScreen.getSpawnPosition().add(-(texture.getWidth()/2), 100)), texture, new Vector2(texture.getWidth(),texture.getHeight()), 100);
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */
	@Override
	protected void Die() { 
		super.Die();
		sprite.setTexture(new Texture("FireStationTempDead.png"));
		displayable = true;
	}

	/**
	 *
	 */
	public void Update(){
		if(playerInRadius()){
			Kroy.mainGameScreen.getPlayer().replenish();
		}
	}
}
