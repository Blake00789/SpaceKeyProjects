package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

/**
 * Pause window
 * 
 * @author Michele Imbriani
 *
 */
public class PauseWindow {
	
	public Stage stage;
	public Table table = new Table();
	private Batch sb;
	private NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private NinePatchDrawable background = new NinePatchDrawable(patch);
	
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    public TextButton resume = new TextButton("RESUME", skin);
    public TextButton exit = new TextButton("EXIT", skin);
    public TextButton menu = new TextButton("MENU", skin);
	// SAVING_2 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
	// Added three buttons to the pause menu which the user can click to save the game.
    public TextButton save1 = new TextButton("SAVE: SLOT 1", skin);
	public TextButton save2 = new TextButton("SAVE: SLOT 2", skin);
	public TextButton save3 = new TextButton("SAVE: SLOT 3", skin);
	// SAVING_2 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
    
    
	/**
	 * @param game
	 */
	public PauseWindow(Kroy game) {
		sb = game.batch;
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		table.row();
	    table.add(resume).width(Kroy.CentreWidth());
		table.row();
	    table.add(menu).width(Kroy.CentreWidth());
	    table.row();
		// SAVING_3 - START OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		// Added the 3 new save buttons to the table.
	    table.add(save1).width(Kroy.CentreWidth());
		table.row();
		table.add(save2).width(Kroy.CentreWidth());
		table.row();
		table.add(save3).width(Kroy.CentreWidth());
		// SAVING_3 - END OF MODIFICATION  - NP STUDIOS - LUCY IVATT
		table.row();
		table.add(exit).width(Kroy.CentreWidth());
	    
		table.setFillParent(true);
	    stage.addActor(table);
	}
	
	/**
	 * @param state	Allows the window to be visible or hidden
	 */
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
}
