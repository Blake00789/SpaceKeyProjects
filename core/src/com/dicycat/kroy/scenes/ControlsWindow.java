// ADD_CONTROL_SCREEN_1 - START OF MODIFICATION - NP Studios - Jordan Spooner ---------------

package com.dicycat.kroy.scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.MenuScreen;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ControlsWindow {
	
	public Stage stage;
	public Table table = new Table();
	private SpriteBatch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);
	private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	
	private TextButton back = new TextButton("BACK", skin);
	
	// input keys for the game as images
	private Texture minus = new Texture("-.png");
	private Texture plus = new Texture("+.png");
	private Texture one = new Texture("1.png");
	private Texture two = new Texture("2.png");
	private Texture three = new Texture("3.png");
	private Texture four = new Texture("4.png");
	private Texture five = new Texture("5.png");
	private Texture six = new Texture("6.png");
	private Texture left = new Texture("left.png");
	private Texture right = new Texture("right.png");
	private Texture up = new Texture("up.png");
	private Texture down = new Texture("down.png");
	private Texture pKey = new Texture("p.png");
	private Texture mKey = new Texture("m.png");
	private Texture esc = new Texture("esc.png");
	private Texture space = new Texture("space.png");
	private Texture tKey = new Texture("t.png");
	
	public ControlsWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		// setting up the screen
		table.setBackground(background);
		updateDraw();
	}
	
	public void clickCheck() {
		// back button, redirects user to main menu
		this.back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
    			return;
			}
		});
	}
	
	// changes visibility of the controls screen, ie false means it can't be seen or interacted with,
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
	
	// sets up the screen
	public void updateDraw() {
		table.pad(3);
		table.row();
		table.setSkin(skin);
		table.add(new Image(plus)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add(""); // these create empty cells and helps to align the table
		table.add("");
		table.add("");
		table.add("");
		table.add("Zoom in", "default-font", "white"); // changing the text to have colour white to be more readable
		table.row();
		table.add(new Image(minus)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Zoom out", "default-font", "white");
		table.row();
		table.add(new Image(one)).prefSize(50,50); // resizing the keyboard key images as theyre too big
		table.add(new Image(two)).prefSize(50,50);
		table.add(new Image(three)).prefSize(50,50);
		table.add(new Image(four)).prefSize(50,50);
		table.add(new Image(five)).prefSize(50,50);
		table.add(new Image(six)).prefSize(50,50);
		table.add("");
		table.add("Select firetruck", "default-font", "white");
		table.row();
		table.add(new Image(up)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Drive up", "default-font", "white");
		table.row();
		table.add(new Image(space)).prefSize(50,20);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("(spacebar) Shoot", "default-font", "white").height(50);
		table.row();
		table.add(new Image(down)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Drive down", "default-font", "white");
		table.row();
		table.add(new Image(left)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Drive left", "default-font", "white");
		table.row();
		table.add(new Image(right)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Drive right", "default-font", "white");
		table.row();
		table.add(new Image(pKey)).prefSize(50,50);
		table.add(new Image(mKey)).prefSize(50,50);
		table.add(new Image(esc)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Pause game", "default-font", "white");
		table.row();
		table.add(new Image(tKey)).prefSize(50,50);
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("Show/Hide minimap", "default-font", "white");
		table.row();
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add("");
		table.add(back).width(Kroy.CentreWidth()-20);
		table.setFillParent(true);
	    stage.addActor(table);
	}
	
}

// ADD_CONTROL_SCREEN_1 - END OF MODIFICATION - NP Studios - Jordan Spooner -----------------
