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
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"), new Texture("york minster.png"), new Texture("york museum.png"),new Texture("cliffords tower.png"), new Texture("york minster.png"), new Texture("york museum.png")};
	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"), new Texture("york museum dead.png"),new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"), new Texture("york museum dead.png")};
	private String[] truckAddress = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png", "fireTruck5.png", "fireTruck6.png"};
	  
	 
	/**
	 * @param truckNum Which truck texture to get
	 */
	public GameTextures(int truckNum) {
		truck = new Texture(truckAddress[truckNum]);
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");	
	}
	
	/**
	 * @return FireTruck
	 */
	public Texture getTruck() {
		return truck;
	}
	
	/**
	 * Change trucks base on the input number
	 * @return FireTruck's texture
	 */
	public Texture getTruck(int truckNum) {
		return new Texture(truckAddress[truckNum]);
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
	 * @return the list of living Fortresses texture
	 */
	public Texture getFortress(int fortress) {
		return livingFortresses[fortress];
	}
	
	/**
	 * @param fortress
	 * @return the list of dead Fortresses texture
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
