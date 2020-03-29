package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.MenuScreen;
import com.sun.javafx.binding.IntegerConstant;

import javax.swing.*;

/**
 * Window which is opened when the user clicks the 'load game' button the main menu. Shows the 3 slots available to load
 * from as well as a back button.
 * 
 * @author Lucy Ivatt - NP STUDIOS
 */
public class LoadWindow {

	public Stage stage;
	public Table table = new Table();
	private Batch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);

	// Initializes the 4 buttons needed
	private static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	public static TextButton load1 = new TextButton("Load Slot 1", skin);
	public static TextButton load2 = new TextButton("Load Slot 2", skin);
	public static TextButton load3 = new TextButton("Load Slot 3", skin);
	public static TextButton back = new TextButton("Back", skin);

	/**
	 * @param game
	 */
	public LoadWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		// Draws the table with the 4 buttons
		table.setBackground(background);
		table.row();
		table.add(load1).width(Kroy.CentreWidth());
		table.row();
		table.add(load2).width(Kroy.CentreWidth());
		table.row();
		table.add(load3).width(Kroy.CentreWidth());
		table.row();
		table.add(back).width(Kroy.CentreWidth());
		table.setFillParent(true);
		stage.addActor(table);
	}
}