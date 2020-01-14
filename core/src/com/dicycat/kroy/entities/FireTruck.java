package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dicycat.kroy.misc.WaterBar;
import com.dicycat.kroy.misc.WaterStream;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Array;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.screens.GameScreen;
import java.util.Dictionary;
import java.util.HashMap;

public class FireTruck extends Entity{
	private float speed;	//How fast the truck can move
	private float flowRate;	//How fast the truck can dispense water
	private float maxWater; //How much water the truck can hold
	private float currentWater; //Current amount of water
	private boolean debug;

	private Rectangle hitbox = new Rectangle(20, 45, 20, 20);

	protected final HashMap<String,Integer> DIRECTIONS = new HashMap<String,Integer>(); // Dictionary to store the possible directions the truck can face based on a key code created later
	protected final int[] ARROWKEYS = {Keys.UP, Keys.DOWN, Keys.RIGHT, Keys.LEFT}; // List of the arrow keys to be able to iterate through them later on
	protected Integer direction = 0; // Direction the truck is facing
	
	WaterStream water;
	WaterBar tank;
	boolean firing;
	float range;
	Array<Sprite> fireTruckSprites; //MC
	TextureAtlas atlas; //MC
	TextureRegion[][] textureByDirection;

	public FireTruck(Vector2 spawnPos, Float[] truckStats) {	//Constructor
		super(spawnPos, GameScreen.mainGameScreen.textures.Truck(), new Vector2(25,50), 100);
		

		DIRECTIONS.put("n",0);		//North Facing Direction (up arrow)
		DIRECTIONS.put("w",90);		//West Facing Direction (left arrow)
		DIRECTIONS.put("s",180);	//South Facing Direction (down arrow)
		DIRECTIONS.put("e",270);	//East Facing Direction (right arrow)

		DIRECTIONS.put("nw",45);	//up and left arrows
		DIRECTIONS.put("sw",135);	//down and left arrows
		DIRECTIONS.put("se",225);	//down and right arrows
		DIRECTIONS.put("ne",315);	//up and right arrows
		DIRECTIONS.put("",0); 		// included so that if multiple keys in the opposite direction are pressed, the truck faces north
		
		textureByDirection = TextureRegion.split(GameScreen.mainGameScreen.textures.Truck(), 32, 32);
		
		speed = truckStats[0]; 			// Speed value of the truck
		flowRate = truckStats[1];		// Flow rate of the truck (referred to as the damage of the truck in game)
		maxWater = truckStats[2];		// Capacity of the truck
		currentWater = truckStats[2];	// amount of water left, initialised as full in the beginning
		range = truckStats[3];			// Range of the truck
				
		firing = false;
		water= new WaterStream(Vector2.Zero);

		debug=true;
		tank= new WaterBar(new Vector2(0,0));
		GameScreen.mainGameScreen.AddGameObject(tank);



	}
	
	public void moveInDirection() {//When called, this method moves the truck by 1 unit of movement in the direction calculated in "updateDirection()"
		
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

	public Integer updateDirection() {// Method checks any arrow keys currently pressed and then converts them into a integer direction
		
			String directionKey = ""; //
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
	
	
	public boolean checkEntities() {

		return false;
	}

	public void Update(){

		
		if (Gdx.input.isKeyPressed(ARROWKEYS[0])||
				Gdx.input.isKeyPressed(ARROWKEYS[1])||
				Gdx.input.isKeyPressed(ARROWKEYS[2])||
				Gdx.input.isKeyPressed(ARROWKEYS[3])) { // Runs movement code if any arrow key pressed
			
			direction = updateDirection(); // updates direction based on current keyboard input
			moveInDirection(); // moves in the direction previously specified
		}
		
		if (GameScreen.mainGameScreen.FOLLOWCAMERA) {
		    GameScreen.mainGameScreen.updateCamera();// Updates the screen position to always have the truck roughly centre
		}
		//Move the hitbox to it's new centered position according to the sprites position.
        hitbox.setCenter(GetCentre().x, GetCentre().y);
		GameScreen.mainGameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2, Color.GREEN);

		//water bar update
		if(debug) {
			tank.setPosition(GetCentre().add(0,20));
			tank.setTankDisplay((currentWater/maxWater)*50);
		}

		//player firing

		ArrayList<GameObject> inRange = EntitiesInRange();		//find list of enemies in range

		if(inRange.isEmpty() || (currentWater<=0)){				//Removes the water stream if nothing is in range
			firing=false;
			water.setRemove(true);
		}else if(!firing){					//Adds the water stream if something comes into range
			water= new WaterStream(Vector2.Zero);
			firing=true;
			GameScreen.mainGameScreen.AddGameObject(water);

		}

		if (firing) {					//check if any enemy is in range and firetruck has water
			PlayerFire(inRange);
		}
	}


	private void PlayerFire(ArrayList<GameObject> targets) {		//Method to find and aim at the nearest target from an ArrayList of coordinates
		GameObject currentGameObject=targets.get(0);
		GameObject nearestEnemy=targets.get(0);				//set nearest enemy to the first gameobject


		for (int i=1;i<targets.size();i=i+1) {									//iterates through inRange to find the closest gameobject
			currentGameObject=targets.get(i);
			if(Vector2.dst(nearestEnemy.GetCentre().x, nearestEnemy.GetCentre().y, GetCentre().x, GetCentre().y)>Vector2.dst(currentGameObject.GetCentre().x,currentGameObject.GetCentre().y,GetCentre().x,GetCentre().y)) {	//checks if the current enemy is the new nearest enemy
				nearestEnemy=targets.get(i);
			}
		}

		Vector2 direction = new Vector2();
		direction.set(new Vector2(nearestEnemy.GetCentre().x,nearestEnemy.GetCentre().y).sub(GetCentre()));		//creates a vector2 distance of the line between the firetruck and the nearest enemy
		float angle = direction.angle();												//works out the angle that the water needs

		water.setRotation(angle);									//adjusts the water sprite to the correct length, position and angle
		water.setRange(direction.len());
		water.setPosition(GetCentre().add(direction.scl(0.5f)));

		((Entity) nearestEnemy).ApplyDamage(1*flowRate);
		currentWater=currentWater-flowRate;

	}

	private ArrayList<GameObject> EntitiesInRange(){	//method to return an array of x,y coordinates of all Enemies in range
		ArrayList<GameObject> tempArray = new ArrayList<GameObject>();
		boolean x = true;
		int counter = 1;
		GameObject tempGameObject;
		while (x){
			tempGameObject=GameScreen.mainGameScreen.getGameObject(counter);
			if (tempGameObject==null) {
				x=false;
			}else if ((((tempGameObject instanceof Fortress) || (tempGameObject instanceof UFO)) && (Vector2.dst(tempGameObject.GetCentre().x, tempGameObject.GetCentre().y, GetCentre().x, GetCentre().y)<range))){  //checks if entity is in range and is an enemy
				tempArray.add(tempGameObject);
			}

			++counter;
		}

		return (tempArray);
	}



	public Rectangle getHitbox(){
		return this.hitbox;
	}
	
	public Integer getDirection() {
		return direction;
	}

	public void atStation(){
		if(!(currentWater >= maxWater)){
			currentWater += 2;
		}
		if(!(healthPoints >= 100)){
			healthPoints += 2;
		}
	}

	@Override
	public void Render(SpriteBatch batch) {
		batch.setColor(0.1f * healthPoints, 0.1f * healthPoints, 0.1f * healthPoints, 1);
		super.Render(batch);
		batch.setColor(Color.WHITE);
	}

	public boolean isOnCollidableTile(Vector2 pos) {
		if(GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x + this.getWidth(), pos.y).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x, pos.y+this.getHeight()).isCollidable()
				||GameScreen.gameMap.getTileTypeByLocation(0, pos.x+this.getWidth(), pos.y+this.getHeight()).isCollidable()) {
			return true;
		}
		return false;
	}




}
