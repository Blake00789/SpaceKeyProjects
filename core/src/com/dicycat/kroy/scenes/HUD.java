package com.dicycat.kroy.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
	
	public Stage stage;
	private Viewport viewport;	//Mic: creating new port so that the HUD stays locked while map can move around independently
	
	
	private Integer trucks;
	private Integer timeCount;	//Mic: change to float maybe
	private Integer score;
	
	Label scoreLabel;
	Label timeLabel;
	Label trucksLabel;
	Label timeCountLabel;
	Label scoreCountLabel;
	Label trucksCountLabel;	//Mic: we could put mini images of the trucks instead of using an int for the lives
	
	
	public HUD(SpriteBatch sb) {
		timeCount = 0;
		score = 0;
		trucks = 4;
		
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Mic: Where we are going to put the HUD elements 
		
		Table table = new Table();	//Mic: this allows to put widgets in the scene in a clean and ordered way
		table.top();	//Mic: puts widgets from the top instead of from the centre
		table.setFillParent(true);	//Mic: makes the table the same size of the stage
		
		timeCountLabel = new Label(String.format("%05d", timeCount), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksLabel = new Label("TRUCKS:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		trucksCountLabel = new Label(String.format("%01d", trucks), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(timeLabel).expandX().padBottom(10);
		table.add(timeCountLabel).expandX().padTop(10);
		table.add(scoreLabel).expandX().padTop(10);			//Mic: expandX so that all elements take up the same amount of space
		table.add(scoreCountLabel).expandX().padTop(10);
		table.add(trucksLabel).expandX().padTop(10);
		table.add(trucksCountLabel).expandX().padTop(10);
		
		stage.addActor(table);
		
	}
	
}

