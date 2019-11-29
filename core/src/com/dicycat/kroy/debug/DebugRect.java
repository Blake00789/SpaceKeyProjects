package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class DebugRect extends DebugDraw {
	Vector2 centre;
	Vector2 dimensions;
	int lineWidth;
	Color color;

	public DebugRect(Vector2 center, Vector2 size, int width, Color colour) {
		super();
		centre = center;
		dimensions = size;
		lineWidth = width;
		color = colour;
	}

	@Override
	public void Draw(Matrix4 projectionMatrix)	//Draw a coloured line between 2 points
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.rect(centre.x - (dimensions.x/2), centre.y - (dimensions.y/2), dimensions.x, dimensions.y);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}
