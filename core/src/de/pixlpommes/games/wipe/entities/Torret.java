package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.pixlpommes.games.wipe.manager.Bullets;
import de.pixlpommes.libgdx.gamepad.Gamepad;

public class Torret {

	/** difference allowed between two vectors to be 'equal' */
	private final float EQUALDIFF = 0.05f;
	
	/** user input device for this player */
	private Gamepad _pad;
	

	// TORRET
	/** torret position */
	private Vector2 _pos;
	
	/** torret size */
	private float _radius = 20f;

	/** torret texture(s) */
	private TextureRegion _texTorret;
	
	/** torret angle */
	private float _angle;
	
	
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
	 * <p>TODO: a new Torret-instance</p>
	 * @param pad
	 */
	public Torret(Gamepad pad, Bullets bullets) {
		_pad = pad;
		_bullets = bullets;
		_pos = new Vector2(0f, 0f);
		_angle = 0f;
		
		Texture texTorret = new Texture(Gdx.files.internal("torret.png"));
		_texTorret = new TextureRegion(texTorret);
	}
	
	
	/**
	 * <p>Update player logic.</p>
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		Vector2 right = _pad.getRightStick().nor();
		
		// torret angle
		
		
		// update torret angle
		if(!_pos.epsilonEquals(right, EQUALDIFF)) {
			_angle = (float) (180.0f / Math.PI * Math.atan2(right.y, right.x));
		}
		
		// fire!
		if(_bulletTimer > _bulletDelay) {
			if(!_pos.epsilonEquals(right, EQUALDIFF)) {
				// spawn bullets in front of and not below the player
				Vector2 corPos = right.cpy().scl(_texTorret.getRegionWidth() / 2f);
				
				_bullets.add(corPos.cpy(), right.cpy().sub(_pos).nor().scl(_bulletSpeed));
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
		// draw torret
		batch.begin();
		batch.draw(_texTorret,
				-_texTorret.getRegionWidth() / 2f, // x
				-_texTorret.getRegionHeight() / 2f, // y
				_texTorret.getRegionWidth() / 2f, // originX
				_texTorret.getRegionHeight() / 2f, // originY
				_texTorret.getRegionWidth(), // width,
				_texTorret.getRegionHeight(), // height
				1.0f, 1.0f, // scaleX, scaleY
				_angle); // rotation
		batch.end();
	}
	
	/**
	 * Torret was hit.
	 */
	public void hit() {
		Gdx.app.log("Torret", "got hit");
	}
	
	/**
	 * @return current torret position
	 */
	public Vector2 getPosition() {
		return _pos;
	}
	
	/**
	 * <p>Sets torret to new position.</p>
	 * @param pos
	 */
	public void setPosition(Vector2 pos) {
		// TODO: error handling
		_pos = pos;
	}
	
	/**
	 * @return size of the torret as radius of a circle
	 */
	public float getRadius() {
		return _radius;
	}
}
