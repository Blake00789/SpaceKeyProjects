package com.dicycat.kroy;

import java.awt.RenderingHints.Key;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Kroy extends ApplicationAdapter {
	public static final float speed = 400;
	
	SpriteBatch batch;
	Texture img;
	float x;
	float y;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		x = 0;
		y = 0;
	}

	@Override
	public void render () {
		float posChange = speed * Gdx.graphics.getDeltaTime(); 
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			y += posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			y -= posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			x += posChange;
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			x -= posChange;
		}
		
		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
