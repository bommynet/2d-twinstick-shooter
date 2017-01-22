package de.pixlpommes.games.wipe.manager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import de.pixlpommes.games.wipe.entities.Bullet;

/**
 * <p>Manages all bullets on a game screen.</p>
 * 
 * @author Thomas Borck
 */
public class Bullets {

	/** list of all bullets existing */
	private List<Bullet> _bullets;
	
	
	/**
	 * <p>Creates a new manager.</p>
	 */
	public Bullets() {
		 _bullets = new ArrayList<Bullet>();
	}
	
	
	/**
	 * <p>Update all existing bullets.</p>
	 * @param delta
	 */
	public void update(float delta) {
		for(int i = _bullets.size() - 1; i >= 0; i--) {
			// remove 'dead' bullets
			if(_bullets.get(i).isDead()) {
				_bullets.remove(i);
				continue;
			}
			
			_bullets.get(i).update(delta);
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
	}
	
	/**
	 * <p>Add a new bullet.</p>
	 * @param pos
	 * @param speed
	 */
	public void add(Vector2 pos, Vector2 speed) {
		Bullet b = new Bullet(pos, speed);
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
}
