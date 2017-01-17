package de.pixlpommes.games.wipe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.pixlpommes.games.wipe.entities.Player;
import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * @author Thomas Borck
 */
public class WipeThemOut extends ApplicationAdapter {
	
	private OrthographicCamera _cam;
	
	private Player[] _players;
	
	private Stage _curStage;
	
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
		
		_players = new Player[1];
		
		if(Controllers.getControllers().size > 0) {
			Gamepad pad = new Gamepad(Controllers.getControllers().first());
			Controllers.addListener(pad);
			
			_players[0] = new Player(pad);
		}
		
		_curStage = new OneRoomStage(_players, _cam);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationAdapter#render()
	 */
	@Override
	public void render() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		_curStage.draw();
		
		
		
//		if(_bulletTimer > _bulletDelay) {
//			if(!_p1.epsilonEquals(_p1Target, 0.1f)) {
//				_bullets.add(new Vector2(_p1));
//				_bulletsSpeed.add(new Vector2().add(_p1Target).sub(_p1).nor().scl(400));
//			}
//			_bulletTimer = 0f;
//		} else {
//			_bulletTimer += Gdx.graphics.getDeltaTime();
//		}
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
		_curStage.dispose();
	}
}
