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
	
	private static Texture textureLiving = Kroy.mainGameScreen.textures.getFireStation();
	private static Texture texturedead = Kroy.mainGameScreen.textures.getFireStationDead();
	
	/**
	 * 
	 */
	public FireStation() {
		super(new Vector2(Kroy.mainGameScreen.getSpawnPosition().add(-(textureLiving.getWidth()/2), 100)), textureLiving, new Vector2(textureLiving.getWidth(),textureLiving.getHeight()), 100);
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */
	@Override
	protected void Die() { 
		super.Die();
		sprite.setTexture(texturedead);
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
