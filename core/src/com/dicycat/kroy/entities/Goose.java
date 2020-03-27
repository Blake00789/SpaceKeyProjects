package com.dicycat.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.dicycat.kroy.Kroy;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Array;

/**
 * Goose class for minigame, acts like the bird in flappy bird
 * 
 * @author Sam Hutchings
 *
 */
public class Goose extends Entity {
	private float deceleration = 0.5f; // Gravity on the goose
	private float velocity = 0; // Speed of the goose falling or flapping
	private Rectangle hitbox = new Rectangle(10, 10, 50, 70); // Hitbox of the goose
	private static float scale = 3; // Allows the goose to be scaled up in size
	private static float textureScale = 200;
	private float xOffset = 100;
	private float yOffset = 70;
	private Animation<TextureRegion>  inFlight;
	private List<TextureRegion> animationFrames;

	/**
	 * @param spawnPos Where the goose spawns
	 * @param img The texture of the goose
	 * @param imSize The size of the goose
	 * @param health The health of the goose unnecessary
	 */
	public Goose(Vector2 spawnPos, Texture img, Vector2 imSize, int health) {
		super(spawnPos, img, imSize, health);
	}

	/**
	 * Instantiate the default goose
	 */
	public Goose() {
		// was * scale
		this(new Vector2(-200, 0 ), new Texture("goose2.png"), new Vector2(scale, scale), 1);
		//UpdateAnimation(1,2);

		inFlight = new Animation<TextureRegion>(1/3f, new TextureRegion(new Texture("0064.png")), new TextureRegion(new Texture("0051.png")));
	}

	private void UpdateAnimation (int startFrame, int EndFrame){
		for (int n = startFrame; n < EndFrame; n++) {
			String frameNameAsInt = String.valueOf(n);
			String frameName = ("0000" + frameNameAsInt).substring(frameNameAsInt.length());
			frameName += ".png";
			animationFrames.add(new TextureRegion(new Texture(frameName)));
		}
	}

	/**
	 * Update the movement of the goose and it's hitbox
	 */
	@Override
	public void update() {
		if (getY() < -400) {
			setVelocity(0);
		} else if (velocity > -5f) {
			velocity -= deceleration;
		}
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			setVelocity(10);
		}
		setPosition(new Vector2(getX(), getY() + velocity));
		hitbox.setCenter(getPosition().x + xOffset, getPosition().y + yOffset);
		if (Kroy.debug) {
			debugDraw();
		}
	}

	/**
	 * Draw debug rectangle of the hitbox 
	 */
	public void debugDraw() {
		Kroy.mainMinigameScreen.DrawRect(new Vector2(hitbox.x, hitbox.y), new Vector2(hitbox.width, hitbox.height), 2,
				Color.GREEN);
	}

	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public  Animation getAnimationInFlight() { return inFlight;}
	/**
	 * @return the scale
	 */
	public float getScale() {
		return scale;
	}

	public float getTextureScale(){
		return textureScale;
	}

	/**
	 * @return the hitbox
	 */
	public Rectangle getHitbox() {
		return this.hitbox;
	}

	/*public float getxOffset(){
		return xOffset;
	}*/

	/**
	 * @return the deceleration
	 */
	public float getDeceleration() {
		return deceleration;
	}

	/**
	 * @return the velocity
	 */
	public float getVelocity() {
		return velocity;
	}

}
