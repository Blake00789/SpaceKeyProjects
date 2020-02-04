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
		Rectangle hitbox = new Rectangle(0, 0, 48*scale, 56*scale);
		assertEquals(hitbox,goose.getHitbox());
	}
	
	@After
	public void clean() {
		goose.getTexture().dispose();
	}

}
