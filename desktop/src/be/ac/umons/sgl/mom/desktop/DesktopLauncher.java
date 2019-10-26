package be.ac.umons.sgl.mom.desktop;

import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1920;
		config.height = 1080; // TODO : Let user resize or choose his own resolution
		config.samples = 3;
		new LwjglApplication(new MasterOfMonsGame(), config);
	}
}
