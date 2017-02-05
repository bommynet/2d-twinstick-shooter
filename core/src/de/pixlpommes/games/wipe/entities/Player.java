package de.pixlpommes.games.wipe.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * <p>A player and its data and graphics.</p>
 * 
 * @author Thomas Borck
 */
public class Player {
	
	/** user input device for this player */
	private Gamepad _pad;
	

	// PLAYER
	/** players position */
	private Vector2 _pos = new Vector2(0, 0);
	
	/** players move direction */
	private Vector2 _dir = new Vector2(0, 0);
	
	/** players size */
	private float _radius = 20f;
	
	/** player moving speed */
	private float _speed = 300;
	
	/** players texture(s) */
	private TextureRegion _texPlayer;
	
	
	/**
	 * <p>TODO: a new Player-instance</p>
	 * @param pad
	 */
	public Player(Gamepad pad) {
		this(pad, new Vector2(0, 100));
	}
	
	/**
	 * <p>TODO: a new Player-instance</p>
	 * @param pad
	 * @param pos
	 * @param lookat
	 */
	public Player(Gamepad pad, Vector2 pos) {
		_pad = pad;
		_pos = pos.cpy();
		
		Texture texPlayer = new Texture(Gdx.files.internal("player.png"));
		_texPlayer = new TextureRegion(texPlayer);
	}
	
	
	/**
	 * <p>Update player logic.</p>
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		Vector2 left = _pad.getLeftStick().nor();
		
		// update position
		_dir = left;
		_pos = _pos.add(_dir.cpy().scl(_speed * dt));
	}
	
	/**
	 * <p>Draw player.</p>
	 * 
	 * @param batch
	 */
	public void draw(Batch batch) {
		batch.begin();
		batch.draw(_texPlayer,
				_pos.x - _texPlayer.getRegionWidth() / 2f, // x
				_pos.y - _texPlayer.getRegionHeight() / 2f, // y
				_texPlayer.getRegionWidth() / 2f, // originX
				_texPlayer.getRegionHeight() / 2f, // originY
				_texPlayer.getRegionWidth(), // width,
				_texPlayer.getRegionHeight(), // height
				1.0f, 1.0f, // scaleX, scaleY
				0.0f); // rotation | TODO: rotate player to look in moving direction
		batch.end();
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
