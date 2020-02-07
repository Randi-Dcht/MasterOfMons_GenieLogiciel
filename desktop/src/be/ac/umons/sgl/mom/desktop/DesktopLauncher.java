package be.ac.umons.sgl.mom.desktop;

import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.Settings;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		MasterOfMonsGame.settings = new Settings();// TODO : Load MasterOfMonsGame.Settings files
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MasterOfMonsGame.settings.getGameResolutionWidth();
		config.height = MasterOfMonsGame.settings.getGameResolutionHeight();
		config.samples = 3;
		new LwjglApplication(new MasterOfMonsGame(), config);
	}
}
