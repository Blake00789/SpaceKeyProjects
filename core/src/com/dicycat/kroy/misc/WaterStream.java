package com.dicycat.kroy.misc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;

/**
* 
* @author 
*
*/
public class WaterStream extends GameObject{

	/**
	 * @param spawnPos
	 */
	public WaterStream(Vector2 spawnPos) {
		super(spawnPos, new Texture("lightBlue.png"), new Vector2(1,1));
	}
	
	/**
	 * @param x
	 */
	public void setRange(float x){
		sprite.setScale(x,2);
	}

	/**
	 *
	 */
	@Override
	public void update() {}
}
