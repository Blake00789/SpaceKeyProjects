package com.dicycat.kroy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;

/**
 * Stores textures for classes to reference.
 * This means multiple of the same sprite use the same reference.
 * Because of this, render calls are reduced.
 * 
 * @author Riju De
 *
 */
public class GameTextures {
	private Texture truck, ufo, bullet, fireStation, fireStationDead;
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"), new Texture("york minster.png"), new Texture("Memorial.png") , new Texture("york museum.png") , new Texture("University.png"), new Texture("Station.png")};
	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"), new Texture("york museum dead.png"), new Texture("york museum dead.png"), new Texture("york minster dead.png"), new Texture("york museum dead.png")};
	private Texture[] truckAddress = {new Texture ("fireTruck1.png"), new Texture("fireTruck2.png"),
			new Texture("fireTruck3.png"), new Texture("fireTruck4.png"),
			new Texture("fireTruck5.png"), new Texture("fireTruck6.png")};
	// Lucy - change from string array to texture array
	 
	/**
	 * @param truckNum Which truck texture to get
	 */
	public GameTextures(int truckNum) {
		truck = truckAddress[truckNum]; // lucy - used new array
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");	
	}
	
	// deleted duplicated unused getter
	
	/**
	 * Change trucks base on the input number
	 * @return FireTruck's texture Lucy - edited to use new array
	 */
	public Texture getTruck(int truckNum) {
		return truckAddress[truckNum];
	}
	
	/**
	 * @return UFO
	 */
	public Texture getUFO() {
		return ufo;
	}
	
	/**
	 * @return Bullet's texture
	 */
	public Texture getBullet() {
		return bullet;
	}
	
	/**
	 * @param fortress
	 * @return the list of living Fortress's texture
	 */
	public Texture getFortress(int fortress) {
		return livingFortresses[fortress];
	}
	
	/**
	 * @param fortress
	 * @return the list of dead Fortress's texture
	 */
	public Texture getDeadFortress(int fortress) {
		return deadFortresses[fortress];
	}
	
	/**
	 * @return the FireStation's texture
	 */
	public Texture getFireStation() {
		return fireStation;
	}
	
	/**
	 * @return the texture of the dead FireStation
	 */
	public Texture getFireStationDead() {
		return fireStationDead;
	}
}
