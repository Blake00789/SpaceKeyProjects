package com.dicycat.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;
import com.dicycat.kroy.screens.MinigameScreen;

/**
 * Main game class
 * 
 * @author Riju De
 * @author Sam Hutchings
 *
 */

public class Kroy extends Game {
	public static final int width = 1080;
	public static final int height = 720;

	public static boolean debug = false;
	
	public static GameScreen mainGameScreen;
	public static MenuScreen mainMenuScreen;
	public static MinigameScreen mainMinigameScreen;
	public static Preferences saveData;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		// HIGHSCORE_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
		// Initializes the save file which the highscore will be accessed from.
		saveData = Gdx.app.getPreferences("Kroy");
		// HIGHSCORE_1 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
		batch = new SpriteBatch();
		mainMenuScreen = new MenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {}
	
	/**
	 * Call to generate a brand new GameScreen which runs a new game
	 * @param truckNum  Selected truck
	 */
	public void newGame(int truckNum) {
		mainGameScreen = new GameScreen(this,truckNum);// Initialise new game
		setScreen(mainGameScreen);// Display new game
	}
	
	public void newMinigame() {
		mainMinigameScreen = new MinigameScreen(this);// Initialise new minigame
		setScreen(mainMinigameScreen);// Display new minigame
	}

	/**
	 * Return back to the menu screen
	 */
	public void backToMenu() {
		mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU; // sets menu screen back to the original state
		mainMenuScreen.setCurrentlyRunningGame(false); //Tells the screen not to block any button pushes which would initialise a new game again
		setScreen(mainMenuScreen); // displays the menu screen
	}
	
	/**
	 * Centre of the screen width
	 * @return centre of the screen width
	 */
	public static int CentreWidth() {
		return width / 3;
	}

	// HIGHSCORE_2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT
	// Updated the setHighscore method to save the value to a 'Preferences' file which will persist between games, i.e
	// will save the highscore even when the game has been closed. Also updated the getHighScore method to access this
	// file.
	/**
	 * Saves the highscore to the games saved data file - updated by NP STUDIOS
	 * @param highScore The new high score
	 */
	public void setHighScore(Integer highScore) {
		if (highScore > saveData.getInteger("highscore", 0)) { // If the new score is bigger than the saved highscore
			saveData.putInteger("highscore", highScore); // replace the saved highscore with the newly achieves score
			saveData.flush(); } // updates the file
	}

	/**
	 * Get the current high score
	 * @return highScore
	 */
	public Integer getHighScore() {
		return saveData.getInteger("highscore", 0); // accesses high-score file and returns integer value
	}
	// HIGHSCORE_2 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT
}
