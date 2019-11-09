package be.ac.umons.sgl.mom;

import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MasterOfMonsGame extends ApplicationAdapter {

	/** Part of content below was inspired by https://github.com/foreignguymike/legacyYTtutorials/tree/master/libgdxasteroids by ForeignGuyMike */
	// For me : https://www.youtube.com/watch?v=VAMEpiKTUZI&list=PL-2t7SM0vDfeZUKeM7Jm4U9utHwFS1N-C&index=4

	public static int WIDTH;
	public static int HEIGHT;

//	public static OrthographicCamera cam;

	protected GameInputManager gim;
	protected GameStateManager gsm;
	protected GraphicalSettings gs;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		gs = new GraphicalSettings();
		gim = new GameInputManager();
		gsm = new GameStateManager(gim, gs);

		Gdx.input.setInputProcessor(gim);
		gs.setSmallFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.02 * (float)HEIGHT));
		gs.setNormalFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.05 * (float)HEIGHT));
		gs.setTitleFont("Fonts/Comfortaa/Comfortaa-Light.ttf", (int)(0.1 * (float)HEIGHT));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		gim.update();
	}

	@Override
	public void dispose () {
		gs.dispose();
	}

}