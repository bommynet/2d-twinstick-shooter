package de.pixlpommes.games.wipe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.pixlpommes.games.wipe.entities.Player;

/**
 * <p>TODO: description</p>
 * 
 * @author Thomas Borck
 */
public class OneRoomStage extends Stage {

	/** TODO: description */
	private Camera _cam;
	
	/** TODO: description */
	private Player[] _players;
	
	public OneRoomStage(Player[] players, Camera cam) {
		_players = players;
		_cam = cam;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#draw()
	 */
	public void draw() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// update logic
		for(Player p : _players)
			p.update(deltaTime);
		
		
		// draw entities
		Batch batch = getBatch();
		batch.setProjectionMatrix(_cam.combined);
		batch.begin();
		
		for(Player p : _players)
			p.draw(batch);
		
		batch.end();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Stage#dispose()
	 */
	public void dispose() {
		getBatch().dispose();
	}
}
