package com.rd1127.testgame.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.rd1127.testgame.GameObject;
import com.rd1127.testgame.TestGame;
import com.rd1127.testgame.bullets.Bullet;
import com.rd1127.testgame.entities.FireTruck;

public class GameScreen implements Screen{
	
	TestGame game;
	
	List<GameObject> gameObjects;
	
	public GameScreen(TestGame _game) {
		game = _game;
	}
	
	@Override
	public void show() {					//Screen first shown
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(new FireTruck(new Vector2(100, 100)));	//Player
		gameObjects.add(new GameObject(new Vector2(480, 580), new Texture("ufo.png"), new Vector2(80, 80)));	//UFO
		gameObjects.add(new Bullet(new Vector2(10, 10), new Vector2(1,5), 50, 500));	//Bullet
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
		
		game.batch.end();
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
