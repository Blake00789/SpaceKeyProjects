package com.dicycat.kroy.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.screens.GameScreen;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class FireTruck extends Entity{

	private int speed = 600;	//How fast the truck can move
	private Rectangle hitbox = new Rectangle(20, 45, 20, 20);

	protected HashMap<String,Integer> directions = new HashMap<String,Integer>(); // Dictionary to store the possible directions the truck can face

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
		//Move the hitbox to it's new centered position according to the sprites position.
		hitbox.setX(GetCentre().x);
		hitbox.setY(GetCentre().y);
		gameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2, Color.GREEN);
	}

	public Rectangle getHitbox(){
		return this.hitbox;
	}
}
