package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The state where to user can choose which game to laod.
 * @author Guillaume Cardoen
 */
public class LoadState extends GameState {
    /**
     * An temporary save path.
     */
    protected final String SAVE_PATH = "D:\\Users\\Guillaume\\Documents\\Test\\MOM"; // TODO : Define it itself

    /**
     * Allow to draw shape.
     */
    private ShapeRenderer sr;
    /**
     * Allow to draw
     */
    private SpriteBatch sb;

    /**
     * An int representing how much the user wants to go down.
     */
    private int mouseWheeled = 0;
    /**
     * A list representing all the <code>Button</code> for this state.
     */
    private List<Button> buttonList;

    /**
     * The background's color.
     */
    private Color backgroundColor;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public LoadState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        backgroundColor = new Color(0, 0, 0, .8f);
        buttonList = new ArrayList<>();
        File saveDir = new File(SAVE_PATH);
        if (! saveDir.exists())
            saveDir.mkdir();
        for (File f : listSaveFile(SAVE_PATH)) { // TODO : Replace with real path
            Button b = new Button(gim, gs);
            b.setOnClick(() -> load(f.getPath()));
            b.setText(f.getName());
            buttonList.add(b);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(backgroundColor);
        sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
        sr.end();
        sb.begin();
        gs.getTitleFont().draw(sb, "Load", (int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin);
        sb.end();
        double alreadyUsed =  2 * topMargin + gs.getTitleFont().getLineHeight();
        int buttonHeight = (int)(gs.getNormalFont().getLineHeight() + 2 * topMargin);
        double maxDown = 2 * topMargin + gs.getTitleFont().getLineHeight() + buttonList.size() * (topMargin + buttonHeight) - MasterOfMonsGame.HEIGHT;
        if (mouseWheeled > maxDown)
            mouseWheeled = (int)maxDown;
        for (Button b : buttonList) {
            if (mouseWheeled < alreadyUsed - buttonHeight)
                b.draw(sb, new Point((int)leftMargin, (int)(MasterOfMonsGame.HEIGHT - alreadyUsed - buttonHeight + mouseWheeled)), new Point((int)(MasterOfMonsGame.WIDTH - 2 * leftMargin), buttonHeight));
            alreadyUsed += topMargin + buttonHeight;
        }
        Gdx.gl.glDisable(GL30.GL_BLEND);

    }

    @Override
    public void handleInput() {
        for (Button b : buttonList)
            b.handleInput();
        mouseWheeled += gim.getScrolledAmount() * 30;
        if (mouseWheeled < 0)
            mouseWheeled = 0;
    }

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
        for (Button b: buttonList)
            b.dispose();
    }

    /**
     * Return a list of all files "*.mom" in the <code>saveDirPath</code> directory. <code>null</code> if the directory doesn't exists/
     * @param saveDirPath The directory
     * @return A list of all files "*.mom" in the <code>saveDirPath</code> directory.
     */
    private File[] listSaveFile(String saveDirPath) {
        return new File(saveDirPath).listFiles((dir, name) -> name.endsWith(".mom"));
    }

    /**
     * Try to load the files and quit the state.
     * @param loadFilePath The load file's path.
     */
    private void load(String loadFilePath) {
        // TODO : call the load system
        gsm.removeFirstState();
    }
}
