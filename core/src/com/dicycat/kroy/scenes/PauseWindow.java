package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import com.dicycat.kroy.screens.GameScreen.State;


public class PauseWindow {
	
	public Stage stage;
	public Table table = new Table();
	SpriteBatch sb = Kroy.batch;
	NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	NinePatchDrawable background = new NinePatchDrawable(patch);
	
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//    FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
//    Skin skin = new Skin(fileHandle);
    public TextButton resume = new TextButton("RESUME", skin);
    public TextButton exit = new TextButton("EXIT", skin);
    public TextButton menu = new TextButton("MENU", skin);
    float sw = Gdx.graphics.getWidth();
    float cw = sw * 0.7f;
    
    
	public PauseWindow() {
			    
		Viewport viewport;	
		viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		table.row();
	    table.add(resume).width(cw/3.0f);
		table.row();
	    table.add(menu).width(cw/3.0f);
	    table.row();
	    table.add(exit).width(cw/3.0f);
	    
		table.setFillParent(true);
	    stage.addActor(table);
	    
	    
	    
	    
	    
	}
	
	public void visibility(boolean state){
		this.table.setVisible(state);
	}
	
	
}