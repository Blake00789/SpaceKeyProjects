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
import com.dicycat.kroy.Kroy;

import static java.lang.Math.abs;

/**
 * HUD window
 * 
 * @author Michele Imbriani
 *
 */
public class HUD {
	public Stage stage;
	private Viewport viewport;	//creating new port so that the HUD stays locked while map can move around independently

	// HUD_UPDATES_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Changed HUD to track fortresses, condensed timer into only 1 variable, changed Objects to primitive types.
	private int fortresses = 6;
	private float countdown;	//change to float maybe
	private static int score = 100000;
	// HUD_UPDATES_1 - END OF MODIFICATION  - NP STUDIOS

	private Label scoreLabel;
	private Label timeLabel;
	private Label fortressLabel;
	private Label countdownLabel;
	private Label scoreCountLabel;
	private Label fortressCountLabel;	//we could put mini images of the trucks instead of using an int for the lives
	
	
	/**
	 * @param sb	SpriteBatch for the game
	 * @param timeLimit the amount of time before the firestation should be destroyed
	 */
	// HUD_UPDATES_2 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Added timeLimit to the constructor paramters so it can be passed from the main game screen and set countdown
	// equal to this value.
	public HUD(SpriteBatch sb, float timeLimit) {
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);	//Where we are going to put the HUD elements
		countdown = timeLimit;
	// HUD_UPDATES_2 - END OF MODIFICATION  - NP STUDIOS
		
		Table tableHUD = new Table();	//this allows to put widgets in the scene in a clean and ordered way
		tableHUD.top();	// puts widgets from the top instead of from the centre
		tableHUD.setFillParent(true);	//makes the table the same size of the stage
		
		countdownLabel = new Label(String.format("%.0f", countdown), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME LEFT UNTIL FIRE STATION DESTROYED:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreCountLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label("SCORE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		fortressLabel = new Label("FORTRESSES REMAINING:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		fortressCountLabel = new Label(String.format("%01d", fortresses), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		tableHUD.add(timeLabel).expandX().padTop(10);
		tableHUD.add(countdownLabel).expandX().padTop(10);
		tableHUD.add(scoreLabel).expandX().padTop(10);			// expandX so that all elements take up the same amount of space
		tableHUD.add(scoreCountLabel).expandX().padTop(10);
		tableHUD.add(fortressLabel).expandX().padTop(10);
		tableHUD.add(fortressCountLabel).expandX().padTop(10);
		
		stage.addActor(tableHUD);
		
	}
	
	/**
	 * Using delta time allows to operate with the real-world time (seconds)
	 * rather than the in-game time (which is computed using frames)
	 * 
	 * @param dt	Delta Time 
	 */
	public void update(float dt) {
	// HUD_UPDATES_3 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Removed the need for a second variable and condensed code.
		if (countdown > 0) { countdown -= dt; }
		countdownLabel.setText(String.format("%.0f", abs(countdown)));
		scoreCountLabel.setText(String.format("%06d", score));
		fortressCountLabel.setText(String.format("%01d", Kroy.mainGameScreen.getFortressesCount())); }

	// Added a setter for the timer to be used when loading a saved game.
	public void setTimer(float time) {
		this.countdown = time;
	}
	// HUD_UPDATES_3 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT

	public int getFinalScore() {
		return score;
	}

	public static void setScore(Integer x){
		score = x;
	}

	public int getScore(){
		return score;
	}

	/**
	 * @param x		Points to be added to the score
	 */
	public void updateScore(Integer x){
		score += x;
	}
	
}

