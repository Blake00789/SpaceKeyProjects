package com.dicycat.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import com.dicycat.kroy.bullets.Bullet;
import com.dicycat.kroy.bullets.BulletDispenser;
import com.dicycat.kroy.bullets.Pattern;

public class Fortress extends Entity {

	BulletDispenser dispenser;
	private Texture deadTexture;

	public Fortress(Vector2 spawnPos, Texture fortressTexture, Texture deadTexture, Vector2 size) {
		super(spawnPos, fortressTexture, size, 100);
		this.deadTexture = deadTexture;
		dispenser = new BulletDispenser(this);
		dispenser.AddPattern(new Pattern(180, 300, 800, 0.1f, 20, 1, 0.5f));
		dispenser.AddPattern(new Pattern(100, 500, 0.5f, 8, 5, 0.5f));
		dispenser.AddPattern(new Pattern(0, 50, 800, 2f, 3, 36, 4));
		dispenser.AddPattern(new Pattern(200, 600, 0.3f, 12, 2, 0.3f));
		dispenser.AddPattern(new Pattern(false, 0, 3, 100, 900, 0.02f, 1, 0.2f));
		dispenser.AddPattern(new Pattern(true, 0, 1, 100, 900, 0.02f, 1, 1.2f));
	}

	public Boolean playerInRadius() {
		Vector2 currentCoords = Kroy.mainGameScreen.getPlayer().getCentre(); // get current player coordinates
		if (Vector2.dst(currentCoords.x, currentCoords.y, getCentre().x, getCentre().y) < radius ) { // checks the distance between the two entities
			Kroy.mainGameScreen.getHud().updateScore(2);
			return true; // returns true if distance between entity and player is less than radius of item
		}else {
			return false; // returns false otherwise
		}
	}

	protected void Die() { // Overwritten die implementation allows for removal from gameObjects List so to remove functionality but to display the broken building graphic
		sprite.setTexture(deadTexture);
		Kroy.mainGameScreen.getHud().updateScore(1000);
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
				bullet.Fire(getCentre());
				Kroy.mainGameScreen.AddGameObject(bullet);
			}
		}
	}

}
