package com.dicycat.kroy.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import org.junit.Test;

public class FireStationTest {
	
	private FireStation firestation;
	
	@Before
	public void init() {
		firestation=new FireStation();
	}
	
	@Test
	public void location() {
		assertTrue(firestation.getCentre().x == 3750.0 );
		assertTrue(firestation.getCentre().y == 4187.5 );
	}

	
	@Test
	public void update() {
		if(firestation.playerInRadius()) {
			float health=Kroy.mainGameScreen.getPlayer().healthPoints;
			Kroy.mainGameScreen.getPlayer().replenish();
			assertTrue((Kroy.mainGameScreen.getPlayer().healthPoints - health) == 2);
		}
			
		if(Kroy.mainGameScreen.gameTimer < 0 ) {
			assertTrue(firestation.getTexture()== Kroy.mainGameScreen.textures.getFireStationDead());
		}
	}

}
