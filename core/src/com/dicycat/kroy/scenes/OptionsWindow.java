package com.dicycat.kroy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.dicycat.kroy.screens.GameScreen.State;

public class OptionsWindow {
	
	public static Stage stage;
	public static Table table = new Table();
	SpriteBatch sb = Kroy.batch;
	private static NinePatch patch = new NinePatch(new Texture("loool.jpg"), 3, 3, 3, 3);
	private static NinePatchDrawable background = new NinePatchDrawable(patch);
	
    private static Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    //options page 1
    private static TextButton music = new TextButton("MUSIC", skin);
    private static TextButton debug = new TextButton("DEBUG", skin);
    private static TextButton back = new TextButton("BACK", skin);
    
    //music options page
    private static TextButton stopMusic = new TextButton("STOP MUSIC", skin);
    private static TextButton playMusic = new TextButton("PLAY MUSIC", skin);
    private static TextButton volumeDown = new TextButton("MUTE VOLUME", skin);
    private static TextButton volumeUp = new TextButton("UNMUTE VOLUME", skin);
    private static TextButton backFromMusic = new TextButton("BACK", skin);
    
    //debug options page
    private static TextButton showDebug = new TextButton("SHOW DEBUG", skin);
    private static TextButton hideDebug = new TextButton("HIDE DEBUG", skin);
    private static TextButton backFromDebug = new TextButton("BACK", skin);
    
    public static State state = State.PAGE1;
    
    public static enum State{
		PAGE1,
		MUSIC,
		DEBUG,
	}
    
    private static float sw = Gdx.graphics.getWidth();
    private static float cw = sw * 0.7f;
    
    
	public OptionsWindow() {
		
		Viewport viewport = new ScreenViewport(new OrthographicCamera());
		stage = new Stage(viewport, sb);

		table.reset();
		table.setBackground(background);
		updateDraw();
	}
	
	
	public void visibility(boolean state){
		this.table.setVisible(state);
	}

	
	
	public void clickCheck(final boolean fromMenu) {		//takes screen as attribute cuz the 'back' button behaves differently based on whether the optionwindow was called from menu or gamescreen
	//page 1
		//resume button
		this.music.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		table.reset();
	    		state = State.MUSIC;
	    		updateDraw();
	    	}
	    });

		//debug button
		this.debug.addListener(new ClickListener() {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y) {
	    		table.reset();
	    		state = State.DEBUG;
	    		updateDraw();
	    	}
	    });
		//back button
			this.back.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		visibility(false);
		    		if (fromMenu == false) {
		    			GameScreen.setGameState(GameScreen.State.PAUSE);
		    			return;
		    		} else if (fromMenu) {
		    			MenuScreen.state = MenuScreen.State.MAINMENU;
		    			return;
		    		}
		    	}
		    });
			
	//music page
			//playStopMusic button
			this.playMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		if (MenuScreen.music.isPlaying() == false) {
		    			MenuScreen.music.play();  
		    		}
		    		}
		    });
			//playStopMusic button
			this.stopMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		if (MenuScreen.music.isPlaying()){
		    			MenuScreen.music.stop();
		    		} 
		    		}
		    });
			//volumeDown button
			this.volumeDown.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    			MenuScreen.musicVolume = 0;
		    			MenuScreen.music.setVolume((float)MenuScreen.musicVolume);

		    	}
		    });
			//volumeUp button
			this.volumeUp.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    			MenuScreen.musicVolume = 1;
		    			MenuScreen.music.setVolume((float)MenuScreen.musicVolume);
		    	}
		    });
			//backFromMusic button
			this.backFromMusic.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		table.reset();
		    		state = State.PAGE1;
		    		updateDraw();
		    	}
		    });
			
	//debug page
			//showDebug button
			this.showDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		GameScreen.showDebug = true;
		    		}
		    });
			//hideDebug button
			this.hideDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		GameScreen.showDebug = false;
		    		}
		    });
			//backFromDebug button
			this.backFromDebug.addListener(new ClickListener() {
		    	@Override
		    	public void clicked(InputEvent event, float x, float y) {
		    		table.reset();
		    		state = State.PAGE1;
		    		updateDraw();
		    	}
		    });
	}

	public static void updateDraw() {
		switch(state) {
		case PAGE1:
			table.row();
		    table.add(music).width(cw/3.0f);
			table.row();
		    table.add(debug).width(cw/3.0f);
		    table.row();
		    table.add(back).width(cw/3.0f);
		    break;
		case MUSIC:
			table.row();
		    table.add(playMusic).width(cw/3.0f);
		    table.row();
		    table.add(stopMusic).width(cw/3.0f);
		    table.row();
		    table.add(volumeDown).width(cw/3.0f);
		    table.row();
		    table.add(volumeUp).width(cw/3.0f);
		    table.row();
		    table.add(backFromMusic).width(cw/3.0f);
		    break;
		case DEBUG:
			table.row();
		    table.add(showDebug).width(cw/3.0f);
		    table.row();
		    table.add(hideDebug).width(cw/3.0f);
		    table.row();
		    table.add(backFromDebug).width(cw/3.0f);
		    break;
		}
		
		table.setFillParent(true);
	    stage.addActor(table);
	}

}
