package be.ac.umons.mom.g02;

import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Settings;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * La classe principale du jeu.
 * Une partie du contenu est tiré de https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids par ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class MasterOfMonsGame extends ApplicationAdapter {


	// For me : https://www.youtube.com/watch?v=VAMEpiKTUZI&list=PL-2t7SM0vDfeZUKeM7Jm4U9utHwFS1N-C&index=4

	/**
	 * La taille horizontale de la fenêtre.
	 */
	public static int WIDTH;
	/**
	 * La taille verticale de la fenêtre.
	 */
	public static int HEIGHT;

//	public static OrthographicCamera cam;

	/**
	 * Le gestionnaire d'entrée du jeu.
	 */
	protected GameInputManager gim;
	/**
	 * Le gestionnaire d'état du jeu.
	 */
	protected GameStateManager gsm;
	/**
	 * Les paramètres graphiques du jeu.
	 */
	protected GraphicalSettings gs;

	public static Settings settings;

	/**
	 * S'éxécute quand l'application est crée.
	 */
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		gs = new GraphicalSettings();
		gs.setSmallFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.02 * (float)HEIGHT));
		gs.setNormalFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.05 * (float)HEIGHT));
		gs.setTitleFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.1 * (float)HEIGHT));
		gs.setQuestFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.015 * (float)HEIGHT));
		gs.setLanguage(settings.getLanguage());
		gim = new GameInputManager();
		gsm = new GameStateManager(gim, gs);

		Gdx.input.setInputProcessor(gim);
	}

	/**
	 * S'éxécute quand l'application doit se dessiner.
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		gsm.update(Gdx.graphics.getDeltaTime());
		gim.update();
		gsm.draw();
	}

	/**
	 * S'éxécute quand l'application se détruit.
	 */
	@Override
	public void dispose () {
		gs.dispose();
		gsm.dispose();
	}

}