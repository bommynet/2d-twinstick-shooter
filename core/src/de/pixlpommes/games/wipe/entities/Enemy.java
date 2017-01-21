package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * <p>A basic enemy.</p>
 * 
 * @author Thomas Borck
 */
public class Enemy {
	
	// ENEMY
	/** enemies position */
	protected Vector2 _pos;
	
	/** enemies velocity */
	protected Vector2 _vel;
	
	/** enemies acceleration */
	protected Vector2 _acc;
	
	/** enemies maximum moving speed */
	protected float _maxspeed = 150;
	
	/** enemies maximum velocity changing */
	protected float _maxforce = 0.1f;
	
	
	// SPAWNING
	/** flag, if enemy is spawned already */
	protected boolean _isSpawned;
	
	protected float _spawnDelay;
	
	
	// PLAYER
	/** for pathfinding the enemy need to know players position */
	protected Player _player;
	
	
	/**
	 * <p>Create enemy instance without showing.</p>
	 * @param player
	 */
	public Enemy(Player player) {
		_player = player;
		
		_pos = new Vector2(0, 0);
		_vel = new Vector2(0, 0);
		_acc = new Vector2(0, 0);
		_isSpawned = false;
	}
	
	
	/**
	 * @param spawn
	 */
	public void spawn(Vector2 spawn) {
		_pos = new Vector2(spawn);
		_isSpawned = true;
	}
	
	/**
	 * @param force
	 */
	public void applyForce(Vector2 force) {
		_acc.add(force);
	}
	
	/**
	 * Update position.
	 * @param delta
	 */
	public void update(float delta) {
		// steering (seek)
		Vector2 desired = _player.getPosition().cpy().sub(_pos);
		desired.nor().scl(_maxspeed * delta);
		
		Vector2 steer = desired.cpy().sub(_vel);
		steer.limit(_maxforce);
		
		applyForce(steer);
		
		// standard 'moveable' operations
		_vel.add(_acc);
		_acc.setZero();
		_pos.add(_vel);
	}
	
	/**
	 * @param batch
	 */
	public void draw(Batch batch) {
		if(_isSpawned) {
			ShapeRenderer sr = new ShapeRenderer();
			sr.setProjectionMatrix(batch.getProjectionMatrix());
			
			// draw enemy
			sr.begin(ShapeType.Filled);
			
			sr.setColor(Color.RED);
			sr.circle(_pos.x, _pos.y, 10);
			
			sr.end();
			sr.dispose();
		}
	}
}
