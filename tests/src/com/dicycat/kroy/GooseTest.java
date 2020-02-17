/**
 * 
 */
package com.dicycat.kroy;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.math.Rectangle;
import com.dicycat.kroy.entities.Goose;

/**
 * @author Sam Hutchings
 *
 */
@RunWith(GdxTestRunner.class)
public class GooseTest {

	private Goose goose;

	@Before
	public void init() {
		goose = new Goose();
	}

	
	@Test
	public void hitboxTest() {
		float scale = goose.getScale();
		Rectangle hitbox = new Rectangle(0, 0, 46 * scale, 54 * scale);
		assertEquals(hitbox, goose.getHitbox());
	}

	@Test
	public void movementTest() {
		// Gravity 
		float y1 = goose.getY(); 
		goose.update();
		float y2 = goose.getY();
		assertTrue(y1 > y2);

		// Jumping
		goose.setVelocity(10);
		goose.update();
		float y3 = goose.getY(); 
		assertTrue(y3 > y2);
	}

	@After
	public void clean() {
		goose.getTexture().dispose();
	}

}
