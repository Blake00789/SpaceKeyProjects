package com.rd1127.testgame;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rd1127.testgame.screens.GameScreen;

<<<<<<< HEAD:kroy/xtra/src/com/rd1127/testgame/TestGame.java
public class TestGame extends Game {
	public static final int width = 1080;
	public static final int height = 720;
	
	public SpriteBatch batch;
=======
public class Kroy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int x;
	int y;
>>>>>>> 1f8af5f491754dc5ff812571fb0e3c96ecd9e47c:kroy/core/src/com/dicycat/kroy/Kroy.java
	
	@Override
	public void create () {
		batch = new SpriteBatch();
<<<<<<< HEAD:kroy/xtra/src/com/rd1127/testgame/TestGame.java
		this.setScreen(new GameScreen(this));
=======
		img = new Texture("badlogic.jpg");
		x = 0;
		y = 0;
>>>>>>> 1f8af5f491754dc5ff812571fb0e3c96ecd9e47c:kroy/core/src/com/dicycat/kroy/Kroy.java
	}

	@Override
	public void render () {
<<<<<<< HEAD:kroy/xtra/src/com/rd1127/testgame/TestGame.java
		super.render();
=======
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			y = y + 4;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			y = y - 4;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x = x + 4;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			x = x - 4;
		}
		
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
>>>>>>> 1f8af5f491754dc5ff812571fb0e3c96ecd9e47c:kroy/core/src/com/dicycat/kroy/Kroy.java
	}
	
	@Override
	public void dispose () {
	}
}
