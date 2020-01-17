package com.dicycat.kroy.debug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * Draws debug shapes
 * 
 * @author riju8
 *
 */
public abstract class DebugDraw {
	protected ShapeRenderer debugRenderer;	//Shape renderer for drawing debug shapes
	
	public DebugDraw() {
		debugRenderer = new ShapeRenderer();
	}
	
	public abstract void Draw(Matrix4 projectionMatrix);
}
