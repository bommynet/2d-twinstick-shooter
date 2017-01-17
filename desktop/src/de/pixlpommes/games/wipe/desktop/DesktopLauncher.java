package de.pixlpommes.games.wipe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.pixlpommes.games.wipe.WipeThemOut;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 800;
		config.height = 800;
		config.resizable = false;
		
		new LwjglApplication(new WipeThemOut(), config);
	}
}
