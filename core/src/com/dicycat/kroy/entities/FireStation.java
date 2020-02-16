package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.MenuScreen;
import com.dicycat.kroy.screens.MenuScreen.MenuScreenState;

/**
 * Static friendly Entity.
 * Player heals and replenishes water when within its radius.
 * 
 * @author 
 *
 */
public class FireStation extends Entity {
	
	private static Texture textureLiving = new Texture("FireStationTemp.png");    //Kroy.mainGameScreen.textures.getFireStation();
	private static Texture texturedead =new Texture("FireStationTempDead.png");   // Kroy.mainGameScreen.textures.getFireStationDead();
	
	/** 
	 * 
	 */
	public FireStation() {
		super(new Vector2(3600,4100), textureLiving, new Vector2(298,175), 100);
	}

	/**
	 * Removes from active pool and displays destroyed state
	 */ 
	@Override
	public void die() { 
		super.die(); 
		sprite.setTexture(getTexturedead());
		displayable = true;
	}

	/**
	 * 
	 */
	@Override
	public void update(){
		
		if(playerInRadius()){
			Kroy.mainGameScreen.getPlayer().replenish();
		}
		if(playerInRadius() && Gdx.input.isKeyPressed(Keys.SPACE)) {
//			  
		}
		if (Kroy.mainGameScreen.gameTimer <= 0) {		//Once timer is over
			applyDamage(100);	//Destroy fire station
		}
	}

	/**
	 * @return texturedead
	 */
	public static Texture getTexturedead() {
		return texturedead;
	}

	/**
	 * change the FireStaion's texture to texturedead when it dies
	 */
	public static void setTexturedead(Texture texturedead) {
		FireStation.texturedead = texturedead;
	}
}
