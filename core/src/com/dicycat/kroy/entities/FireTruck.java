package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;

import java.util.List;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class FireTruck extends Entity{
	private int speed = 600;	//How fast the truck can move
	protected HashMap<String,Integer> directions = new HashMap<String,Integer>(); // Dictionary to store the possible directions the truck can face
	ArrayList<Float> inRange;
	float fireTruckX;
	float fireTruckY;
	float nearestEnemyX;
	float nearestEnemyY;
	Vector2 nearestEnemy;
	
	
	public FireTruck(GameScreen gScreen, Vector2 spawnPos) {	//Constructor
		super(gScreen, spawnPos, new Texture("fireTruck.png"), new Vector2(50,100));
		
		directions.put("n",0);
		directions.put("w",90);
		directions.put("s",180);
		directions.put("e",270);
		
		directions.put("nw",45);
		directions.put("sw",135);
		directions.put("se",225);
		directions.put("ne",315);
		directions.put("",0); // included so that if multiple keys in the opposite direction are pressed, the truck faces north
		
	}

	public void moveInDirection(int keyPressed) {// movement method for fireTruck, keyPressed is a 4 bit code of 0s and 1s, where a 1 represents a certain arrow/WASD key
		
		if (keyPressed != 0) { // will not run main logic if no key is pressed
			String[] keys = {"up","down","left","right"};

			float posChange = speed * Gdx.graphics.getDeltaTime();	//Get how far the truck can move this frame
			Matrix3 distance = new Matrix3().setToScaling(posChange,posChange); // Matrix to scale the final normalised vector to the correct distance
			
			Vector3 movement = new Vector3(0,0,1); // vector to store the movement of the truck for this frame
			Vector3[] translations = {new Vector3(0, 1,1),new Vector3(0, -1,1), new Vector3(1, 0,1), new Vector3(-1,0,1)}; // Array of Vectors to store 
																																//the possible movements the truck can make
			String directionKey = ""; // string to convert keyPressed code into dictionary key to get direction 
			String[] directionKeys = {"n", "s", "e", "w"}; // alphabet of directionKey
			
			for (int i = 0; i <= 3; i ++) {// loop to make calculate movement changes, works by detecting any 1s in relevant slots then making changes as set up in the arrays above
				if (keyPressed%2 == 1) {
					directionKey+=directionKeys[i];
					movement.add(translations[i]);
				}
				keyPressed = (int) Math.floor(keyPressed/10);
			}
			
			if (directionKey.contains("ns")) {// makes sure direction doesn't change if both up and down are pressed
				directionKey = directionKey.substring(2);
			}
			if (directionKey.contains("ew")) {// makes sure direction doesn't change if both up and down are pressed
				directionKey = directionKey.substring(0, directionKey.length()-2);
			}
			
			movement.nor(); // Vector3 method to normalise coordinate vector
			movement.mul(distance); // multiplies normalised vector by distance to represent speed truck should be travelling
			changePosition(new Vector2(movement.x,movement.y));// updates truck coordinates
			setRotation(directions.get(directionKey));// updates truck direction
		
		}
	}
	
	public void Update() 
	{
		
		int keyDetect = 0;
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {	//Check all inputs for player movement
			keyDetect += 1;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			keyDetect += 10;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			keyDetect += 100;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			keyDetect += 1000;
		}
		
		moveInDirection(keyDetect);

		gameScreen.DrawRect(GetCentre(), new Vector2(20, 20), 2, Color.FIREBRICK);
		//gameScreen.DrawRect(position, size, 2, Color.FIREBRICK);
		
		//player firing
		
		fireTruckX=this.getX();
		fireTruckY=this.getY();
		//lineDraw.dispose();
		inRange = FireTruck.EntitiesInRange(fireTruckX, fireTruckY, gameScreen, 100f);
		
		nearestEnemyX=100000f;	
		nearestEnemyY=100000f;													//set nearest enemy to max value
		for (int i=0;i<inRange.size();i=i+2) {									//iterates through inRange to find the closest enemy from x and y values
			if(Vector2.dst(nearestEnemyX, nearestEnemyY, fireTruckX, fireTruckY)>Vector2.dst(inRange.get(i),inRange.get(i+1),fireTruckX,fireTruckY)) {
				nearestEnemyX=inRange.get(i);
				nearestEnemyY=inRange.get(i+1);
			}
		}
		nearestEnemy=new Vector2(nearestEnemyX,nearestEnemyY);
		if (false==((nearestEnemyX==100000f)&&(nearestEnemyY==100000f))) {		//checks there is actually an enemy in range
			gameScreen.DrawLine(this.GetCentre(),nearestEnemy, 10, Color.BLUE);	//draws a line from the firetruck to the nearestenemy
		}
		
		
	}
	
	private static ArrayList<Float> EntitiesInRange(float centreX, float centreY, GameScreen gScreen, float range){	//method to return an array of x,y coordinates of all Enemies in range
		ArrayList<Float> tempArray = new ArrayList<Float>();
		boolean x = true;
		int counter = 1;
		GameObject tempGameObject;
		while (x){
			tempGameObject=gScreen.getGameObject(counter);
			if (tempGameObject==null) {
				x=false;
			}else if (((tempGameObject instanceof UFO) && (Vector2.dst(tempGameObject.getX(), tempGameObject.getY(), centreX, centreY)<range))){  //add in all gameobjects to be avoided
				tempArray.add(tempGameObject.getX());
				tempArray.add(tempGameObject.getY());
			}
				
			++counter;
		}
		return (tempArray);
	}
}
