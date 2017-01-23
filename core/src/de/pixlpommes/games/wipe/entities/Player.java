package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import de.pixlpommes.games.wipe.manager.Bullets;
import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * <p>A player and all it's data and graphics.</p>
 * 
 * @author Thomas Borck
 */
public class Player {

	/** difference allowed between two vectors to be 'equal' */
	private final float EQUALDIFF = 0.05f;
	
	/** user input device for this player */
	private Gamepad _pad;
	

	// PLAYER
	/** players position */
	private Vector2 _pos = new Vector2(0, 0);
	
	/** players move direction */
	private Vector2 _dir = new Vector2(0, 0);
	
	/** players face direction */
	private Vector2 _lookat = new Vector2(1, 0);
	
	/** players size */
	private float _radius = 20f;
	
	/** player moving speed */
	private float _speed = 300;
	
	
	// TARGET CROSS
	/** target cross position */
	private Vector2 _cross = new Vector2(0, 0);
	
	/** target cross distance from player */
	private float _crossDistance = 50;
	
	
	// BULLETS
	/** current timer for next bullet to spawn */
	private float _bulletTimer = 0f;
	
	/** interval to spawn new bullets */
	private float _bulletDelay = 0.1f;
	
	/** bullet moving speed */
	private float _bulletSpeed = 600f;
	
	/** bullet manager */
	private Bullets _bullets;
	
	
	/**
	 * <p>TODO: a new Player-instance</p>
	 * @param pad
	 */
	public Player(Gamepad pad, Bullets bullets) {
		this(pad, bullets, new Vector2(), new Vector2(1, 0));
	}
	
	/**
	 * <p>TODO: a new Player-instance</p>
	 * @param pad
	 * @param pos
	 * @param lookat
	 */
	public Player(Gamepad pad, Bullets bullets, Vector2 pos, Vector2 lookat) {
		_pad = pad;
		_bullets = bullets;
		_pos = pos.cpy();
		_lookat = lookat.cpy();
	}
	
	
	/**
	 * <p>Update player logic.</p>
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		Vector2 left = _pad.getLeftStick().nor();
		Vector2 right = _pad.getRightStick().nor();
		
		// update position
		_dir = left;
		_pos = _pos.add(new Vector2(_dir).scl(_speed * dt));
		
		// update cross
		_cross = right.cpy().scl(_crossDistance).add(_pos);
		
		// update face direction
		if(!_pos.epsilonEquals(_cross, EQUALDIFF)) {
			_lookat = right.cpy();
		}
		
		// fire!
		if(_bulletTimer > _bulletDelay) {
			if(!_pos.epsilonEquals(_cross, EQUALDIFF)) {
				_bullets.add(_pos.cpy(), _cross.cpy().sub(_pos).nor().scl(_bulletSpeed));
			}
			_bulletTimer = 0f;
		} else {
			_bulletTimer += dt;
		}
	}
	
	/**
	 * <p>Draw player.</p>
	 * 
	 * @param batch
	 */
	public void draw(Batch batch) {
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		
		// draw player
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.YELLOW);
		sr.circle(_pos.x, _pos.y, _radius);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		Vector2 marker = new Vector2(_dir).scl(10).add(_pos);
		sr.line(_pos.x, _pos.y, marker.x, marker.y);
		
		sr.setColor(Color.YELLOW);
		Vector2 c = new Vector2(_lookat).scl(2*_radius).add(_pos);
		sr.circle(c.x, c.y, _radius*0.75f);
		sr.end();
		
		// draw target cross
		if(!_pos.epsilonEquals(_cross, EQUALDIFF)) {
			sr.begin(ShapeType.Line);
			sr.setColor(Color.RED);
			sr.line(_cross.x - 5, _cross.y, _cross.x + 5, _cross.y);
			sr.line(_cross.x, _cross.y - 5, _cross.x, _cross.y + 5);
			sr.end();
		}
		
		sr.dispose();
	}
	
	/**
	 * Player was hit.
	 */
	public void hit() {
		Gdx.app.log("Player", "got hit");
	}
	
	/**
	 * @return current player position
	 */
	public Vector2 getPosition() {
		return _pos;
	}
	
	/**
	 * <p>Sets player to new position.</p>
	 * @param pos
	 */
	public void setPosition(Vector2 pos) {
		// TODO: error handling
		_pos = pos;
	}
	
	/**
	 * @return size of the player as radius of a circle
	 */
	public float getRadius() {
		return _radius;
	}
}
