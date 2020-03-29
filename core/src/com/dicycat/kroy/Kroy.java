package com.dicycat.kroy;

import com.badlogic.gdx.Game;
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
	// GAME_RESIZE - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	public static final int width = 1820;
	public static final int height = 980;
	public static boolean debug = false;
	// GAME_RESIZE - END OF MODIFICATION  - NP STUDIOS
	
	public static GameScreen mainGameScreen;
	public static MenuScreen mainMenuScreen;
	public static MinigameScreen mainMinigameScreen;
	public SpriteBatch batch;
	
	private Integer highScore;
	
	@Override
	public void create () {
		highScore = 0;		 
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
	 * Call to generate a new GameScreen instance which runs a new game. Saveslot is not needed
	 * as this is created a new game and not loading it, therefore set to 0.
	 */
	public void newGame() {
		mainGameScreen = new GameScreen(this, 0, 0);// Initialise new game
		setScreen(mainGameScreen);// Display new game
	}

	// SAVING_1 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Added load game function
	/**
	 * Creates a new game instance and passes the saveslot number the game will need to load values from.  - NP STUDIOS
	 * @param saveSlot the slot to load the game from either 1, 2 or 3
	 */
	public void loadGame(int saveSlot) {
		mainGameScreen = new GameScreen(this, 0, saveSlot);
		setScreen(mainGameScreen);// Display new game
	}
	// SAVING_1 - END OF MODIFICATION  - NP STUDIOS
	
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
	
	/** 
	 * Set the high score
	 * @param highScore The new high score
	 */
	public void setHighScore(Integer highScore) {
		this.highScore = highScore;
	}
	
	/**
	 * Get the current high score
	 * @return highScore
	 */
	public Integer getHighScore() {
		return highScore;
	}
}
