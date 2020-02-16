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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import static org.junit.Assert.assertTrue;

import org.junit.After;

@RunWith(GdxTestRunner.class)
public class FireTruckTest {

	 private FireTruck truck;
	 private GameObject gameObject;
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

	/**
	 * Correct amounts should be assigned to truck
	 */	 
	@Test
	public void testInitialisation() {
		org.junit.Assert.assertTrue(truck.getHealthPoints() == 100);
	}
	
	/**
	 * Check the Hitbox
	 */
	@Test
	public void Hitbox() {		
		Rectangle hitbox = new Rectangle(20, 45, 20, 20);

		assertTrue((int) truck.getHitbox().x == hitbox.x);
		assertTrue((int) truck.getHitbox().y == hitbox.y);
		assertTrue((int) truck.getHitbox().width == hitbox.width);
		assertTrue((int) truck.getHitbox().height == hitbox.height);
	}
	

	/**
	 * Check the replenish method to see if HealthPoints and CurrentWater increase correctly
	 */
	@Test
	public void testRefill() {		
		truck.setHealthPoints(2);		
		truck.replenish();
		assertTrue(truck.getHealthPoints() !=  102);  //HealthPoits didn't increase because it will get more than max HealthPoits
		assertTrue(truck.getCurrentWater() ==  302);
		
		truck.applyDamage(10);
		
		assertTrue(truck.getHealthPoints() ==  90); 
		
		truck.replenish();
		assertTrue(truck.getHealthPoints() ==  92);   //increased this time as HealthPoits is lower than max HealthPoits
		assertTrue(truck.getCurrentWater() ==  304);
		
		truck.setCurrentWater(96);
		
		assertTrue(truck.getCurrentWater() ==  400);
		
		truck.replenish();
		
		assertTrue(truck.getHealthPoints() ==  94);   //CurretWater didn't increase because it will get more than max water
		assertTrue(truck.getCurrentWater() ==  400);

	}
	
	/**
	 * Check the movements correctness when user press the movement keys
	 */
	@Test
	public void movementTest() {
		
		truck.setDirection(truck.DIRECTIONS.get("nw"));
		assertTrue(truck.getDirection() == 45);
		
		truck.setDirection(truck.DIRECTIONS.get("se"));
		assertTrue(truck.getDirection() == 225);

		truck.setDirection(truck.DIRECTIONS.get("s"));
		assertTrue(truck.getDirection() == 180);
			
		truck.setDirection(truck.DIRECTIONS.get("ns"));
		assertTrue(truck.getDirection() == null);           //it should not move when we press both up and down keys
		
		truck.setDirection(truck.DIRECTIONS.get("sn"));
		assertTrue(truck.getDirection() == null);  
		
		truck.setDirection(truck.DIRECTIONS.get("we"));
		assertTrue(truck.getDirection() == null);           //it should not move when we press both left and right keys
		

	}

}          