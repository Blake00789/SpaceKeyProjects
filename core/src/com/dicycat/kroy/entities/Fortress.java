package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;
import com.dicycat.kroy.screens.GameScreen;

public class Fortress extends Entity {

	BulletDispenser dispenser;
	
	public Fortress(Vector2 spawnPos) {
		super(spawnPos, new Texture("TempFortress.png"), new Vector2(256,218), 100);
		dispenser = new BulletDispenser(this);
		dispenser.AddPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		dispenser.AddPattern(new Pattern(100, 500, 0.5f, 8, 5, 0.5f));
		dispenser.AddPattern(new Pattern(0, 50, 800, 2f, 3, 36, 4));
		dispenser.AddPattern(new Pattern(200, 600, 0.3f, 12, 2, 0.3f));
		dispenser.AddPattern(new Pattern(false, 0, 3, 100, 900, 0.02f, 1, 0.2f));
		dispenser.AddPattern(new Pattern(true, 0, 1, 100, 900, 0.02f, 1, 1.2f));
	}

	public Boolean playerInRadius() {
		Vector2 currentCoords = GameScreen.mainGameScreen.GetPlayer().GetCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, GetCentre().x, GetCentre().y) < radius ) { // checks the distance between the two entities
			GameScreen.mainGameScreen.getHud().updateScore(2);
			return true; // returns true if distance between entity and player is less than radius of item
		}else {
			return false; // returns false otherwise
		}
	}
	
	private void Die() { // Overwritten die implementation allows for removal from gameObjects List so to remove functionality but to display the broken building graphic
		sprite.setTexture(new Texture("TempFortressDead.png"));
		GameScreen.mainGameScreen.getHud().updateScore(1000);
		setRemove(true);
		displayable = true;
	}
	
	public void ApplyDamage(float damage) {
		healthPoints -= damage;
		if (healthPoints <= 0) {
			Die();
		}
	}
	
	@Override
	public void Update() {
		//weapons

		Bullet[] toShoot = dispenser.Update(playerInRadius());
		if (toShoot != null) {
			for (Bullet bullet : toShoot) {
				bullet.Fire(GetCentre());
				GameScreen.mainGameScreen.AddGameObject(bullet);
			}
		}
	}

}
