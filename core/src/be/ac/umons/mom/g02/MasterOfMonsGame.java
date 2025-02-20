package be.ac.umons.mom.g02;

import be.ac.umons.mom.g02.GameStates.Dialogs.OutGameDialogState;
import be.ac.umons.mom.g02.GameStates.Menus.MainMenuState;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Objects.Settings;
import be.ac.umons.mom.g02.Other.LogicSaving;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * The main class of the game.
 * Part of the content is taken from https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike
 * @author Guillaume Cardoen
 */
public class MasterOfMonsGame extends ApplicationAdapter {

	/**
	 * The horizontal size of the window.
	 */
	public static int WIDTH;
	/**
	 * The vertical size of the window.
	 */
	public static int HEIGHT;

//	public static OrthographicCamera cam;

	/**
	 * The entry manager.
	 */
	protected GameInputManager gim;
	/**
	 * The game status manager.
	 */
	protected GameStateManager gsm;
	/**
	 * Game graphics settings.
	 */
	protected GraphicalSettings gs;
	/**
	 * The settings of the game
	 */
	protected static Settings settings;
	/**
	 * The path to the game to load if one has been selected.
	 */
	protected static String gameToLoad;
	/**
	 * The save to load if one has been selected
	 */
	protected static LogicSaving saveToLoad;

	/**
	 * Runs when the application is created.
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
		GameStateManager.createInstance(gs);
		gim = GameInputManager.getInstance();
		gsm = GameStateManager.getInstance();

		gsm.setStateWithoutAnimation(MainMenuState.class);

		Gdx.input.setInputProcessor(gim);
	}

	/**
	 * Runs when the application needs to take shape.
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
	 * @return The settings of the game
	 */
	public static Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings The settings of the game
	 */
	public static void setSettings(Settings settings) {
		MasterOfMonsGame.settings = settings;
	}

	/**
	 * @param gameToLoad The path to the game to load if one has been selected.
	 */
	public static void setGameToLoad(String gameToLoad) {
		MasterOfMonsGame.gameToLoad = gameToLoad;
	}

	/**
	 * @return The path to the game to load if one has been selected.
	 */
	public static String getGameToLoad() {
		return gameToLoad;
	}

	/**
	 * Show an error with an OutGameStateDialog.
	 * @param error The error to show.
	 */
	public static void showAnError(String error) {
		OutGameDialogState ogds = (OutGameDialogState) GameStateManager.getInstance().setState(OutGameDialogState.class);
		ogds.setText(error);
		ogds.addAnswer("OK");
	}

	/**
	 * @param saveToLoad The save to load if one has been selected
	 */
	public static void setSaveToLoad(LogicSaving saveToLoad) {
		MasterOfMonsGame.saveToLoad = saveToLoad;
	}

	/**
	 * @return The save to load if one has been selected
	 */
	public static LogicSaving getSaveToLoad() {
		return saveToLoad;
	}

	/**
	 * Execute when the application is destroyed.
	 */
	@Override
	public void dispose() {
		gs.dispose();
		gsm.dispose();
	}

}