package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.debug.DebugCircle;
import com.dicycat.kroy.debug.DebugDraw;
import com.dicycat.kroy.debug.DebugLine;
import com.dicycat.kroy.debug.DebugRect;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.UFO;
import com.dicycat.kroy.scenes.HUD;

public class GameScreen implements Screen{
	
	Kroy game;
	private OrthographicCamera gamecam;	//m 	//follows along what the port displays
	private Viewport gameport; 	//m
	private HUD hud;	//m
	
	FireTruck player; //Reference to the player
	List<GameObject> gameObjects;	//List of active game objects
	List<GameObject> toAdd;
	List<DebugDraw> debugObjects; //List of debug items
	
	public GameScreen(Kroy _game) {
		game = _game;
		gamecam = new OrthographicCamera();    //m
		gameport = new ScreenViewport(gamecam);	//m //Mic:could also use StretchViewPort to make the screen stretch instead of adapt
		hud = new HUD(game.batch);												//or FitPort to make it fit into a specific width/height ratio
	}
	
	@Override
	public void show() {	//Screen first shown
		toAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		debugObjects = new ArrayList<DebugDraw>();
		player = new FireTruck(this, new Vector2(0, 0));
		gameObjects.add(player);	//Player	//Mic:modified from (100, 100) to (0, 0)
		gameObjects.add(new UFO(this, new Vector2(0, 200)));	//UFO	//Mic:modified from (480,580) to (0, 200)
		//gameObjects.add(new Bullet(this, new Vector2(10, 10), new Vector2(1,5), 50, 500));	//Bullet
		
	}

	@Override
	public void render(float delta) {		//Called every frame
		Gdx.gl.glClearColor(.47f, .66f, .29f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		game.batch.setProjectionMatrix(gamecam.combined);	//Mic:only renders the part of the map where the camera is
		game.batch.begin(); // Game loop Start

		UpdateLoop();	//Update all game objects
		
		game.batch.end();
		
		DrawDebug(); //Draw all debug items as they have to be drawn outside the batch
	}
	
	//region Game Logic
	private void UpdateLoop() {
		List<GameObject> toRemove = new ArrayList<GameObject>();;
		for (GameObject gObject : gameObjects) {	//Go through every game object
			gObject.Update();//Update the game object
			CheckCollisions();
			if (gObject.CheckRemove()) {				//Check if game object is to be removed
				toRemove.add(gObject);					//Set it to be removed
			}else {
				game.batch.draw(gObject.getTexture(), gObject.getX(), gObject.getY(), gObject.getOriginX(), gObject.getOriginY(), gObject.getWidth(), gObject.getHeight(), gObject.getXScale(), gObject.getYScale(), gObject.getRotation(), 0, 0, gObject.getTextureWidth(), gObject.getTextureHeight(), false, false);
			}
		}
		for (GameObject rObject : toRemove) {	//Remove game objects set for removal
			gameObjects.remove(rObject);
		}
		for (GameObject aObject : toAdd) {		//Add game objects to be added
			gameObjects.add(aObject);
		}
		toAdd.clear();

	}
	
	public void AddGameObject(GameObject gameObject) {	//Add a game object next frame
		toAdd.add(gameObject);
	}
	
	public FireTruck GetPlayer() {
		return player;
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

	public void CheckCollisions(){

		for (GameObject object : gameObjects) {
			if(object instanceof Bullet){

				Bullet currentBullet = (Bullet) object;

				//Debug draw.
				DrawRect(new Vector2(player.getHitbox().x, player.getHitbox().y), new Vector2(player.getHitbox().width, player.getHitbox().height), 2, Color.GREEN);
				DrawCircle(new Vector2(currentBullet.GetHitbox().x, currentBullet.GetHitbox().y), currentBullet.GetHitbox().radius, 2, Color.RED);

				if(Intersector.overlaps(currentBullet.GetHitbox(),player.getHitbox())){
					System.out.println("Bullet Collision!");
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {			
		gameport.update(width, height);				//m
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		game.batch.dispose();
	}

}
