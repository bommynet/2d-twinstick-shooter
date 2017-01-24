package de.pixlpommes.games.wipe.manager;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.pixlpommes.games.firework.types.Particle;
import de.pixlpommes.games.wipe.entities.Enemy;
import de.pixlpommes.games.wipe.entities.Player;

/**
 * <p>Manages all enemies.</p>
 * 
 * @author Thomas Borck
 */
public class Enemies {

	/** list of all existing enemies */
	private List<Enemy> _enemies;
	
	/** list to all existing explosions */
	private List<Particle> _explosions;
	
	/** amount of particles per explosion */
	private final static int PARTICLES = 100;
	
	/** amount of decreased alpha-value per second for explosions */
	private final static float FADEOUT = 400f;
	
	/** reference to arena boundings */
	private Vector3 _arena;
	
	
	/**
	 * <p>TODO: a new Enemies-instance</p>
	 * @param arena
	 */
	public Enemies(Vector3 arena) {
		_enemies = new ArrayList<Enemy>();
		_explosions = new ArrayList<Particle>();
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
				explode(_enemies.remove(i).getPosition());
				continue;
			}
			
			_enemies.get(i).update(delta);
		}
		
		// update all explosions
		for(int i = _explosions.size()-1; i >= 0; i--) {
			Particle ex = _explosions.get(i);
			
			if(ex.getLifespan() < 0) {
				_explosions.remove(i);
			} else {
				ex.setLifespan(ex.getLifespan() - FADEOUT*delta);
			}
			
			ex.getVelocity().scl(0.9f);
			ex.update();
		}
	}
	
	/**
	 * Draw each enemy alive.
	 * @param batch
	 */
	public void draw(Batch batch) {
		for(Enemy e : _enemies)
			e.draw(batch);
		
		// draw explosions
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(batch.getProjectionMatrix());
		sr.begin(ShapeType.Filled);
		for(Particle p : _explosions) {
			sr.setColor(1f, 0f, 0f, p.getLifespan()/255f);
			p.draw(sr);
		}
		sr.end();
		sr.dispose();
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Vector2 kill(int index) {
		return _enemies.get(index).kill();
	}
	
	/**
	 * Let an enemy explode.
	 * @param pos
	 */
	public void explode(Vector2 pos) {
		List<Particle> ex = new ArrayList<Particle>(PARTICLES);
		for(int i = 0; i < PARTICLES; i++) {
			// each particle has a random direction and speed
			Vector2 vel = new Vector2().setToRandomDirection();
			vel.scl((float) (Math.random()*4));
			
			// setup new particle
			Particle p = new Particle(1, pos.x, pos.y, vel.x, vel.y);
			ex.add(p);
		}
		_explosions.addAll(ex);
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
