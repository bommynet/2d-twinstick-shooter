package de.pixlpommes.games.wipe.manager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.pixlpommes.games.wipe.entities.Bullet;

/**
 * <p>Manages all bullets on a game screen.</p>
 * 
 * @author Thomas Borck
 */
public class Bullets {

	/** list of all bullets existing */
	private List<Bullet> _bullets;
	
	/** list of all explosions existing */
	private List<Vector3> _explosions;
	
	/** size of each explosion at the beginning and when it will be removed */
	private float _explosionRadiusStart = 50f,
			_explosionRadiusStop = 10f;
	
	/**
	 * <p>Creates a new manager.</p>
	 */
	public Bullets() {
		 _bullets = new ArrayList<Bullet>();
		 _explosions = new ArrayList<Vector3>();
	}
	
	
	/**
	 * <p>Update all existing bullets.</p>
	 * @param delta
	 */
	public void update(float delta) {
		// update bullets
		for(int i = _bullets.size() - 1; i >= 0; i--) {
			// remove 'dead' bullets
			if(_bullets.get(i).isDead()) {
				explode(_bullets.remove(i).getPosition());
				continue;
			}
			
			_bullets.get(i).update(delta);
		}
		
 		// update explosions
 		for(int i = _explosions.size()-1; i >= 0; i--) {
 			if(_explosions.get(i).z < _explosionRadiusStop) {
 				_explosions.remove(i);
 				continue;
 			}
 			_explosions.get(i).z *= 0.9;
 		}
	}
	
	/**
	 * <p>Draw all bullets.</p>
	 * @param batch
	 */
	public void draw(Batch batch) {
		for(Bullet b : _bullets) {
			b.draw(batch);
		}
		

		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Filled);
		// draw explosions
		for(Vector3 v : _explosions) {
			if(v.z > 40)
				sr.setColor(Color.DARK_GRAY);
			else
				sr.setColor(Color.WHITE);
			
			sr.circle(v.x, v.y, v.z);
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
		Bullet b = new Bullet(pos, speed, 12f);
		_bullets.add(b);
	}
	
	/**
	 * @param index
	 * @return bullet at given index or <code>null</code>
	 */
	public Bullet get(int index) {
		if(index < 0 || index >= size()) return null;
		return _bullets.get(index);
	}
	
	/**
	 * @return
	 */
	public List<Bullet> get() {
		return _bullets;
	}
	
	/**
	 * @return amount of existing bullets
	 */
	public int size() {
		return _bullets.size();
	}
	
	/**
	 * <p>Mark a specific bullet as killed.</p>
	 * @param index
	 * @return position of killed bullet
	 */
	public Vector2 kill(int index) {
		if(index < 0 || index >= size()) return null;
		return _bullets.get(index).kill();
	}
	
	public void explode(Vector2 pos) {
		Vector3 ex = new Vector3(pos.x, pos.y, _explosionRadiusStart);
		_explosions.add(ex);
	}
}
