package com.dicycat.kroy.misc;

import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.entities.FireTruck;
import com.dicycat.kroy.screens.GameScreen;

public interface Interactivity {
	
	public default boolean PlayerInRadius(int x, int y, int range, GameScreen gscreen) {
		Vector2 tempVector = new Vector2();
		FireTruck player = gscreen.GetPlayer();
		tempVector = player.GetCentre();
		tempVector = tempVector.sub(x,y);
		if (range>= tempVector.len()) {
			return(true);
		}
		return(false);
	}
	
}
