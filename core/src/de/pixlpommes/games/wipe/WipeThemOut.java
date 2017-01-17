package de.pixlpommes.games.wipe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import de.pixlpommes.games.wipe.entities.Player;
import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * @author Thomas Borck
 *
 */
public class WipeThemOut extends ApplicationAdapter {
	
	private OrthographicCamera _cam;
	
	private Player _player1;
	
	private List<Vector2> _bullets = new ArrayList<Vector2>();
	private List<Vector2> _bulletsSpeed = new ArrayList<Vector2>();
	private float _bulletTimer = 0f;
	private float _bulletDelay = 0.2f;
	
	private float _speed = 200;
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#create()
	 */
	@Override
	public void create() {
		_cam = new OrthographicCamera();
		
		if(Controllers.getControllers().size > 0) {
			Gamepad pad = new Gamepad(Controllers.getControllers().first());
			Controllers.addListener(pad);
			
			_player1 = new Player(pad);
		}
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#render()
	 */
	@Override
	public void render() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// update logic
		_player1.update(deltaTime);
		
		
		// draw entities
		SpriteBatch batch = new SpriteBatch();
		batch.setProjectionMatrix(_cam.combined);
		batch.begin();
		_player1.draw(batch);
		batch.end();
		batch.dispose();
		
		
		
//		if(_bulletTimer > _bulletDelay) {
//			if(!_p1.epsilonEquals(_p1Target, 0.1f)) {
//				_bullets.add(new Vector2(_p1));
//				_bulletsSpeed.add(new Vector2().add(_p1Target).sub(_p1).nor().scl(400));
//			}
//			_bulletTimer = 0f;
//		} else {
//			_bulletTimer += Gdx.graphics.getDeltaTime();
//		}
		
		
		
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(_cam.combined);
		
		
		
		
		
		// draw bullets
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GREEN);
		for(int i = _bullets.size()-1; i >= 0; i--) {
			// update
			_bullets.get(i).add(new Vector2().add(_bulletsSpeed.get(i)).scl(Gdx.graphics.getDeltaTime()));
			
			Vector2 b = _bullets.get(i);
			if(b.x < -Gdx.graphics.getWidth() ||
					b.x > Gdx.graphics.getWidth() ||
					b.y < -Gdx.graphics.getHeight() ||
					b.x > Gdx.graphics.getHeight()) {
				_bullets.remove(i);
				_bulletsSpeed.remove(i);
			} else {
				// draw
				sr.circle(_bullets.get(i).x, _bullets.get(i).y, 4);
			}
		}
		sr.end();
		
		
		
		// draw debug
//		sr.begin(ShapeType.Line);
//		
//		sr.setColor(Color.BLACK);
//		sr.circle(0, 0, 2);
//
//		sr.setColor(Color.RED);
//		sr.line(0, 0, 100 * left.x, 100 * left.y);
//
//		sr.setColor(Color.GREEN);
//		sr.circle(100 * right.x, 100 * right.y, 2);
//		
//		sr.end();
		
		sr.dispose();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		_cam.viewportWidth = width;
		_cam.viewportHeight = height;
		
		_cam.update();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#dispose()
	 */
	@Override
	public void dispose() {
		
	}
}
