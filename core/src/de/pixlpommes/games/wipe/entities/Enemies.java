package de.pixlpommes.games.wipe.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * <p>Manages all enemies.</p>
 * 
 * @author Thomas Borck
 */
public class Enemies {

	/** TODO: description */
	private List<Enemy> _enemies;
	
	/** TODO: description */
	private Vector3 _arena;
	
	
	/**
	 * <p>TODO: a new Enemies-instance</p>
	 * @param arena
	 */
	public Enemies(Vector3 arena) {
		_enemies = new ArrayList<Enemy>();
		_arena = arena;
	}
	
	/**
	 * @param player
	 */
	public void add(Player player) {
		Enemy e = new Enemy(player);
		e.spawn(_arena);
		_enemies.add(e);
	}
	
	/**
	 * Update logic for each enemy.
	 * @param delta
	 */
	public void update(float delta) {
		// remove killed enemies and update the living ones
		for(int i = _enemies.size()-1; i >= 0; i--) {
			if(_enemies.get(i).isDead()) {
				_enemies.remove(i);
				continue;
			}
			
			_enemies.get(i).update(delta);
		}
	}
	
	/**
	 * Draw each enemy alive.
	 * @param batch
	 */
	public void draw(Batch batch) {
		for(Enemy e : _enemies)
			e.draw(batch);
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Vector2 kill(int index) {
		return _enemies.get(index).kill();
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Enemy get(int index) {
		return _enemies.get(index);
	}
	
	/**
	 * @return
	 */
	public List<Enemy> get() {
		return _enemies;
	}
}
