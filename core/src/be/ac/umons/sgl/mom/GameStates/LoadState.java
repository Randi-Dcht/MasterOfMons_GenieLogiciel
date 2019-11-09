package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.GraphicalObjects.Button;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadState extends GameState {
    protected final String SAVE_PATH = "D:\\Users\\Guillaume\\Documents\\Test\\MOM"; // TODO : Define it itself

    private ShapeRenderer sr;
    private SpriteBatch sb;

    private double topMargin;
    private double leftMargin;
    private int mouseWheeled = 0;

    private List<Button> buttonList;

    public LoadState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        buttonList = new ArrayList<>();
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
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
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
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
                b.draw(sb, (int)leftMargin, (int)(MasterOfMonsGame.HEIGHT - alreadyUsed - buttonHeight + mouseWheeled), (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin), buttonHeight);
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
    }

    private File[] listSaveFile(String saveDirPath) {
        return new File(saveDirPath).listFiles((dir, name) -> name.endsWith(".mom"));
    }

    private void load(String loadFilePath) {
        // TODO : call the load system
        gsm.removeFirstState();
    }
}
