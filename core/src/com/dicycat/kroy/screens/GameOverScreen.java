package com.dicycat.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

public class GameOverScreen implements Screen{
	
	private Kroy game; 
	  private OrthographicCamera gamecam;	//m
	  private Viewport gameport; 	//m
	  
	  private Stage stage;
	  
	  public boolean result = true; //to change based on whether game is won or lost
	  
	  private Texture gameOverIMG= new Texture("gameover.png");
	  private Texture youWonIMG= new Texture("youwon.png");
	  private Texture youLostIMG= new Texture("youlost.png");
	  private Texture playBTN= new Texture("newgame.png");
	  private Texture playBTN_ACTIVE= new Texture("newActive.png");
	  private Texture menuBTN= new Texture("menu.png");
	  private Texture menuBTN_ACTIVE = new Texture("menuActive.png");
	  
	  private Integer score;
	  private Integer highScore; 
	  private Label scoreLabel;
	  private Label scoreNumberLabel;
	  private Label highScoreLabel;
	  private Label highScoreNumberLabel;
	  private Integer scaleScore = 2;
	  private float padScore;
	  private float padTop;
	  private int truckNum;

	  //coordinates for gameoverIMG, Play and Exit buttons 
	  private int gameOverIMG_WIDTH = 400;
	  private int gameOverIMG_HEIGHT= 200;
	  private int gameOverIMG_y = ((Kroy.height/2)+75);
	  private int gameOverIMG_x_axis_centred = (Kroy.width/2) - (gameOverIMG_WIDTH/2);
	  
	  private int resultIMG_WIDTH = 300;
	  private int resultIMG_HEIGHT= 100;
	  private int resultIMG_y = ((Kroy.height/2)-20);
	  private int resultIMG_x_axis_centred = (Kroy.width/2) - (resultIMG_WIDTH/2);
	  
	  private int BTN_WIDTH = 250;
	  private int BTN_HEIGHT = 50;
	  private int x_axis_centered = (Kroy.width/2) - (BTN_WIDTH/2);
	  private int playBTN_y = ((Kroy.height/2)-150);
	  private int minigameBTN_y = (Kroy.height/2)-225;
	  
	  Pixmap pm = new Pixmap(Gdx.files.internal("handHD2.png")); //cursor
	  int xHotSpot = pm.getWidth() / 3;	//where the cursor's aim is 
	  int yHotSpot = 0;
	  
	  
	  public GameOverScreen(Kroy game, int truckNum) { 
		  this.game = game; 
		  gamecam = new OrthographicCamera();    //m
		  gameport = new FitViewport(Kroy.width, Kroy.height, gamecam);
		  stage = new Stage(gameport);
		  this.truckNum = truckNum;
		  
		  Table table = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		  table.setFillParent(true);
		  table.top();
		  
		  score = Kroy.mainGameScreen.getHud().getFinalScore();
		  highScore = game.getHighScore();
		  if (score > highScore) {
			  highScore = score;
			  game.setHighScore(highScore);
		  }
		  
		  scoreLabel = new Label("YOUR SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  scoreNumberLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  highScoreLabel = new Label("HIGH SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  highScoreNumberLabel = new Label(String.format("%05d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		  
		  padScore = (Kroy.width/2)-scoreLabel.getWidth()-10;
		  padTop = (Kroy.height/2);

		  scoreLabel.setFontScale(scaleScore);
		  scoreNumberLabel.setFontScale(scaleScore);
		  highScoreLabel.setFontScale(scaleScore);
		  highScoreNumberLabel.setFontScale(scaleScore);
		  
		  table.add(scoreLabel).padLeft(padScore).padTop(padTop);
		  table.add(scoreNumberLabel).padRight(padScore).padTop(padTop);
		  table.row();
		  table.add(highScoreLabel).padLeft(padScore);
		  table.add(highScoreNumberLabel).padRight(padScore);
		  
		  stage.addActor(table);
	  }
	  
	  @Override 
	  public void show() {}
	  
	  @Override 
	  public void render(float delta) {
		  stage.act();	//allows the stage to interact with user input
		  
		  game.batch.setProjectionMatrix(gamecam.combined);
		  game.batch.begin();
		  
		  Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, xHotSpot, yHotSpot));
		 
		  game.batch.draw(gameOverIMG, gameOverIMG_x_axis_centred, gameOverIMG_y, gameOverIMG_WIDTH, gameOverIMG_HEIGHT);
		  
		  if (result) {
			  game.batch.draw(youWonIMG, resultIMG_x_axis_centred, resultIMG_y, resultIMG_WIDTH, resultIMG_HEIGHT);
		  } else {
			  game.batch.draw(youLostIMG, resultIMG_x_axis_centred, resultIMG_y, resultIMG_WIDTH, resultIMG_HEIGHT);
		  }
		  

		  //for play button: checks if the position of the cursor is inside the coordinates of the button
		  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > playBTN_y ) && (Kroy.height - Gdx.input.getY() < (playBTN_y + BTN_HEIGHT)) ) ){
			  game.batch.draw(playBTN_ACTIVE, x_axis_centered, playBTN_y, BTN_WIDTH, BTN_HEIGHT);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  this.dispose();
				  game.batch.end();
				  game.newGame(truckNum);
				  return;
			  }
		  } else {
			  game.batch.draw(playBTN, x_axis_centered, playBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  }
		  
		
			
		  //for minigame button
		  if(( (Gdx.input.getX() < (x_axis_centered + BTN_WIDTH)) && (Gdx.input.getX() > x_axis_centered) ) && ( (Kroy.height - Gdx.input.getY() > minigameBTN_y ) && (Kroy.height - Gdx.input.getY() < (minigameBTN_y + BTN_HEIGHT)) ) ){
			  game.batch.draw(menuBTN_ACTIVE, x_axis_centered, minigameBTN_y, BTN_WIDTH, BTN_HEIGHT);
			  if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				  dispose();
				  game.backToMenu();
			  }
		  } else {
			  game.batch.draw(menuBTN, x_axis_centered, minigameBTN_y, BTN_WIDTH, BTN_HEIGHT);
		  }
		  game.batch.end();
		  
		  stage.draw();

		  
	  	}
	  
	  @Override 
	  public void resize(int width, int height) { 
		  gameport.update(width, height);
	  }
	  
	  @Override 
	  public void pause() {}
	  
	  @Override 
	  public void resume() {}
	  
	  @Override 
	  public void hide() {}
	  
	  @Override 
	  public void dispose() {}
	  
	}
	 


