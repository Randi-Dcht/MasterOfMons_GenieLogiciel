package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveState extends GameState {
    protected final String SAVE_STR = "Save";

    protected ShapeRenderer sr;
    protected SpriteBatch sb;

    protected int topMargin;
    protected int leftMargin;
    protected TextBox nameBox;

    public SaveState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    // https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
    @Override
    public void init() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        nameBox = new TextBox(gim, gs);
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        nameBox.setText(String.format("MOM - %s", new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date())));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() { // TODO : Implements and draw the "Save" button.
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        float halfHeight = (float)MasterOfMonsGame.HEIGHT / 2;
        float quartHeight = (float)MasterOfMonsGame.HEIGHT / 4;
        float halfWidth = (float)MasterOfMonsGame.WIDTH / 2;
        float quartWidth = (float)MasterOfMonsGame.WIDTH / 4;
        float fontLineHeight = gs.getNormalFont().getLineHeight();
        sr.rect(quartWidth,  halfHeight - quartHeight / 2, halfWidth, quartHeight); // TODO : Variables would be great here and there
        sr.end();
        sb.begin();
        gs.getNormalFont().draw(sb, SAVE_STR, halfWidth - SAVE_STR.length() * gs.getNormalFont().getXHeight() / 2, halfHeight + quartHeight / 2 - topMargin);
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        int nameBoxY = (int)(halfHeight + quartHeight / 2 - 2 * fontLineHeight - 3 * topMargin);
        int controlHeight = (int)(fontLineHeight + 2 * topMargin);
        int controlWidth = (int)(halfWidth - 2 * leftMargin);
        nameBox.draw(sb, (int)(quartWidth + leftMargin), nameBoxY, controlWidth, controlHeight);
    }

    // https://stackoverflow.com/a/44956044
    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed)) {
            // TODO : Call the save object and save the essential parts of the game.
            gsm.removeFirstState();
        }
        nameBox.handleInput();
    }

    @Override
    public void dispose() {

    }
}
