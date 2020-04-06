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

	private Texture ufo, fireStation, fireStationDead, bullet, powerupBox;
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"),
			new Texture("york minster.png"), new Texture("Memorial.png") ,
			new Texture("york museum.png") , new Texture("University.png"),
			new Texture("Station.png")};

	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"),
			new Texture("york minster dead.png"), new Texture("york museum dead.png"),
			new Texture("york museum dead.png"), new Texture("york minster dead.png"),
			new Texture("york museum dead.png")};

	// REFACTOR_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Changed the array of strings containing the file names to an array of textures contaning all textures required
	// for the 6 trucks within the game to keep consistent as the above are arrays of textures and not filenames.
	private Texture[] truckTextures = {new Texture ("fireTruck1.png"), new Texture("fireTruck2.png"),
			new Texture("fireTruck3.png"), new Texture("fireTruck4.png"),
			new Texture("fireTruck5.png"), new Texture("fireTruck6.png")};
	// REFACTOR_1 - END OF MODIFICATION - NP STUDIOS

	// REFACTOR_2 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Removed truck texture here as all 6 are now required it was an unused variable left by the previous team.
	// Also removed 'truckNum' parameter which was used to get the correct texture previously. Also deleted bullet
	// texture as we removed the static access within the bullet class to aid testing.
	public GameTextures() {
	// REFACTOR_2 - END OF MODIFICATION - NP STUDIOS
		ufo = new Texture("ufo.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");
		powerupBox = new Texture("ItemBox.png");
		bullet = new Texture(("bullet.png"));
	}

	// REFACTOR_3 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Edited to work with the new texture array compared to the previous filename strings as well as improving the
	// Javadoc associated with this method.
	/**
	 * Gets the trucks texture from the array truckTextures
	 * @param truckNum the index of the truck's texture that is needed
	 * @return FireTruck's texture
	 */
	public Texture getTruck(int truckNum) {
		return truckTextures[truckNum];
	}
	// REFACTOR_3 - END OF MODIFICATION - NP STUDIOS

	public Texture getUFO() {
		return ufo;
	}

	// REFACTOR_4 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Fixed incorrect java doc as it said the return type was a list of textures when it is a single texture based
	// on an index input.
	/**
	 * Gets the fortress texture from the array of livingFortresses
	 * @param fortress index of the fortress texture required
	 * @return the alive fortress texture
	 */
	// REFACTOR_4 - END OF MODIFICATION  - NP STUDIOS
	public Texture getFortress(int fortress) {
		return livingFortresses[fortress];
	}

	// REFACTOR_5 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Fixed incorrect java doc as it said the return type was a list of textures when it is a single texture based
	// on an index input.
	/**
	 * @param fortress index of the fortress texture required
	 * @return the dead fortress texture
	 */
	// REFACTOR_5 - END OF MODIFICATION  - NP STUDIOS
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

	public Texture getPowerupBox() {
		return powerupBox;
	}
	public Texture getBullet(){
		return bullet;
	}
}
