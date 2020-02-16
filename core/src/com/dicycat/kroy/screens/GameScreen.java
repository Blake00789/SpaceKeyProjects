package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.dicycat.kroy.entities.UFO;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.scenes.HUD;
import com.dicycat.kroy.scenes.OptionsWindow;
import com.dicycat.kroy.scenes.PauseWindow;


/**
 * Contains the main game logic
 * 
 * @author Riju De
 * @author lnt20
 *
 */
public class GameScreen implements Screen{  

	public static enum GameScreenState{
		PAUSE,
		RUN,
		RESUME,
		OPTIONS
	}
	
	public Kroy game;
	public GameTextures textures;
	public float gameTimer; //Timer to destroy station
	
	
	public GameScreenState state = GameScreenState.RUN;
	
	public static TiledGameMap gameMap;
	
	private OrthographicCamera gamecam;	//follows along what the port displays
	private Viewport gameport;
	
	private HUD hud; 
	private PauseWindow pauseWindow;
	private OptionsWindow optionsWindow;

	private Float[][] truckStats = {	//Each list is a configuration of a specific truck. {speed, flowRate, capacity, range}
			{400f, 1f, 400f, 300f},		//Speed
			{350f, 1.25f, 400f, 300f},	//Speed + Flow rate
			{300f, 1.5f, 400f, 300f},	//Flow rate
			{300f, 1f, 450f, 400f},  	//Capacity + Range
			{300f, 1f, 500f, 300f},		//Capacity
			{300f, 1f, 400f, 450f},		//Range
		};
	
	
	private int truckNum; // Identifies the truck thats selected in the menu screen
	private FireTruck currentTruck;
	private int lives = 4;
	
	private int fortressesCount;
	private Vector2 spawnPosition;	//Coordinates the player spawns at
	
	private List<GameObject> gameObjects, deadObjects;	//List of active game objects
	private List<GameObject> objectsToRender = new ArrayList<GameObject>(); // List of game objects that have been updated but need rendering
	private List<GameObject> objectsToAdd;
	private List<DebugDraw> debugObjects; //List of debug items

	private float lastPatrol; //time passsed since we last spawned patrols
	private List<Vector2> fortressPositions, fortressSizes; //where our fortresses spawn
	private int patrolUpdateRate; //How many seconds should pass before we respawn patrols;

	private ArrayList<FireTruck> firetrucks=new ArrayList<FireTruck>();

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
		gameTimer = 60 * 5; //Set timer to 5 minutes
		this.truckNum = truckNum;
		lastPatrol = Gdx.graphics.getDeltaTime();
		fortressPositions = new ArrayList<>();
		fortressPositions.add(new Vector2(2903, 3211));
		fortressPositions.add(new Vector2(3200, 5681));
		fortressPositions.add(new Vector2(2050, 1937));
		fortressPositions.add(new Vector2(4350, 900));
		fortressPositions.add(new Vector2(5900, 1000));
		fortressPositions.add(new Vector2(520, 3500));
		fortressSizes = new ArrayList<>();
		fortressSizes.add(new Vector2(256, 218));
		fortressSizes.add(new Vector2(256, 320));
		fortressSizes.add(new Vector2(400, 240));
		fortressSizes.add(new Vector2(400, 240));
		fortressSizes.add(new Vector2(400, 240));
		fortressSizes.add(new Vector2(400, 240));
		
		patrolUpdateRate = 30;
	}
	

	/**
	 * Screen first shown
	 */
	@Override
	public void show() {
		objectsToAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		deadObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();

		// Initialises the FireTrucks
		for (int i = 0; i < 6; i++) {
			firetruckInit(spawnPosition.x + -50 + (((i + 1) % 3) * 50), spawnPosition.y - ((i % 2) * 50), i);
			fortressInit(i);
		}
		gameObjects.add(new FireStation());
		switchTrucks(truckNum);

		gamecam.translate(new Vector2(currentTruck.getX(), currentTruck.getY())); // sets initial Camera position

	}

	/**
	 * Initialises each fortress
	 * 
	 * @param num the fortress number
	 */
	private void fortressInit(int num) {
		gameObjects.add(new Fortress(fortressPositions.get(num), textures.getFortress(num), textures.getDeadFortress(num),
				fortressSizes.get(num)));
	}

	/**
	 * Initialises each truck
	 * 
	 * @param num the truck number
	 */
	private void firetruckInit(float x, float y, int num) {
		firetrucks.add(new FireTruck(new Vector2(x, y), truckStats[num], num));
	}

	/**
	 * Called every frame
	 */
	@Override
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

				updateLoop(); //Update all game objects positions but does not render them as to be able to render everything as quickly as possible

				gameMap.renderRoads(gamecam); // Render the background roads, fields and rivers
				gameMap.renderBuildings(gamecam); // Renders the buildings and the foreground items which are not entities

				game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
				game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
				game.batch.begin(); // Game loop Start

				hud.update(delta);

				renderObjects(); // Renders objects specified in the UpdateLoop() called previously

				game.batch.end();


				hud.stage.draw();
				pauseWindow.stage.draw();

				if (Kroy.debug) {
					DrawDebug(); //Draw all debug items as they have to be drawn outside the batch
				}

				break;
			case PAUSE:
				pauseWindow.stage.draw();
				clickCheck();
				break;
			case RESUME:
				pauseWindow.visibility(false);
				setGameState(GameScreenState.RUN);
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
	private void updateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();
		for (GameObject gObject : gameObjects) {	//Go through every game object
			gObject.update();						//Update the game object
			if (gObject.isRemove()) {				//Check if game object is to be removed
				toRemove.add(gObject);					//Set it to be removed
			}else {
				objectsToRender.add(gObject);
			}
		}

		currentTruck.update();
		if (Gdx.input.isKeyPressed(Keys.PLUS)) {
		}
		

		for (GameObject rObject : toRemove) {	//Remove game objects set for removal
			gameObjects.remove(rObject);
			if (rObject.isDisplayable()) {
				deadObjects.add(rObject);
			}
		}
		for (GameObject aObject : objectsToAdd) {		//Add game objects to be added
			gameObjects.add(aObject);
		}
		objectsToAdd.clear();	// Clears list as not to add new objects twice

		for (GameObject dObject : deadObjects) { // loops through the destroyed but displayed items (such as destroyed bases)
			objectsToRender.add(dObject);
		}
		if (currentTruck.isRemove()) {	//If the player is set for removal, respawn
			updateLives();
		}
		switchTrucks();

		lastPatrol += Gdx.graphics.getDeltaTime();
		if (lastPatrol >= patrolUpdateRate) {
			lastPatrol = 0;

			//we should spawn a patrol near every fortress if it given it's been 10 secs.
			for (Vector2 position: fortressPositions) {

				//have to adjust these by about 100 px each since the values given are the bottom left corner, NOT the center.
				float oldX = position.x + 200;
				float oldY = position.y + 200;
				float randX = (float) (oldX - 400 + Math.random() * 400);
				float randY = (float) (oldY - 400 + Math.random() * 400);

				gameObjects.add(new UFO(new Vector2(randX, randY)));


			}
		}

	}

	/**
	 * Renders the objects in "objectsToRender" then clears the list
	 */
	private void renderObjects() {
		for (GameObject object : objectsToRender) {
			object.render(game.batch);
		}
		for (FireTruck truck : firetrucks) {
			truck.render(game.batch);
		}

		objectsToRender.clear();
	}

	/**
	 * Add a game object next frame
	 * @param gameObject gameObject to be added
	 */
	public void addGameObject(GameObject gameObject) {
		objectsToAdd.add(gameObject);
	}

	/**
	 * Allows external classes to access the player
	 * @return player
	 */
	public FireTruck getPlayer() {
		return currentTruck;
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
		if (Kroy.debug) {
			debugObjects.add(new DebugLine(start, end, lineWidth, colour));
		}
	}

	/**
	 * Draw a debug circle (outline)
	 * @param position Centre of the circle
	 * @param radius Radius of the circle
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawCircle(Vector2 position, float radius, int lineWidth, Color colour) {
		if (Kroy.debug) {
			debugObjects.add(new DebugCircle(position, radius, lineWidth, colour));
		}
	}

	/**
	 * Draw a debug rectangle (outline)
	 * @param bottomLefiretrucks Bottom lefiretrucks point of the rectangle
	 * @param dimensions Dimensions of the rectangle (Width, Length)
	 * @param lineWidth Width of the outline
	 * @param colour Colour of the line
	 */
	public void DrawRect(Vector2 bottomLefiretrucks, Vector2 dimensions, int lineWidth, Color colour) {
		if (Kroy.debug) {
			debugObjects.add(new DebugRect(bottomLefiretrucks, dimensions, lineWidth, colour));
		}
	}

	/**
	 * Updates the position of the camera to have the truck centre
	 * Ensures it never goes out of bounds (512, 400) to (6628, 6043)
	 */ 
	public void updateCamera() {
		float cameraX = Math.max(256+256, Math.min(currentTruck.getX(), 6884-256));
		float cameraY = Math.max(400, Math.min(currentTruck.getY(), 6043));
		gamecam.position.lerp(new Vector3(cameraX, cameraY,gamecam.position.z),0.1f);// sets the new camera position based on the current position of the FireTruck
		gamecam.update();
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
	}

	@Override
	public void pause() {
		setGameState(GameScreenState.PAUSE);
	}

	@Override
	public void resume() {
		setGameState(GameScreenState.RESUME);
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
	public void setGameState(GameScreenState s){
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
	
	public int getLives() {
		return lives;
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
	    		pauseWindow.visibility(false);
	    		dispose();
	    		game.backToMenu();
	    		return;
	    	}
	    });
	}

	/**
	 * Add one fortress to the count
	 */
	public void addFortress() {
		fortressesCount++;
	}

	/**
	 * Remove one fortress to the count
	 */
	public void removeFortress() {
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
	 * switch to another truck if currentTruck dies
	 */
	public void updateLives() {
		if (lives>1) {
			lives -= 1;
			if(firetrucks.get(0).isAlive()) {
				switchTrucks(0);
			}else if(firetrucks.get(1).isAlive()) {
				switchTrucks(1);
			}else if(firetrucks.get(2).isAlive()) {
				switchTrucks(2);
			}else if(firetrucks.get(3).isAlive()) {
				switchTrucks(3);
			}else if(firetrucks.get(4).isAlive()) {
				switchTrucks(4);
			}else if(firetrucks.get(5).isAlive()) {
				switchTrucks(5);
			}
		} else {
			gameOver(false);
		}
	}
	
	private void switchTrucks(int n) {
		changeToTruck(firetrucks.get(n));
	}

	/**
	 * Check for inputs to switch between trucks.
	 */
	private void switchTrucks() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			if(firetrucks.get(0).isAlive())
				changeToTruck(firetrucks.get(0));
			else {
				System.out.println("This truck id dead");
			}
		} 
		else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			if(firetrucks.get(1).isAlive())
				changeToTruck(firetrucks.get(1));
			else {
				System.out.println("This truck id dead");
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			if(firetrucks.get(2).isAlive())
				changeToTruck(firetrucks.get(2));
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			if(firetrucks.get(3).isAlive())
				changeToTruck(firetrucks.get(3));
			else {
				System.out.println("This truck id dead");
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_5)) {
			if(firetrucks.get(4).isAlive())
				changeToTruck(firetrucks.get(4));
			else {
				System.out.println("This truck id dead");
			}
		}
		else if (Gdx.input.isKeyPressed(Keys.NUM_6)) {
			if(firetrucks.get(5).isAlive())
				changeToTruck(firetrucks.get(5));
			else {
				System.out.println("This truck id dead");
			}
		}

	}

	/**
	 * Switches the camera to the specified truck.
	 *
	 * @param t The truck to switch to
	 */
	private void changeToTruck(FireTruck t) {
		currentTruck = t;

	}  


	public HUD getHud(){
		return hud;
	}

	public Vector2 getSpawnPosition() {
		return spawnPosition;
	}
	
}
