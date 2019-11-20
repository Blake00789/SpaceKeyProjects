package com.dicycat.kroy.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.entities.UFO;

public class GameScreen implements Screen{
	
	Kroy game;
	
	List<GameObject> gameObjects;
	List<GameObject> toAdd;
	
	public GameScreen(Kroy _game) {
		game = _game;
	}
	
	@Override
	public void show() {	//Screen first shown
		toAdd = new ArrayList<GameObject>();
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new FireTruck(this, new Vector2(100, 100)));	//Player
		gameObjects.add(new UFO(this, new Vector2(480, 580)));	//UFO
		//gameObjects.add(new Bullet(this, new Vector2(10, 10), new Vector2(1,5), 50, 500));	//Bullet
		
	}

	@Override
	public void render(float delta) {		//Called every frame
		Gdx.gl.glClearColor(.47f, .66f, .29f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();

		List<GameObject> toRemove = new ArrayList<GameObject>();;
		for (GameObject gObject : gameObjects) {
			gObject.Update();
			if (gObject.CheckRemove()) {
				toRemove.add(gObject);
			}else {
				game.batch.draw(gObject.GetSprite(), gObject.GetPos().x, gObject.GetPos().y, gObject.GetSize().x, gObject.GetSize().y);
			}
		}
		for (GameObject rObject : toRemove) {
			gameObjects.remove(rObject);
		}
		for (GameObject aObject : toAdd) {
			gameObjects.add(aObject);
		}
		toAdd.clear();
		
		game.batch.end();
	}

	public void AddGameObject(GameObject gameObject) {
		toAdd.add(gameObject);
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
