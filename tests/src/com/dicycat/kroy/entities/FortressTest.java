package com.dicycat.kroy.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.dicycat.kroy.GdxTestRunner;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


@RunWith(GdxTestRunner.class)
public class FortressTest {
	
	private Fortress fortress;
	
	@Before
	public void init() {
		fortress=new Fortress(new Vector2(2903,3211),new Texture("cliffords tower.png"),new Texture("cliffords tower dead.png"), new Vector2(256, 218));
	}

	@Test
	public void location() {

	    assertTrue( fortress.getCentre().x == 3031.0 );
	    assertTrue( fortress.getCentre().y == 3320.0);
	}
	
	@Test
	public void isDead() {
		if(fortress.isDisplayable()) {
			assertTrue(fortress.getTexture()== new Texture ("cliffords tower dead.png"));
		}
	}
}
