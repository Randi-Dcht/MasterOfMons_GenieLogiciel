package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.GameKeys;
import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class InGameMenuState extends MenuState {

    ShapeRenderer sr;

    public InGameMenuState(GameStateManager gsm, GameInputManager gim) {
        super(gsm, gim);
    }

    @Override
    public void init() {
        super.init();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(sb.getProjectionMatrix());
        sr.setAutoShapeType(true);
//        sb.setColor(Color.valueOf("FF212121"));
        topMargin = .1;
        betweenItemMargin = .01;
        menuItems = new MenuItem[] { new MenuItem("Master Of Mons", MenuItemType.Title, false),
                new MenuItem("Continue"),
                new MenuItem("Save"),
                new MenuItem("Load"),
                new MenuItem("Quick Save"),
                new MenuItem("Quick Load"),
                new MenuItem("Settings"),
                new MenuItem("Quit")};
    }

    // https://gamedev.stackexchange.com/questions/67817/how-do-i-render-a-png-with-transparency-in-libgdx
    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        sr.rect(0, 0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
        sr.end();
        super.draw();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        super.handleInput();

        if (gim.isKey(GameKeys.Enter, KeyStatus.Pressed)) {
            switch (selectedItem) {
                case 1:
                    gsm.removeFirstState();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    Gdx.app.exit();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        sr.dispose();
    }
}
