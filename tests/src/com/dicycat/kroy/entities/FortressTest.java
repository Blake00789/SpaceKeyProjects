package com.dicycat.kroy.entities;

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
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.misc.StatBar;
import com.dicycat.kroy.screens.GameScreen;

@PrepareForTest( Fortress.class )
@RunWith(GdxTestRunner.class)
public class FortressTest {	
	
	@Mock BulletDispenser dispenser;
	@Mock StatBar healthBar;
	
	@Mock Vector2 spawnPos = new Vector2(new Vector2(2903, 3211));
	@Mock Texture fortressTexture = new Texture("cliffords tower.png");
	@Mock Texture deadTexture = new Texture("cliffords tower dead.png");
	@Mock Vector2 size = new Vector2(new Vector2(256, 218));
	
	@Mock
	private Fortress fortress;
	private GameObject gameObject;
	
	@Before
	public void setupMock() {	
	 	Mockito.mock(Entity.class);		 			 	
	 	Mockito.mock(FireStation.class);
	 	Mockito.mock(Kroy.class);
	 	Mockito.mock(BulletDispenser.class);
	 	Mockito.mock(GameScreen.class);
	 	
	 	spawnPos = Mockito.mock(Vector2.class);
	 	fortressTexture = Mockito.mock(Texture.class);
	 	deadTexture = Mockito.mock(Texture.class);
	 	size = Mockito.mock(Vector2.class);
	 	gameObject = PowerMockito.mock(GameObject.class);
	 	fortress = Mockito.mock(Fortress.class);
	 			 			
	 	PowerMockito.mockStatic(Entity.class);
	 	PowerMockito.mockStatic(GameObject.class);
	 	PowerMockito.mockStatic(Kroy.class);
	 	PowerMockito.mockStatic(GameScreen.class);
	 	PowerMockito.mockStatic(BulletDispenser.class);
	 	PowerMockito.mock(Entity.class);
	 	PowerMockito.mock(BulletDispenser.class);
		 
	 }
	 
	@Before
	public void init() {
		fortress=new Fortress();
	}
	 
	/**
	 * Check the Fortress was built at the right place
	 */	 
	@Test 
	public void location() {
		PowerMockito.when(gameObject.getCentre()).thenReturn(new Vector2(3031,3320));
		assertTrue(fortress.getCentre().x==3031);
		assertTrue(fortress.getCentre().y==3320);
		
	}
	
	/**
	 * HealthPoints should decrease when user call the Damage function
	 */	
	@Test
    public void takeDamage() {		
		
		assertTrue(fortress.getHealthPoints() == 500 );
        fortress.Damage( 5);
        assertTrue(fortress.getHealthPoints() == 495 );
    }
	
	/**
	 * Fortress should become disable when it dies 
	 */
	@Test
	public void deathCheck() {
		fortress.death();
		assertTrue(fortress.isDisplayable()==true);
	}

}


