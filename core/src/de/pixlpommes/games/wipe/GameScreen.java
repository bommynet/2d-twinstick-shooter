package de.pixlpommes.games.wipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import de.pixlpommes.games.wipe.entities.Bullets;
import de.pixlpommes.games.wipe.entities.Player;
import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * <p>A game screen.</p>
 * 
 * @author Thomas Borck
 */
public class GameScreen implements Screen {
	
	/** flag to pause the game */
	private boolean _isRunning = false;
	
	/** arena (as a circle (x,y,r))to play inside */
	private Vector3 _arena;

	/** batch to render everything */
	private SpriteBatch _batch;
	
	/** standard 2D camera */
	private OrthographicCamera _cam;
	
	/** all active players */
	private Player[] _players;
	
	private Bullets _bullets;
	
	/**
	 * <p>TODO: a new GameScreen-instance</p>
	 */
	public GameScreen() {
		// setup graphics
		_cam = new OrthographicCamera();
		_batch = new SpriteBatch();
		
		// TODO: setup players and their input
		_players = new Player[1];
		
		_bullets = new Bullets();
		
		if(Controllers.getControllers().size > 0) {
			Gamepad pad = new Gamepad(Controllers.getControllers().first());
			Controllers.addListener(pad);
			
			_players[0] = new Player(pad, _bullets);
		}
		
		// TODO: setup arena
		_arena = new Vector3(0, 0, 375);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
    public void render(float delta) {
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Gdx.app.log("GameScreen->delta", ""+delta);
        
        // update logic
        if(_isRunning) {
	 		for(Player p : _players) {
	 			// update position
	 			p.update(delta);
	 			
	 			// check for collision with arena walls
	 			if(p.getPosition().len() + p.getRadius() > _arena.z) {
	 				Gdx.app.log("GameScreen:Player", "hit arena wall");
	 				
	 				Vector2 pos = new Vector2(p.getPosition());
	 				pos.nor().scl(_arena.z - p.getRadius());
	 				
	 				p.setPosition(pos);
	 			}
	 		}
	 		
	 		_bullets.update(delta);
	 		
	 		
        }
 		
		// draw entities
		for(Player p : _players)
			p.draw(_batch);
		_bullets.draw(_batch);
		
		// draw arena
		ShapeRenderer sr = new ShapeRenderer();
		sr.setProjectionMatrix(_batch.getProjectionMatrix());
		
		// draw player
		sr.begin(ShapeType.Line);
		sr.setColor(Color.RED);
		sr.circle(_arena.x, _arena.y, _arena.z);
		sr.end();
		sr.dispose();
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
        
        // update camera
		_cam.viewportWidth = width;
		_cam.viewportHeight = height;
		_cam.update();
		
		// update renderer
		_batch.setProjectionMatrix(_cam.combined);
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#show()
     */
    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
        
        // game loop running
        _isRunning = true;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#hide()
     */
    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#pause()
     */
    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
        
        // game loop paused
        _isRunning = false;
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#resume()
     */
    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
        
        // game loop running
        _isRunning = true;    
    }

    /* (non-Javadoc)
     * @see com.badlogic.gdx.Screen#dispose()
     */
    @Override
    public void dispose() {
    	Gdx.app.log("GameScreen", "dispose called");
    }

}
