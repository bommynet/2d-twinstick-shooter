package de.pixlpommes.games.wipe.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * <p>TODO: description</p>
 * 
 * @author Thomas Borck
 */
public class Bullets {
	
	/** radius of each bullet */
	public static final float RADIUS = 7;

	/** list of all bullets existing */
	private List<Vector2> _bullets = new ArrayList<Vector2>();
	
	/** list of bullet speed for each existing bullet */
	private List<Vector2> _bulletsSpeed = new ArrayList<Vector2>();
	
	/**
	 * <p>Update all bullets.</p>
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		for(int i = 0; i < _bullets.size(); i++) {
			_bullets.get(i).add(_bulletsSpeed.get(i).cpy().scl(delta));
		}
	}
	
	/**
	 * <p>Draw all bullets.</p>
	 * 
	 * @param batch
	 */
	public void draw(Batch batch) {
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Filled);
		
		sr.setColor(Color.GREEN);
		for(Vector2 b : _bullets) {
			sr.circle(b.x, b.y, RADIUS);
		}
		
		sr.end();
		sr.dispose();
	}
	
	/**
	 * <p>Add a new bullet.</p>
	 * @param pos
	 * @param speed
	 */
	public void add(Vector2 pos, Vector2 speed) {
		_bullets.add(pos);
		_bulletsSpeed.add(speed);
	}
	
	/**
	 * @param index
	 * @return bullet at given index or <code>null</code>
	 */
	public Vector2 get(int index) {
		if(index < 0 || index >= size()) return null;
		
		return _bullets.get(index);
	}
	
	/**
	 * @return amount of existing bullets
	 */
	public int size() {
		return _bullets.size();
	}
	
	/**
	 * <p>Removes a bullet from the game.</p>
	 * @param index
	 * @return
	 */
	public Vector2 kill(int index) {
		if(index < 0 || index >= size()) return null;
		
		_bulletsSpeed.remove(index);
		return _bullets.remove(index);
	}
}
