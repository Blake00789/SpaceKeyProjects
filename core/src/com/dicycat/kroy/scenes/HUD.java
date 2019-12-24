package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameOverScreen;
import com.dicycat.kroy.screens.GameScreen;

public class HUD {
	
	private Kroy game; 
	public Stage stage;
	private Viewport viewport;	//creating new port so that the HUD stays locked while map can move around independently
	
	
	private static Integer trucks = 4;
	private static Integer worldTimer = 0;	//change to float maybe
	private static Integer score = 0;
	private static float timeCount = 0;
	
	private static Integer scoreMultiplier = 3;
	
	Label scoreLabel;
	Label timeLabel;
	Label trucksLabel;
	Label worldTimerLabel;
	Label scoreCountLabel;
	Label trucksCountLabel;	
	Label spaceLabel;
	
	
	public HUD(SpriteBatch sb, Kroy game) {
		this.game = game; 
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements 
		
		Table tableHUD = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage
		
		worldTimerLabel = new Label(String.format("%05d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksLabel = new Label("TRUCKS:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksCountLabel = new Label(String.format("%01d", trucks), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		spaceLabel = new Label("                         ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		

		tableHUD.add(timeLabel).expandX().padTop(10);
		tableHUD.add(worldTimerLabel).expandX().padTop(10);
		tableHUD.add(spaceLabel).expandX().padTop(10);
		tableHUD.add(scoreLabel).expandX().padTop(10);			// expandX so that all elements take up the same amount of space
		tableHUD.add(scoreCountLabel).expandX().padTop(10);
		tableHUD.add(spaceLabel).expandX().padTop(10);
		tableHUD.add(trucksLabel).expandX().padTop(10);
		tableHUD.add(trucksCountLabel).expandX().padTop(10);
		
		stage.addActor(tableHUD);
		
	}
	
	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			worldTimer++;
			worldTimerLabel.setText(String.format("%05d", worldTimer));
			timeCount =0;
			score += scoreMultiplier;
			scoreCountLabel.setText(String.format("%06d", score));
		}
	}
	
	public static void updateLives() {
		if (trucks>0) {
			trucks -= 1;
		} else {
			gameOver();
		}
		}
	
	public static void gameOver() {
		Kroy.batch.dispose();
		
	}
	
	public static Integer getFinalScore() {
		return score;
	}
	
}

