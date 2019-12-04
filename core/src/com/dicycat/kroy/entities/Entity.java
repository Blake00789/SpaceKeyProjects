package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.GameObject;
import com.dicycat.kroy.gamemap.TiledGameMap;
import com.dicycat.kroy.screens.GameScreen;

public abstract class Entity extends GameObject{

	protected int healthPoints;
	protected int radius;
	protected TiledGameMap map;
	
	public Entity(GameScreen gScreen, Vector2 spawnPos, Texture img, Vector2 imSize) {
		super(gScreen, spawnPos, img, imSize);
		healthPoints = 10;
		radius = 50;
		changePosition(spawnPos);
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Render(SpriteBatch batch) {
		//batch.draw(GetSprite(), GetPos().x, GetPos().y, GetSize().x, GetSize().y);
	}
}

