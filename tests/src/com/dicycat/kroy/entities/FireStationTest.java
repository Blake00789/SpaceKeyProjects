package com.dicycat.kroy.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;

@PrepareForTest( FireStation.class )
@RunWith(GdxTestRunner.class)
public class FireStationTest {
	
	@Mock Texture textureLiving;
	@Mock Texture texturedead;
	@Mock Vector2 spawnPos;
	@Mock Vector2 imSize;

	
	 private FireStation firestation;
	 private GameObject gameObject;
	 private Entity entity;
	 private GameScreen gameScreen;
	 
	@Before
	public void setupMock() {

		firestation = Mockito.mock(FireStation.class);
	    Mockito.mock(Kroy.class);
	    gameObject = Mockito.mock(GameObject.class);
	    PowerMockito.spy(new Kroy());			
	    entity = Mockito.mock(Entity.class);
		gameScreen = Mockito.mock(GameScreen.class);
		gameObject = PowerMockito.mock(GameObject.class);
			
		PowerMockito.mockStatic(Kroy.class);
		PowerMockito.constructor(Kroy.class);
		PowerMockito.mockStatic(GameScreen.class);
		PowerMockito.mockStatic(GameObject.class);
		PowerMockito.mockStatic(Entity.class);
		 	
	 }
	
	@Before
	public void init() {
		firestation = new FireStation();
	}
	
	 
		 
	@Test
	public void location() {		
		PowerMockito.when(gameObject.getCentre()).thenReturn(new Vector2(3600,4100));
		assertTrue(firestation.getCentre().x == 3749.0 );
		assertTrue(firestation.getCentre().y == 4187.5 );
	}
	
	@Test
	public void die() {
		assertFalse(firestation.getTexture() == firestation.getTexturedead());
		firestation.die();
		assertTrue(firestation.getTexture() == firestation.getTexturedead());
	} 
	 
	
	@Test
	public void update() {

		if(entity.playerInRadius()) {			
			gameScreen.getPlayer().replenish();
			assertTrue(gameScreen.getPlayer().getHealthPoints() == 42);
		}		
		
		if(gameScreen.gameTimer < 0 ) {
			assertTrue(firestation.getTexture()== gameScreen.textures.getFireStationDead());
		}
	}

}
