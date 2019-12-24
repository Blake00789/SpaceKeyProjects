package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;

public class GameTextures {	//Ensures multiple similar sprites use same texture to aid batching
	Texture truck;
	Texture ufo;
	Texture bullet;
	String[] trucks = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png"};
	
	public GameTextures(int truckNum) {
		
		truck = new Texture(trucks[truckNum]);
		ufo = new Texture("ufo.png");
		bullet = new Texture("singleP.png");
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
