package com.dicycat.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.screens.GameOverScreen;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;

public class Kroy extends Game {
	public static final int width = 1080;
	public static final int height = 720;
	
	
	public static SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
	
	public void gameOverScreenSwitch(int truckNum) {
		 setScreen(new GameOverScreen(this, truckNum));
	}
	
}
