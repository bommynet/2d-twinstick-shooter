package de.pixlpommes.games.wipe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Basic game class.
 * 
 * @author Thomas Borck
 */
public class WipeThemOut extends Game {

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	@Override
	public void create() {
		Gdx.app.log("WipeThemOut", "created");
        setScreen(new GameScreen());
	}
}
