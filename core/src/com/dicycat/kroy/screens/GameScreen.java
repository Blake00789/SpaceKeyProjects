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


/**
 * Contains the main game logic
 * 
 * @author riju8
 *
 */
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
	private OrthographicCamera gamecam;	//follows along what the port displays
	private Viewport gameport;
	private HUD hud;
	private PauseWindow pauseWindow;
	public static OptionsWindow optionsWindow;
	public static TiledGameMap gameMap;
	private Float[][] truckStats = {	//Each list is a configuration of a specific truck. {speed, flowRate, capacity, range}
			{450f, 1f, 400f, 300f},		//Speed
			{300f, 1.5f, 400f, 300f},	//Flow rate
			{300f, 1f, 500f, 300f},		//Capacity
			{300f, 1f, 400f, 450f}		//Range
		};
	private int truckNum; // Identifies the truck thats selected in the menu screen
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private int fortressesCount;
	private Vector2 spawnPosition;	//Coordinates the player spawns at
	FireStation fireStation;	//Reference to fire station
	FireTruck player; //Reference to the player
	WaterStream waterStream; // Water stream on the screen
	List<GameObject> gameObjects, deadObjects;	//List of active game objects
	List<GameObject> toAdd;
	List<DebugDraw> debugObjects; //List of debug items
	public State state = State.RUN;

	public float gameTimer; //Timer to destroy station

	/**
	 * @param _game
	 * @param truckNum
	 */
	public GameScreen(Kroy _game, int truckNum) {
		game = _game;
		gamecam = new OrthographicCamera();
		gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);	//Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(game.batch, this.game);
		gameMap = new TiledGameMap();										//or FitPort to make it fit into a specific width/height ratio
		pauseWindow = new PauseWindow(game);
		pauseWindow.visibility(false);
		optionsWindow = new OptionsWindow(game);
		optionsWindow.visibility(false);
		textures = new GameTextures(truckNum);
		spawnPosition = new Vector2(3750, 4000);
		gameTimer = 60 * 15; //Set timer to 15 minutes
		this.truckNum = truckNum;
	}

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		toAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();
		player = new FireTruck(spawnPosition.cpy(),truckStats[truckNum]); // Initialises the FireTruck

		gamecam.translate(new Vector2(player.getX(),player.getY())); // sets initial Camera position
		gameObjects.add(player);	//Player
		
		fireStation = new FireStation();
		gameObjects.add(fireStation);
		gameObjects.add(new Fortress(new Vector2(2903,3211),textures.getFortress(0), textures.getDeadFortress(0), new Vector2(256, 218)));
		gameObjects.add(new Fortress(new Vector2(3200,5681), textures.getFortress(1), textures.getDeadFortress(1), new Vector2(256, 320)));
		gameObjects.add(new Fortress(new Vector2(2050,1937), textures.getFortress(2), textures.getDeadFortress(2), new Vector2(400, 240)));

	}

	/**
	 * Called every frame
	 */
	public void render(float delta) {
		Gdx.input.setInputProcessor(pauseWindow.stage);  //Set input processor
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

				game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
				game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
				game.batch.begin(); // Game loop Start

				hud.update(delta);

				renderObjects(); // Renders objects specified in the UpdateLoop() called previously

				game.batch.end();

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

	/**
	 * Updates all the active gameobjects and adds them to the render queue.
	 * Removes gameobjects from the active pool if they are marked for removal.
	 * Adds new gameobjects.
	 * Adds dead objects to render queue.
	 * Respawns the player if necessary.
	 */
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
		toAdd.clear();	// Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed bases)
			objectsToRender.add(dObject);
		}
		if (player.CheckRemove()) {	//If the player is set for removal, respawn
			respawn();
		}
	}

	/**
	 * Renders the objects in "objectsToRender" then clears the list
	 */
	public void renderObjects() {
		for (GameObject object : objectsToRender) {
			object.Render(game.batch);
		}
		objectsToRender.clear();
	}

	/**
	 * Add a game object next frame
	 * @param gameObject gameObject to be added
	 */
	public void AddGameObject(GameObject gameObject) {
		toAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * @return player
	 */
	public FireTruck getPlayer() {
		return player;
	}

	public WaterStream getWaterStream() {
		return waterStream;
	}

	/**
	 * Draws all debug objects for one frame
	 */
	private void DrawDebug() {
		for (DebugDraw dObject : debugObjects) {
			dObject.Draw(gamecam.combined);
		}
		debugObjects.clear();
	}

	/**
	 * Draw a debug line
	 * @param start Start of the line
	 * @param end End of the line
	 * @param lineWidth Width of the line
	 * @param colour Colour of the line
	 */
	public void DrawLine(Vector2 start, Vector2 end, int lineWidth, Color colour) {
		debugObjects.add(new DebugLine(start, end, lineWidth, colour));
	}

	/**
	 * Draw a debug circle (outline)
	 * @param position Centre of the circle
	 * @param radius Radius of the circle
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
	}

	/**
	 * Draw a debug rectangle (outline)
	 * @param bottomLeft Bottom left point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawRect(Vector2 bottomLeft, Vector2 dimensions, int lineWidth, Color colour) {
		debugObjects.add(new DebugRect(bottomLeft, dimensions, lineWidth, colour));
	}

	/**
	 * Updates the position of the camera to have the truck centre
	 */
	public void updateCamera() {
		gamecam.position.lerp(new Vector3(player.getX(),player.getY(),gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
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
	public void hide() {}

	@Override
	public void dispose() {
		Kroy.mainGameScreen = null;
	}

	/**
	 * @param s
	 */
	public void setGameState(State s){
	    state = s;
	}

	/**
	 * @param index
	 * @return
	 */
	public GameObject getGameObject(int index) {
		if (index <= (gameObjects.size()-1)) {
			return gameObjects.get(index);
		}else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public List<GameObject> getGameObjects(){
		return gameObjects;
	}

	/**
	 * Checks the pause buttons
	 */
	private void clickCheck() {
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

	/**
	 * Add one fortress to the count
	 */
	public void AddFortress() {
		fortressesCount++;
	}

	/**
	 * Remove one fortress to the count
	 */
	public void RemoveFortress() {
		fortressesCount--;
	}

	/**
	 * How many fortresses are left?
	 * @return Number of fortresses remaining
	 */
	public int fortressesLeft() {
		return fortressesCount;
	}

	/**
	 * Switch to the game over screen
	 * @param won Did the player reach the win state?
	 */
	public void gameOver(boolean won) {
		game.setScreen(new GameOverScreen(game, truckNum, won));
	}

	/**
	 * Respawns the player at the spawn position and updates the HUD
	 */
	public void respawn() {
		hud.updateLives();
		player = new FireTruck(spawnPosition.cpy(),truckStats[truckNum]);
		gameObjects.add(player);
	}
	
	public HUD getHud(){
		return hud;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}
}
