package com.dicycat.kroy.entities;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.GdxTestRunner;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;



import static org.junit.Assert.assertTrue;


@RunWith(GdxTestRunner.class)
public class FireTruckTest {

	private FireTruck truck;
	Float[] truckStats={400f, 1f, 400f, 300f};

	@Before
	public void init() {
		truck = new FireTruck(new Vector2(3750, 4000), truckStats);
	}

	@Test
	public void testInitialisation() {
		org.junit.Assert.assertTrue(truck.getHealthPoints() == 100);
		//org.junit.Assert.assertTrue(truck.tr() == 400);
	}

	@Test
	public void testRefill() {		
		assertTrue(truckStats[2] == 400);		
		float health=truck.getHealthPoints();
		truck.replenish(); 
		assertTrue(truckStats[2]== 402);
		assertTrue(truck.getHealthPoints()- health == 2);
	}

}