package com.dicycat.kroy.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.GdxTestRunner;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.screens.GameScreen;
import com.badlogic.gdx.math.Rectangle;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class FireTruckTest {

	 private FireTruck truck;
	 Float[] truckStats={300f, 1.5f, 400f, 300f};
	 
	 @Before
	    public void setupMock() {
	        Mockito.mock(FireStation.class);
	        Mockito.mock(Kroy.class);
	        Mockito.mock(GameObject.class);
	        PowerMockito.spy(new Kroy());			
			Mockito.mock(Entity.class);
			Mockito.mock(GameScreen.class);
			
			PowerMockito.mockStatic(Kroy.class);
		 	PowerMockito.constructor(Kroy.class);
		 	PowerMockito.mockStatic(GameScreen.class);
		 	PowerMockito.mockStatic(GameObject.class);
		 	PowerMockito.mockStatic(Entity.class);
		 	         
	    }	

	@Before
	public void init() {
		truck = new FireTruck();
	}

	@Test
	public void testInitialisation() {
		org.junit.Assert.assertTrue(truck.getHealthPoints() == 100);
	}
	
	@Test
	public void Hitbox() {
		
		Rectangle hitbox = new Rectangle(20, 45, 20, 20);

		assertTrue((int) truck.getHitbox().x == hitbox.x);
		assertTrue((int) truck.getHitbox().y == hitbox.y);
		assertTrue((int) truck.getHitbox().width == hitbox.width);
		assertTrue((int) truck.getHitbox().height == hitbox.height);
	}

	@Test
	public void testRefill() {
		
		truck.setHealthPoints(2);		
		truck.replenish();
		assertTrue(truck.getHealthPoints() ==  102);
		assertTrue(truck.getCurrentWater() ==  302);
	}

}          