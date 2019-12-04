package com.dicycat.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen.State;

public class PauseScreen implements Screen {
	  private Kroy game; 
	  private OrthographicCamera gamecam;	//m
	  private Viewport gameport; 	//m
	  private Texture resumeBTN;
	  private Texture menuBTN;
	  private Texture newGameBTN;
	  private Texture resumeBTN_ACTIVE;
	  private Texture menuBTN_ACTIVE;
	  private Texture newGameBTN_ACTIVE;
	  private Texture background;
	  private Stage stage;

	  //coordinates for Play and Exit buttons 
	  private int BTN_WIDTH = 250;
	  private int BTN_HEIGHT = 50;
	  private int x_axis_centered = (Kroy.width/2) - (BTN_WIDTH/2);
	  private int resumeBTN_y = (Kroy.height/2);
	  private int newGameBTN_y = (Kroy.height/2)+75;
	  private int menuBTN_y = (Kroy.height/2)-75;
	  
	  Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
	  int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
	  int yHotSpot = 0;
	  
	  
	  public PauseScreen(Kroy game) { 
		  this.game = game; 
		  menuBTN = new Texture("menu.png"); 	
		  resumeBTN = new Texture("button_resume.png");
		  newGameBTN = new Texture("newgame.png");
		  menuBTN_ACTIVE = new Texture("menuActive.png"); 	
		  resumeBTN_ACTIVE = new Texture("resumeActive.png");
		  newGameBTN_ACTIVE = new Texture("newActive.png");
		  background = new Texture ("loool.jpg");
		  gamecam = new OrthographicCamera();    //m
		  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
		  stage = new Stage(gameport);
	  }
	  
	  
	  @Override public void show() {}
	  
	  
	  
	  @Override public void render(float delta) { 
		  Gdx.gl.glClearColor(1, 1, 2, 1);
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  
		  stage.act();	//allows the stage to interact with user input
		  
		  game.batch.setProjectionMatrix(gamecam.combined);
		  game.batch.begin();
		  
		  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
		  game.batch.draw(background, 0, 0);
		  
		 

		  //for resume
		  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > resumeBTN_y ) && (Kroy.height - Gdx.input.getY() < (resumeBTN_y + BTN_HEIGHT)) ) ){
			  game.batch.draw(resumeBTN_ACTIVE, x_axis_centered, resumeBTN_y, BTN_WIDTH, BTN_HEIGHT);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  this.dispose();
				  game.batch.end();
				  GameScreen.state = State.RESUME;
				  return;
			  }
		  } else {
			  game.batch.draw(resumeBTN, x_axis_centered, resumeBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  }
		  
		//for menu button
		  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > menuBTN_y ) && (Kroy.height - Gdx.input.getY() < (menuBTN_y + BTN_HEIGHT)) ) ){
			  game.batch.draw(menuBTN_ACTIVE, x_axis_centered, menuBTN_y, BTN_WIDTH, BTN_HEIGHT);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  this.dispose();
				  game.batch.end();
				  game.setScreen(new MenuScreen(game));
				  return;
			  }
		  } else {
			  game.batch.draw(menuBTN, x_axis_centered, menuBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  }
		  
		  //for newgame
		  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > newGameBTN_y ) && (Kroy.height - Gdx.input.getY() < (newGameBTN_y + BTN_HEIGHT)) ) ){
			  game.batch.draw(newGameBTN_ACTIVE, x_axis_centered, newGameBTN_y, BTN_WIDTH, BTN_HEIGHT);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  this.dispose();
				  game.batch.end();
				  game.setScreen(new GameScreen(game));
				  GameScreen.state = State.RUN;
				  return;
			  }
		  } else {
			  game.batch.draw(newGameBTN, x_axis_centered, newGameBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  }
		  
		  game.batch.end();
	  	}
	  
	  
	  
	  
	  @Override public void resize(int width, int height) { 
		  gameport.update(width, height);
	  }
	  
	  @Override public void pause() { // TODO Auto-generated method stub
	  
	  }
	  
	  @Override public void resume() { // TODO Auto-generated method stub
		  GameScreen.state = State.RESUME;
	  }
	  
	  @Override public void hide() { // TODO Auto-generated method stub
	  
	  }
	  
	  @Override public void dispose() { // TODO Auto-generated method stub
//		  resumeBTN.dispose();
//		  menuBTN.dispose();
//		  background.dispose();
//		  pm.dispose();
//		  game.dispose();
//		  resumeBTN.dispose();
//		  menuBTN.dispose();
//		  newGameBTN.dispose();
//		  resumeBTN_ACTIVE.dispose();
//		  menuBTN_ACTIVE.dispose();
//		  newGameBTN_ACTIVE.dispose();
//		  background.dispose();
//		  stage.dispose();
//		  game.batch.dispose();
	  }
}
