package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * <p>A basic enemy.</p>
 * 
 * @author Thomas Borck
 */
public class Enemy {
	
	protected int _life;
	
	// ENEMY
	/** enemies position */
	protected Vector2 _pos;
	
	/** enemies velocity */
	protected Vector2 _vel;
	
	/** enemies acceleration */
	protected Vector2 _acc;
	
	/** enemies size */
	protected float _radius;
	
	/** enemies maximum moving speed */
	protected float _maxspeed = 150;
	
	/** enemies maximum velocity changing */
	protected float _maxforce = 0.1f;
	
	
	// SPAWNING
	/** flag, if enemy is spawned already */
	protected boolean _isSpawned;
	
	/** flag, if enemy is 'landed' and moves towards the player */
	protected boolean _isMoving;
	
	/** time between spawning and landing on the ground */
	protected float _spawnDelay;
	
	/** is enemy alive */
	protected boolean _isAlive;
	
	
	// PLAYER
	/** for pathfinding the enemy need to know players position */
	protected Player _player;
	
	
	/**
	 * <p>Create enemy instance without showing.</p>
	 * @param player
	 */
	public Enemy(Player player) {
		_player = player;
		
		_life = 2;
		
		_pos = new Vector2(0, 0);
		_vel = new Vector2(0, 0);
		_acc = new Vector2(0, 0);
		_radius = 15f;
		
		_isSpawned = false;
		_isMoving = false;
		_isAlive = true;
		
		_spawnDelay = 1f;
	}
	
	
	/**
	 * @param arena
	 */
	public void spawn(Vector3 arena) {
		// choose random direction
		Vector2 direction = new Vector2(
				(float)(Math.random()*2 - 1),
				(float)(Math.random()*2 - 1));
		direction.nor();
		
		// choose random distance (50% of arena, 100% of arena)
		float length = (float)Math.random()*arena.z/2 + arena.z/2;
		
		// spawn at random position
		_pos = direction.scl(length);
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
		// update position if enemy is spawned and on the ground
		if(_isSpawned && _isMoving) {
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
		// update falling in
		else if(_isSpawned && !_isMoving) {
			if(_spawnDelay > 0)
				_spawnDelay -= delta;
			else
				_isMoving = true;
		}
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
			
			if(_isMoving)
				sr.setColor(Color.RED);
			else
				sr.setColor(Color.DARK_GRAY);
			
			sr.circle(_pos.x, _pos.y, _radius);
			
			sr.end();
			sr.dispose();
		}
	}
	
//	/**
//	 * Kills this enemy.
//	 * @return last position of the enemy
//	 */
//	public Vector2 kill() {
//		_isAlive = false;
//		return _pos;
//	}
	
	public void hit() {
		_life--;
		
		if(_life <= 0)
			_isAlive = false;
	}
	
	/**
	 * @return enemy is dead
	 */
	public boolean isDead() {
		return !_isAlive;
	}
	
	/**
	 * @return enemy is spawned and moving
	 */
	public boolean isMoving() {
		/* checking 'isMoving' would be enough...check
		 * 'isSpawned' just to be sure */
		return _isSpawned && _isMoving;
	}
	
	/**
	 * @return
	 */
	public Vector2 getPosition() {
		return _pos;
	}
	
	/**
	 * @return
	 */
	public float getRadius() {
		return _radius;
	}
}
