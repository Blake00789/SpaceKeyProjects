package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;

public class FireTruckSelectionScene {

	public Stage stage;
	public Table table = new Table();
	SpriteBatch sb = Kroy.batch;
	NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("Grey.png"), 3, 3, 3, 3));
	
    Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
//    FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
//    Skin skin = new Skin(fileHandle);
    public TextButton truckButton1 = new TextButton("Speed", skin);
    public TextButton truckButton2 = new TextButton("Damage", skin);
    public TextButton truckButton3 = new TextButton("Capacity", skin);
    public TextButton truckButton4 = new TextButton("Range", skin);
    float sw = Gdx.graphics.getWidth();
    float cw = sw * 0.7f;
    
    
	public FireTruckSelectionScene() {
			    
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.setBackground(background);
		table.add(new Image(new Texture("FireTruck1.png")));
		table.add(new Image(new Texture("FireTruck2.png")));
		table.add(new Image(new Texture("FireTruck3.png")));
		table.add(new Image(new Texture("FireTruck4.png")));
		table.row();
	    table.add(truckButton1).width(cw/3.0f);
	    table.add(truckButton2).width(cw/3.0f);
	    table.add(truckButton3).width(cw/3.0f);
	    table.add(truckButton4).width(cw/3.0f);
	    
		table.setFillParent(true);
	    stage.addActor(table);
	    
	    
	    
	    
	    
	}
	
	public void visibility(boolean state){
		this.table.setVisible(state);
	}

	
	
}