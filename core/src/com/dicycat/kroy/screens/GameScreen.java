package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.UFO;
import com.dicycat.scenes.HUD;

public class GameScreen implements Screen{
	
	Kroy game;
	private OrthographicCamera gamecam;	//m 	//follows along what the port displays
	private Viewport gameport; 	//m
	private HUD hud;	//m
	
	List<GameObject> gameObjects;
	List<GameObject> toAdd;
	
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
		gameObjects.add(new FireTruck(this, new Vector2(100, 50)));	//Player	//Mic:modified from (100, 100) to (0, 0)
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

		List<GameObject> toRemove = new ArrayList<GameObject>();;
		for (GameObject gObject : gameObjects) {
			gObject.Update();
			if (gObject.CheckRemove()) {
				toRemove.add(gObject);
			}else {
				game.batch.draw(gObject.getTexture(), gObject.getX(), gObject.getY(), gObject.getOriginX(), gObject.getOriginY(), gObject.getWidth(), gObject.getHeight(), gObject.getXScale(), gObject.getYScale(), gObject.getRotation(), 0, 0, gObject.getTextureWidth(), gObject.getTextureHeight(), false, false);
			}
		}
		for (GameObject rObject : toRemove) {
			gameObjects.remove(rObject);
		}
		for (GameObject aObject : toAdd) {
			gameObjects.add(aObject);
		}
		toAdd.clear();
		
		game.batch.end(); // Game loop end
	}

	public void addGameObject(GameObject gameObject) {
		toAdd.add(gameObject);
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
	public void dispose() {					//Get rid of screen
		game.batch.dispose();
	}

}
