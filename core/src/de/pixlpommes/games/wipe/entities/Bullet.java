package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * <p>A bullet.</p>
 * 
 * @author Thomas Borck
 */
public class Bullet {

	// TODO: differentiate between player and enemy bullets?
	// TODO: differentiate between player1 and player2 bullets?
	
	/** bullet position */
	private Vector2 _pos;
	
	/** bullet size */
	private float _radius;
	
	/** bullet speed and direction */
	private Vector2 _speed;
	
	/** flag, if bullet is alive or can be removed */
	private boolean _isAlive;
	
	
	/**
	 * <p>Create a new bullet.</p>
	 * @param position
	 * @param speed
	 * @param radius
	 */
	public Bullet(Vector2 position, Vector2 speed, float radius) {
		_pos = position.cpy();
		_speed = speed.cpy();
		_radius = radius;
		
		_isAlive = true;
	}
	
	/**
	 * <p>Create a new bullet.</p>
	 * @param position
	 * @param speed
	 */
	public Bullet(Vector2 position, Vector2 speed) {
		this(position, speed, 7f);
	}
	
	
	/**
	 * Update position.
	 * @param delta
	 */
	public void update(float delta) {
		Vector2 step = _speed.cpy();
		step.scl(delta);
		_pos.add(step);
	}
	
	/**
	 * Draw bullet.
	 * @param batch
	 */
	public void draw(Batch batch) {
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Filled);
		
		sr.setColor(Color.GREEN);
		sr.circle(_pos.x, _pos.y, _radius);
		
		sr.end();
		sr.dispose();
	}
	
	/**
	 * @return the bullets position
	 */
	public Vector2 getPosition() {
		return _pos;
	}
	
	/**
	 * @return the bullets size
	 */
	public float getRadius() {
		return _radius;
	}
	
	/**
	 * Marks this bullet as killed/removable.
	 */
	public Vector2 kill() {
		_isAlive = false;
		return _pos;
	}
	
	/**
	 * @return true, if bullet was killed
	 */
	public boolean isDead() {
		return !_isAlive;
	}
}
