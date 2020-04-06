package com.dicycat.kroy.entities;

import java.util.ArrayList;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.misc.StatBar;
import com.dicycat.kroy.misc.StatusIcon;
import com.dicycat.kroy.misc.WaterStream;
import com.dicycat.kroy.screens.GameScreen;

/**
 * Controlled by the player.
 * Automatically fires at hostile enemies when they're within range.
 * 
 * @author Riju De
 *
 */
public class FireTruck extends Entity{
	private float speed;	//How fast the truck can move
	private float flowRate;	//How fast the truck can dispense water
	private float maxWater; //How much water the truck can hold
	private float currentWater; //Current amount of water

 
	private Rectangle hitbox = new Rectangle(20, 45, 20, 20);  

	public final HashMap<String,Integer> DIRECTIONS = new HashMap<String,Integer>(); // Dictionary to store the possible directions the truck can face based on a key code created later
	protected final int[] ARROWKEYS = {Keys.UP, Keys.DOWN, Keys.RIGHT, Keys.LEFT}; // List of the arrow keys to be able to iterate through them later on
	protected Integer direction = 0; // Direction the truck is facing

	private StatusIcon defenceUpIcon;
	private StatusIcon unlimitedWaterIcon;
	private WaterStream water;
	private boolean firing;
	private boolean unlimitedWater;
	private boolean defenceUp;


	private boolean[] statusEffects = new boolean[4];
	private float range;

	private Vector2 statusIconPos = Vector2.Zero;

    // [FORTRESS_IMPROVEMENT] - START OF MODIFICATION  - [NP_STUDIOS] - [CASSIE_LILLYSTONE] ----
	private ArrayList fortressList; //New attribute
    // [FORTRESS_IMPROVEMENT] - END OF MODIFICATION  - [NP_STUDIOS] -----

	/**
	 * @param spawnPos
	 * @param truckStats
	 */

	public FireTruck(Vector2 spawnPos, Float[] truckStats, Texture texture) {
		super(spawnPos, texture, new Vector2(25,50), 100);
		AssignStatusEffectArray();
		DIRECTIONS.put("n",0);			//North Facing Direction (up arrow)
		DIRECTIONS.put("w",90);			//West Facing Direction (left arrow)
		DIRECTIONS.put("s",180);		//South Facing Direction (down arrow)
		DIRECTIONS.put("e",270);		//East Facing Direction (right arrow)

		DIRECTIONS.put("nw",45);		//up and left arrows
		DIRECTIONS.put("sw",135);		//down and left arrows
		DIRECTIONS.put("se",225);		//down and right arrows
		DIRECTIONS.put("ne",315);		//up and right arrows
		DIRECTIONS.put("",0); 			// included so that if multiple keys in the opposite direction are pressed, the truck faces north

		speed = truckStats[0]; 			// Speed value of the truck
		flowRate = truckStats[1];		// Flow rate of the truck (referred to as the damage of the truck in game)
		maxWater = truckStats[2];		// Capacity of the truck
		currentWater = truckStats[2];	// amount of water left, initialised as full in the beginning
		range = truckStats[3];			// Range of the truck

		firing = false;
		//Power ups
		defenceUp = false;
		unlimitedWater = false;

		water = new WaterStream(Vector2.Zero);

		defenceUpIcon = new StatusIcon(statusIconPos,"DefenceUp.png");
		Kroy.mainGameScreen.addGameObject(defenceUpIcon);

		unlimitedWaterIcon = new StatusIcon(statusIconPos,"UnlimitedWater.png");
		Kroy.mainGameScreen.addGameObject(unlimitedWaterIcon);

	}

		// STATBAR_REFACTOR_2 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Removed the creation of statbars from the firetruck class as adding the objects to the array
		// in GameScreen would cause problems with testing so we moved this functionality to the
		// GameScreen class itself.
		// STATBAR_REFACTOR_2 - END OF MODIFICATION  - NP STUDIOS

	// TESTING_REFACTOR_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Removed constructor created by previous group that was just for testing purposes
	// TESTING_REFACTOR_1 - END OF MODIFICATION  - NP STUDIOS

	/**
	 * When called, this method moves the truck by 1 unit of movement in the direction calculated in "updateDirection()"
	 */
	public void moveInDirection() {

		Vector2 movement = new Vector2(1,0); // movement represents where the truck is moving to. Initially set to (1,0) as this represents a unit vector

		movement.setAngle(direction+90); // rotates the vector to whatever angle it needs to face. 90 is added in order to get the keys matching up to movement in the right direction

		float posChange = speed * Gdx.graphics.getDeltaTime();	//Sets how far the truck can move this frame in the x and y direction
		Matrix3 distance = new Matrix3().setToScaling(posChange,posChange); // Matrix to scale the final normalised vector to the correct distance

		movement.nor(); // Normalises the vector to be a unit vector
		movement.mul(distance); // Multiplies the directional vector by the correct amount to make sure the truck moves the right amount

		Vector2 newPos = new Vector2(getPosition());
		if (!isOnCollidableTile(newPos.add(movement.x,0))) { // Checks whether changing updating x direction puts truck on a collidable tile
				setPosition(newPos); // updates x direction
		}
		newPos = new Vector2(getPosition());
		if (!isOnCollidableTile(newPos.add(0,movement.y))) { // Checks whether changing updating y direction puts truck on a collidable tile
			setPosition(newPos); // updates y direction
		}

		setRotation(direction);// updates truck direction
	}

	/**
	 * Method checks any arrow keys currently pressed and then converts them into a integer direction
	 * @return Direction to go
	 */
	public Integer updateDirection() { 
			String directionKey = ""; 
			String[] directionKeys = {"n", "s", "e", "w"}; // alphabet of directionKey

			for (int i = 0; i <= 3; i++) {// loops through the 4 arrow keys (Stored as KEYS above)
				if (Gdx.input.isKeyPressed(ARROWKEYS[i])) {
					directionKey+=directionKeys[i];
				}
			}

			if (directionKey.contains("ns")) {// makes sure direction doesn't change if both up and down are pressed
				directionKey = directionKey.substring(2);
			}
			if (directionKey.contains("ew")) {// makes sure direction doesn't change if both left and right are pressed
				directionKey = directionKey.substring(0, directionKey.length()-2);
			}
				
			return DIRECTIONS.get(directionKey);
	}

	/**
	 *main function of the firetruck: updates the direction in which 
	 *the firetruck is moving in as well as rendering its asset, moving 
	 *its hitbox and checking whether any entity is inside its range
	 */
	@Override
	public void update(){
		if (Gdx.input.isKeyPressed(ARROWKEYS[0]) ||
				Gdx.input.isKeyPressed(ARROWKEYS[1]) ||
				Gdx.input.isKeyPressed(ARROWKEYS[2]) ||
				Gdx.input.isKeyPressed(ARROWKEYS[3])) { // Runs movement code if any arrow key pressed

			setDirection(updateDirection());   //extended 
											   //updates direction based on current keyboard input
			moveInDirection(); // moves in the direction previously specified
		}

		Kroy.mainGameScreen.updateCamera(); // Updates the screen position to always have the truck roughly centre
		
		//Move the hit box to it's new centred position according to the sprite's position.
        hitbox.setCenter(getCentre().x, getCentre().y);
        
        //Draw debugs
    	Kroy.mainGameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2, Color.GREEN);
    	Kroy.mainGameScreen.DrawCircle(getCentre(), range, 1, Color.BLUE);

		UpdateStatusIcons();
		AssignStatusEffectArray();
		moveIconByFixedPoint();
		// STATBAR_REFACTOR_3 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Removed the statbars  update code from the firetruck class.
		// STATBAR_REFACTOR_3- END OF MODIFICATION  - NP STUDIOS

		//player firing
		ArrayList<GameObject> inRange = entitiesInRange();		//find list of enemies in range

		if(inRange.isEmpty() || (currentWater<=0)){				//Removes the water stream if nothing is in range
			firing=false;
			water.setRemove(true);
		}else if(!firing){					//Adds the water stream if something comes into range
			water= new WaterStream(Vector2.Zero);
			firing=true;
			Kroy.mainGameScreen.addGameObject(water);		//initialises water as a WaterStream
		}

		if (firing) {					//if the player is firing runs the PlayerFire method
			playerFire(inRange);
		}
	}

	private void moveIconByFixedPoint(){
		int offPoint = 0;
		if (defenceUpIcon.isEnabled()){
			offPoint += 15;
			defenceUpIcon.setPosition(getCentre().add(20 - offPoint,25));
		}
		if (unlimitedWaterIcon.isEnabled()){
			offPoint += 15;
			unlimitedWaterIcon.setPosition(getCentre().add(20 - offPoint,25));
		}
	}


	private void AssignStatusEffectArray(){
		this.statusEffects[0] = this.defenceUp;
		this.statusEffects[1] = this.unlimitedWater;
	}

	// Updates Icons based on if the FireTruck is currently effected by status elements
	// else clears icon textures if currently visible

	private void UpdateStatusIcons(){
		if (this.defenceUp){
			if (!(this.defenceUpIcon.isEnabled())) {
				this.defenceUpIcon.addIcon();
			}
		} else if (this.defenceUpIcon.isEnabled()){
			this.defenceUpIcon.removeIcon();
		}
		if (this.unlimitedWater){
			if (!(this.unlimitedWaterIcon.isEnabled())) {
				this.unlimitedWaterIcon.addIcon();
			}
		} else if (this.unlimitedWaterIcon.isEnabled()){
			this.unlimitedWaterIcon.removeIcon();
		}
	}
	
	/** 
	 * new
	 * @param direction
	 * change direction to the new direction 
	 */
	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	/**
	 * @param targets
	 * find and aim at the nearest target from an ArrayList of Gameobjects
	 */
	private void playerFire(ArrayList<GameObject> targets) {		//Method to find and aim at the nearest target from an ArrayList of Gameobjects
		GameObject currentGameObject=targets.get(0);
		GameObject nearestEnemy=targets.get(0);				//set nearest enemy to the first gameobject

		for (int i=1;i<targets.size();i=i+1) {									//iterates through inRange to find the closest gameobject
			currentGameObject=targets.get(i);
			if(Vector2.dst(nearestEnemy.getCentre().x, nearestEnemy.getCentre().y, getCentre().x, getCentre().y)>Vector2.dst(currentGameObject.getCentre().x,currentGameObject.getCentre().y,getCentre().x,getCentre().y)) {	//checks if the current enemy is the new nearest enemy
				nearestEnemy=targets.get(i);
			}
		}

		Vector2 direction = new Vector2();
		direction.set(new Vector2(nearestEnemy.getCentre().x,nearestEnemy.getCentre().y).sub(getCentre()));		//creates a vector2 distance of the line between the firetruck and the nearest enemy
		float angle = direction.angle();												//works out the angle of the water stream

		water.setRotation(angle);									//adjusts the water sprite to the correct length, position and angle
		water.setRange(direction.len());
		water.setPosition(getCentre().add(direction.scl(0.5f)));

		((Entity) nearestEnemy).applyDamage((float) (flowRate * Math.max(0.5, GameScreen.gameTimer * (1/600))));//Applies damage to the nearest enemy
		//POWERUPS - START OF MODIFICATION - NPSTUDIOS - BETHANY GILMORE
		if (!this.unlimitedWater) { //water only depletes while the power up is inactive.
			currentWater = currentWater - flowRate;//reduces the tank by amount of water used
		}
		//POWERUPS - END OF MODIFICATION - NPSTUDIOS - BETHANY GILMORE
	}

	/**
	 * Returns an array of all enemy GameObjects in range
	 * @return
	 */
	private ArrayList<GameObject> entitiesInRange(){
		ArrayList<GameObject> outputArray = new ArrayList<GameObject>();	//create array list to output enemies in range

		for (GameObject currentObject : Kroy.mainGameScreen.getGameObjects()) {		//iterates through all game objects
			if (((currentObject instanceof Fortress) || (currentObject instanceof UFO)) && (objectInRange(currentObject))){  	//checks if entity is in range and is an enemy
				outputArray.add(currentObject);												//adds the current entity to the output array list
			}
		}

		return (outputArray);
	}

	/**
	 * Check if a game object is in range of the fire truck
	 * @param object Object to check
	 * @return Is the object within range?
	 */
	public boolean objectInRange(GameObject object) {
		return (Vector2.dst(object.getCentre().x, object.getCentre().y, getCentre().x, getCentre().y)<range);
	}

	/**
	 *remove the FireTruck and Statbars
	 */
	@Override
	public void die() {
		super.die();
		water.setRemove(true);

		// STATBAR_REFACTOR_4 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Removed statbar remove code.
		// STATBAR_REFACTOR_4 - END OF MODIFICATION  - NP STUDIOS

        // [FORTRESS_IMPROVEMENT] - START OF MODIFICATION  - [NP_STUDIOS] - [CASSIE_LILLYSTONE] ----
        fortressList = Kroy.mainGameScreen.getFortresses(); //Create a new list which contains the fortresses

        for (Object fortress : fortressList){
            setHealthPoints(10); //Add 10 to the health of each fortress each time a truck is killed - so that fortresses improve their health over time
		// [FORTRESS_IMPROVEMENT] - END OF MODIFICATION  - [NP_STUDIOS] ----
        }

	} 

	/**
	 * @return hitbox
	 */
	public Rectangle getHitbox(){
		return this.hitbox;
	}
	
	/**
	 * @return direction
	 */
	public Integer getDirection() {
		return direction;
	}

	// STATBAR_REFACTOR_5 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Added getter for MaxWater needed for the statbars in the GameScreen class.
	// STATBAR_REFACTOR_5 - END OF MODIFICATION  - NP STUDIOS
	public float getMaxWater() {
		return maxWater;
	}

	/**
	 * extended
	 * Replenishes health and water
	 */
	public void replenish(){
		if(!(getCurrentWater() >= maxWater)){
			currentWater += 2;
		}
		if(!(getHealthPoints() >= maxHealthPoints)){
			setHealthPoints(2) ;
		}
	}

	/**
	 * @param pos
	 * @return
	 */
	public boolean isOnCollidableTile(Vector2 pos) {
		if(GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x + this.getWidth(), pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y+this.getHeight()).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x+this.getWidth(), pos.y+this.getHeight()).isCollidable()) {
			return true;
		}
		return false;
	}
	
	/**
	 * new
	 * @return currentWater
	 */
	public float getCurrentWater() {
		return currentWater;
	}
	
	/**
	 * new
	 * Increase the currentWater by the input parameter
	 */
	public void setCurrentWater(float x) {
		 currentWater += x;
	}
	/**
	 *
	 * @param flag
	 */
	public void setUnlimitedWater(Boolean flag){
		this.unlimitedWater = flag;
		UpdateStatusIcons();
		AssignStatusEffectArray();
	}

	/**
	 *
	 * @param flag
	 */
	public void setDefenceUp(Boolean flag){
		this.defenceUp = flag;
		UpdateStatusIcons();
		AssignStatusEffectArray();
	}

	public Boolean getDefenceUp(){
		return this.defenceUp;
	}
	//POWERUPS - END OF MODIFICATION - NPSTUDIOS

}
