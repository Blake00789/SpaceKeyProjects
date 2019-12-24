package com.dicycat.kroy.screens;
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.scenes.FireTruckSelectionScene;
import com.dicycat.kroy.screens.GameScreen.State;
  
 public class MenuScreen implements Screen{
  
  private Kroy game; 
  private OrthographicCamera gamecam;	//m
  private Viewport gameport; 	//m
  private Texture playBTN;
  private Texture playBTN_ACTIVE;
  private Texture controlsBTN;
  private Texture controlsBTN_ACTIVE;
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
  FireTruckSelectionScene fireTruckSelector = new FireTruckSelectionScene();
  boolean currentlyRunningGame = false;
  
  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  exitBTN = new Texture("exit.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  exitBTN_ACTIVE = new Texture("exitActive.png");
	  controlsBTN = new Texture("controls.png"); 	//in later stages we could also have buttonActive and buttonInactive
	  controlsBTN_ACTIVE = new Texture("controls_active.png");
	  playBTN = new Texture("newgame.png");
	  playBTN_ACTIVE = new Texture("newActive.png");
	  minigameBTN = new Texture("minigame.png");
	  minigameBTN_ACTIVE = new Texture("minigameActive.png");
	  background = new Texture ("fireforce.png");
	  gamecam = new OrthographicCamera();    //m
	  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
	  stage = new Stage(gameport);
	  fireTruckSelector.visibility(false);
	  
  }
  
  public static enum State{
	  MAINMENU,
	  TRUCKSELECT
  }
  
  public static State state = State.MAINMENU;
  
  @Override public void show() {}
  
  
  
  @Override public void render(float delta) { 

		  switch(state) {
		  case MAINMENU:			  
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
					  fireTruckSelector.visibility(true);
					  setGameState(State.TRUCKSELECT);
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
				  
			  break;
		  case TRUCKSELECT:
			  Gdx.input.setInputProcessor(fireTruckSelector.stage);
			  fireTruckSelector.stage.act();
			  fireTruckSelector.stage.draw();
			  clickCheck();
			  break;
			  }
		  }
  
	public void setGameState(State s){
	    MenuScreen.state = s;
	}
  
	public void clickCheck() {
		//Truck 1 Selected
		fireTruckSelector.truckButton1.addListener(new ClickListener() {
			@Override
	    	public void clicked(InputEvent event, float x, float y) {
				startGame(0);
	    	}
	    });
		//Truck 2 Selected
		fireTruckSelector.truckButton2.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		startGame(1);
	    	}
	    });
		//Truck 3 Selected
		fireTruckSelector.truckButton3.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		startGame(2);
	    	}
	    });
		//Truck 4 Selected
		fireTruckSelector.truckButton4.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(3);
				
			}
	    });
	}


  
  
  
  @Override public void resize(int width, int height) {
	  gameport.update(width, height);
  }
  
  public void startGame(int truckNum) {
	 if (!currentlyRunningGame) {	
		 currentlyRunningGame = true;
		 game.setScreen(new GameScreen(game,truckNum));
	  }
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
 