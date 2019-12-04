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
  
 public class MenuScreen implements Screen{
  
  private Kroy game; 
  private OrthographicCamera gamecam;	//m
  private Viewport gameport; 	//m
  private Texture playBTN;
  private Texture playBTN_ACTIVE;
  private Texture exitBTN;
  private Texture exitBTN_ACTIVE;
  private Texture minigameBTN;
  private Texture minigameBTN_ACTIVE;
  private Texture background;
  private Stage stage;
  //private Texture background;

  //coordinates for Play and Exit buttons 
  private int BTN_WIDTH = 250;
  private int BTN_HEIGHT = 50;
  private int x_axis_centered = (Kroy.width/2) - (BTN_WIDTH/2);
  private int playBTN_y = (Kroy.height/2);
  private int exitBTN_y = (Kroy.height/2)-150;
  private int minigameBTN_y = (Kroy.height/2)-75;
  
  Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
  int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
  int yHotSpot = 0;
  
  
  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitBTN = new Texture("exit.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitBTN_ACTIVE = new Texture("exitActive.png");
	  playBTN = new Texture("newgame.png");
	  playBTN_ACTIVE = new Texture("newActive.png");
	  minigameBTN = new Texture("minigame.png");
	  minigameBTN_ACTIVE = new Texture("minigameActive.png");
	  background = new Texture ("fireforce.png");
	  gamecam = new OrthographicCamera();    //m
	  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
	  stage = new Stage(gameport);
  }
  
  
  @Override public void show() {}
  
  
  
  @Override public void render(float delta) { 
	  
	  stage.act();	//allows the stage to interact with user input
	  
	  game.batch.setProjectionMatrix(gamecam.combined);
	  game.batch.begin();
	  
	  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
	  game.batch.draw(background, 0, 0);
	 
	  game.batch.draw(minigameBTN, x_axis_centered, minigameBTN_y, BTN_WIDTH, BTN_HEIGHT);


	  //for play button: checks if the position of the cursor is inside the coordinates of the button
	  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > playBTN_y ) && (Kroy.height - Gdx.input.getY() < (playBTN_y + BTN_HEIGHT)) ) ){
		  game.batch.draw(playBTN_ACTIVE, x_axis_centered, playBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			  this.dispose();
			  game.batch.end();
			  game.setScreen(new GameScreen(game));
			  return;
		  }
	  } else {
		  game.batch.draw(playBTN, x_axis_centered, playBTN_y, BTN_WIDTH, BTN_HEIGHT);
	  }
	  
	//for exit button
	  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > exitBTN_y ) && (Kroy.height - Gdx.input.getY() < (exitBTN_y + BTN_HEIGHT)) ) ){
		  game.batch.draw(exitBTN_ACTIVE, x_axis_centered, exitBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			  Gdx.app.exit();
		  }
	  } else {
		  game.batch.draw(exitBTN, x_axis_centered, exitBTN_y, BTN_WIDTH, BTN_HEIGHT);
	  }
		
	  //for minigame button
	  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > minigameBTN_y ) && (Kroy.height - Gdx.input.getY() < (minigameBTN_y + BTN_HEIGHT)) ) ){
		  game.batch.draw(minigameBTN_ACTIVE, x_axis_centered, minigameBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			  //what shall we put?
		  }
	  } else {
		  game.batch.draw(minigameBTN, x_axis_centered, minigameBTN_y, BTN_WIDTH, BTN_HEIGHT);
	  }
	  game.batch.end();
	  
  	}
  
  
  
  
  @Override public void resize(int width, int height) { 
	  gameport.update(width, height);
  }
  
  @Override public void pause() { // TODO Auto-generated method stub
  
  }
  
  @Override public void resume() { // TODO Auto-generated method stub
  
  }
  
  @Override public void hide() { // TODO Auto-generated method stub
  
  }
  
  @Override public void dispose() { // TODO Auto-generated method stub
//	  playBTN.dispose();
//	  exitBTN.dispose();
//	  background.dispose();
//	  pm.dispose();
  }
  
  }
 