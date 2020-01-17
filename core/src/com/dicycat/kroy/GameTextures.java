package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;

/**
 * Stores textures for classes to reference.
 * This means multiple of the same sprite use the same reference.
 * Because of this, render calls are reduced.
 * 
 * @author Riju De
 *
 */
public class GameTextures {
	Texture truck;
	Texture ufo;
	Texture bullet;
	String[] trucks = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png"};
	
	
	public GameTextures(int truckNum) {
		truck = new Texture(trucks[truckNum]);
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
	}
	
	public Texture Truck() {
		return truck;
	}
	
	public Texture UFO() {
		return ufo;
	}
	
	public Texture Bullet() {
		return bullet;
	}
}
