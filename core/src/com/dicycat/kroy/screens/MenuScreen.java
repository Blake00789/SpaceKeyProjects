package com.dicycat.kroy.screens;
  
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dicycat.kroy.Kroy;
import com.dicycat.scenes.HUD;
  
 public class MenuScreen implements Screen{
  
  private Kroy game; 
  private Texture texture;
  private OrthographicCamera gamecam;	//m
  private Viewport gameport; 	//m
  private HUD hud;
  
  
  public MenuScreen(Kroy game) { 
	  this.game = game; 
	  texture = new Texture("fatcat.png"); 
	  gamecam = new OrthographicCamera();    //m
	  gameport = new ScreenViewport(gamecam);
	  hud = new HUD(game.batch);
  }
  
  
  @Override public void show() { // TODO Auto-generated method stub
  
  }
  
  @Override public void render(float delta) { 
	  Gdx.gl.glClearColor(1, 1, 1, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  
	  game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
	  hud.stage.draw();
	  
	  game.batch.setProjectionMatrix(gamecam.combined);
	  game.batch.begin();
	  game.batch.draw(texture,  0,  0);
	  game.batch.end();
  }
  
  @Override public void resize(int width, int height) { 
	  gameport.update(width, height);
  }
  
  @Override public void pause() { // TODO Auto-generated method stub
  
  }
  
  @Override public void resume() { // TODO Auto-generated method stub
  
  }
  
  @Override public void hide() { // TODO Auto-generated method stub
  
  }
  
  @Override public void dispose() { // TODO Auto-generated method stub
  
  }
  
  }
 