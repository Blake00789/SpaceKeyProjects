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

/**
 * Pause window
 * 
 * @author Michele Imbriani
 *
 */
public class LoadWindow {

	private Kroy game;
	public Stage stage;
	public Table table = new Table();
	private Batch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);

    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public TextButton load1 = new TextButton("Load Slot 1", skin);
    public TextButton load2 = new TextButton("Load Slot 2", skin);
    public TextButton load3 = new TextButton("Load Slot 3", skin);
    public TextButton back = new TextButton("Back", skin);

	/**
	 * @param game
	 */
	public LoadWindow(Kroy game) {
		this.game = game;
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

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
	
	/**
	 * @param state	Allows the window to be visible or hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}

	public void clickCheck() {
		//back button
		this.back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				visibility(false);
				Kroy.mainMenuScreen.state = MenuScreen.MenuScreenState.MAINMENU;
				}
		});

		//music page
		//playStopMusic button
		this.load1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				 game.loadGame(1);
			}
		});
		//playStopMusic button
		this.load2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.loadGame(2);
			}
		});
		//volumeDown button
		this.load3.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.loadGame(3);
			}
		});
		//volumeUp button
		this.back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
			}
		});

	}
}
