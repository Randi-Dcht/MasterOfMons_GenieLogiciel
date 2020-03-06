package be.ac.umons.mom.g02.desktop;

import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Settings;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		MasterOfMonsGame.settings = SuperviserNormally.getSupervisor().getSave().getSavingGraphic();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MasterOfMonsGame.settings.getGameResolutionWidth();
		config.height = MasterOfMonsGame.settings.getGameResolutionHeight();
		config.samples = 3;
		new LwjglApplication(new MasterOfMonsGame(), config);
	}
}
