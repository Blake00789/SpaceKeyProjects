package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GameTextures;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.debug.DebugCircle;
import com.dicycat.kroy.debug.DebugDraw;
import com.dicycat.kroy.debug.DebugLine;
import com.dicycat.kroy.debug.DebugRect;
import com.dicycat.kroy.entities.FireStation;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.Fortress;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.misc.WaterStream;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;


public class GameScreen implements Screen{

	public static enum State{
		PAUSE,
		RUN,
		RESUME,
		OPTIONS
	}
	public GameTextures textures;

	public static Boolean showDebug = false;

	public Kroy game;
	private OrthographicCamera gamecam;	//m 	//follows along what the port displays
	private Viewport gameport; 	//m
	private HUD hud;	//m
	public static boolean FOLLOWCAMERA = true;
	private PauseWindow pauseWindow;
	public static OptionsWindow optionsWindow;
	public static TiledGameMap gameMap;
	private Float[][] truckStats = {//Each list is a configuration of a specific truck. {speed, flowRate, capcity, range}
			{450f, 1f, 75f, 300f},		//Speed
			{300f, 1.5f, 75f, 300f},	//Flow rate
			{300f, 1f, 100f, 300f},		//Capacity
			{300f, 1f, 75f, 400f}		//Range
			};
	private int truckNum; // Identifies the truck thats selected in the menu screen
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering

	private Vector2 spawnPosition;	//Coords the player spawns at
	FireStation fireStation;	//Reference to fire statiom
	FireTruck player; //Reference to the player
	WaterStream waterStream; // Water stream on the screen
	List<GameObject> gameObjects, deadObjects;	//List of active game objects
	List<GameObject> toAdd;
	List<DebugDraw> debugObjects; //List of debug items
	List<GameObject> fortresses; //List of all fortresses
	public State state = State.RUN;

	public float gameTimer; //Timer to destroy station

	public GameScreen(Kroy _game, int truckNum) {
		game = _game;
		gamecam = new OrthographicCamera();    //m
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);	//m //Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(Kroy.batch, this.game);
		gameMap = new TiledGameMap();											//or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow();
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow();
		optionsWindow.visibility(false);
		textures = new GameTextures(truckNum);
		spawnPosition = new Vector2(3750, 4000);
		gameTimer = 60 * 15; //Set timer to 15 minutes
		this.truckNum = truckNum;
	}

	@Override
	public void show() {	//Screen first shown
		toAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();
		player = new FireTruck(spawnPosition.cpy(),truckStats[truckNum]); // Initialises the FireTruck

		gamecam.translate(new Vector2(player.getX(),player.getY()));// sets initial Camera position
		gameObjects.add(player);	//Player

		fireStation = new FireStation(new Vector2(3650,4050));
		gameObjects.add(fireStation);

		gameObjects.add(new Fortress(new Vector2(2903,3211), new Texture("cliffords tower.png"), new Vector2(256, 218)));
		gameObjects.add(new Fortress(new Vector2(3200,5681), new Texture("york minster.png"), new Vector2(256, 300)));
		gameObjects.add(new Fortress(new Vector2(2050,1937), new Texture("york museum.png"), new Vector2(400, 240)));

	}


	public void render(float delta) {		//Called every frame
		Gdx.input.setInputProcessor(pauseWindow.stage);  //DA CONTROLLARE
		pauseWindow.stage.act();

		switch (state) {
			case RUN:
			if (Gdx.input.isKeyPressed(Keys.P) || Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.M)|| Gdx.input.isKeyPressed(Keys.ESCAPE)){
				pauseWindow.visibility(true);
				pause();
			}
			gameTimer -= delta;		//Decrement timer
			if (gameTimer <= 0) {		//Once timer is over
				fireStation.ApplyDamage(100);	//Destroy fire station
			}

			UpdateLoop(); //Update all game objects positions but does not render them as to be able to render everything as quickly as possible

			gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers

			Kroy.batch.setProjectionMatrix(hud.stage.getCamera().combined);
			Kroy.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
			Kroy.batch.begin(); // Game loop Start

			hud.update(delta);

			renderObjects(); // Renders objects specified in the UpdateLoop() called previously

			Kroy.batch.end();

			gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

			hud.stage.draw();
			pauseWindow.stage.draw();

			if (showDebug) {
				DrawDebug(); //Draw all debug items as they have to be drawn outside the batch
			}

			break;
			case PAUSE:
				pauseWindow.stage.draw();
				clickCheck();
				break;
			case RESUME:
				pauseWindow.visibility(false);
				setGameState(State.RUN);
				break;
			default:
				break;
		}

	}

	//region Game Logic
	private void UpdateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject gObject : gameObjects) {	//Go through every game object
			gObject.Update();						//Update the game object
			if (gObject.CheckRemove()) {				//Check if game object is to be removed
				toRemove.add(gObject);					//Set it to be removed
			}else {
				objectsToRender.add(gObject);
			}
		}
		for (GameObject rObject : toRemove) {	//Remove game objects set for removal
			gameObjects.remove(rObject);
			if (rObject.checkDisplayable()) {
				deadObjects.add(rObject);
			}
		}
		for (GameObject aObject : toAdd) {		//Add game objects to be added
			gameObjects.add(aObject);
		}
		toAdd.clear();// Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed bases)
			objectsToRender.add(dObject);
		}
		if (player.CheckRemove()) {
			respawn();
		}

	}

	public void renderObjects() {// Renders the objects in "objectsToRender" then clears the list
		for (GameObject object : objectsToRender) {
			object.Render(Kroy.batch);
		}
		objectsToRender.clear();
	}

	public void AddGameObject(GameObject gameObject) {	//Add a game object next frame
		toAdd.add(gameObject);
	}

	public FireTruck getPlayer() {
		return player;
	}

	public WaterStream getWaterStream() {
		return waterStream;
	}

	private void DrawDebug() {		//Draws all debug objects for one frame
		for (DebugDraw dObject : debugObjects) {
			dObject.Draw(gamecam.combined);
		}
		debugObjects.clear();
	}

	public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
		debugObjects.add(new DebugLine(start, end, lineWidth, colour));
	}

	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
	}



	public void DrawRect(Vector2 bottomLeft, Vector2 dimensions, int lineWidth, Color colour) {
		debugObjects.add(new DebugRect(bottomLeft, dimensions, lineWidth, colour));
	}

	public void updateCamera() {// updates the position of the camera to have the truck centre
		gamecam.position.lerp(new Vector3(player.getX(),player.getY(),gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);				//m
	}

	@Override
	public void pause() {
		setGameState(State.PAUSE);

	}

	@Override
	public void resume() {
		setGameState(State.RESUME);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	public void setGameState(State s){
	    state = s;
	}

	public GameObject getGameObject(int index) {
		if (index <= (gameObjects.size()-1)) {
			return gameObjects.get(index);
		}else {
			return null;
		}
	}

	public List<GameObject> getGameObjects(){
		return gameObjects;
	}

	public void clickCheck() {
		//resume button
		pauseWindow.resume.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		pauseWindow.visibility(false);
				resume();
	    	}
	    });

		//exit button
		pauseWindow.exit.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		Gdx.app.exit();
	    	}
	    });
		//menu button
		pauseWindow.menu.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		dispose();
	    		game.backToMenu();
	    		return;
	    		}
	    });
	}

	public HUD getHud(){
		return hud;
	}

	public void gameOver() {
		game.setScreen(new GameOverScreen(game, truckNum));
	}

	public void respawn() {
		hud.updateLives();
		player = new FireTruck(spawnPosition.cpy(),truckStats[truckNum]);
		gameObjects.add(player);

	}



}
