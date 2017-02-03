package de.pixlpommes.games.wipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

import de.pixlpommes.games.wipe.entities.Bullet;
import de.pixlpommes.games.wipe.entities.Enemy;
import de.pixlpommes.games.wipe.entities.Player;
import de.pixlpommes.games.wipe.entities.Torret;
import de.pixlpommes.games.wipe.manager.Bullets;
import de.pixlpommes.games.wipe.manager.Enemies;
import de.pixlpommes.libgdx.gamepad.Gamepad;

/**
 * <p>A game screen.</p>
 * 
 * @author Thomas Borck
 */
public class GameScreen implements Screen {
	
	/** flag, to pause the game (pause and resume) */
	private boolean _isRunning = false;
	
	/** flag, to pause the game (manually by players) */
	private boolean _isPaused = false;
	
	/** arena (as a circle (x,y,r))to play inside */
	private Vector3 _arena;

	/** batch to render everything */
	private SpriteBatch _batch;
	
	/** standard 2D camera */
	private OrthographicCamera _cam;
	
	/** all active players */
	private Player[] _players;
	
	/** a players torret */
	private Torret _torret;
	
	/** TODO: description */
	private Bullets _bullets;
	
	/** TODO: description */
	private Enemies _enemies;
	
	private float _enemySpawnTimer = 0f;
	private float _enemySpawnDelay = 1f;
	
	
	// EFFECTS
	/** TODO: description */
	private float _sleeping;
	
	private boolean _isSleeping;
	
	
	/**
	 * <p>TODO: a new GameScreen-instance</p>
	 */
	public GameScreen() {
		// setup effects
		_sleeping = 0f;
		_isSleeping = false;
		
		// setup graphics
		_cam = new OrthographicCamera();
		_batch = new SpriteBatch();
		
		// TODO: setup players and their input
		_players = new Player[1];
		
		_bullets = new Bullets();
		
		if(Controllers.getControllers().size > 0) {
			Gamepad pad = new Gamepad(Controllers.getControllers().first());
			Controllers.addListener(pad);
			
			_players[0] = new Player(pad);
			_torret = new Torret(pad, _bullets);
		}
		
		// TODO: setup arena
		_arena = new Vector3(0, 0, 375);
		
		_enemies = new Enemies(_arena);
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
    public void render(float delta) {
        //### USER INPUT ##################################
		/// TODO: process by central input class
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			_isPaused = !_isPaused;
		}
		
		
		//### UPDATE LOGIC ################################
		// update, if game is running
		if(_isRunning && !_isPaused && !_isSleeping) {
			update(delta);
		}
		// do a little 'sleep', if isSleeping is set
		else if(_isRunning && !_isPaused && _isSleeping) {
        	if(_sleeping > 0) {
        		_sleeping -= delta;
        	} else {
        		_isSleeping = false;
        	}
        }
        
		
		//### DRAW EVERYTHING #############################
		draw();
	}
	
	/**
	 * 
	 */
	private void draw() {
		// prepare OpenGL state
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
        // clear screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
 		
        //### LAYER 0: BACKGROUND #########################
        
        
        //### LAYER 1: ARENA ##############################
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(_batch.getProjectionMatrix());
        sr.begin(ShapeType.Line);
        sr.setColor(Color.RED);
        sr.circle(_arena.x, _arena.y, _arena.z);
        sr.end();
        sr.dispose();
        
        
        //### LAYER 5: ENEMIES & BULLETS ##################
        _enemies.draw(_batch);
        _bullets.draw(_batch);
        
        
        //### LAYER 10: PLAYERS ###########################
        for(Player p : _players)
			p.draw(_batch);
        _torret.draw(_batch);
        
        
        //### LAYER 20: OVERLAY ############################
        // TODO: draw 'pause'-info to screen
		if(_isPaused) {
			Gdx.app.log("MainLoop", "isPaused");
		}
		
		
		// post set OpenGL state
		Gdx.gl.glDisable(GL20.GL_BLEND);
    }
	
	/**
	 * @param delta
	 */
	private void update(float delta) {
    	//### UPDATE PLAYER ###############################
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
 			
 			// check for collision with enemies
 			for(Enemy e : _enemies.get()) {
 				// ignore inactive enemies
 				if(!e.isMoving()) continue;
 				
 				// calc lengths
 				Vector2 dist = p.getPosition().cpy().sub(e.getPosition());
 				float lenDist2 = dist.len2();
 				float lenRad2 = (float)Math.pow(p.getRadius() + e.getRadius(), 2);
 				
 				if(lenDist2 <= lenRad2) {
 					/// TODO: how to react on 'player got hit'
 					/// currently: kill enemy, hit player
 					e.kill();
 					p.hit();
 				}
 			}
 		}
 		
 		
 		//### UPDATE TORRET ###############################
 		_torret.update(delta);
 		
 		
 		//### UPDATE BULLETS ##############################
 		_bullets.update(delta);
 		
 		// check for collision between bullets and arena
 		for(Bullet b : _bullets.get()) {
 			float len = b.getPosition().len();
 			float radius = b.getRadius();
 			
 			if(len + radius > _arena.z) {
 				b.kill();
 			}
 		}
 		
 		
 		//### UPDATE ENEMIES ##############################
 		_enemies.update(delta);
 		
 		// check for collision between enemies and bullets
 		for(Enemy e : _enemies.get()) {
 			// only check complete spawned enemies (moving ones)
 			if(!e.isMoving()) continue;
 			
 			for(Bullet b : _bullets.get()) {
 				Vector2 dist = e.getPosition().cpy().sub(b.getPosition());
 				float len = dist.len();
 				float lenCollision = e.getRadius() + b.getRadius();
 				
 				if(len <= lenCollision) {
 					// hit/kill both entities
 					e.hit();
 					b.kill();
 					
 					// add effekt 'sleep' for each kill
 					sleep();
 					
 					// leave inner loop
 					break;
 				}
 			}
 		}
 		
 		
 		// spawn new enemies
 		if(_enemySpawnTimer > _enemySpawnDelay) {
 			_enemies.add(_players[0]);
 			_enemySpawnTimer = 0f;
 		} else {
 			_enemySpawnTimer += delta;
 		}
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

    /**
     * 
     */
    private void sleep() {
    	_sleeping = 0.02f;
    	_isSleeping = true;
    }
}
