package be.ac.umons.mom.g02.desktop;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Saving;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		MasterOfMonsGame.setSettings(Saving.getSavingGraphic());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MasterOfMonsGame.getSettings().getGameResolutionWidth();
		config.height = MasterOfMonsGame.getSettings().getGameResolutionHeight();
		config.samples = 3;
		new LwjglApplication(new MasterOfMonsGame(), config);
	}
}
