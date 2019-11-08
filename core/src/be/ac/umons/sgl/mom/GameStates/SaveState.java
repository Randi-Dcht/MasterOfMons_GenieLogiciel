package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.IntStream;

public class SaveState extends GameState {
    protected final String SAVE_STR = "Save";

    protected ShapeRenderer sr;
    protected SpriteBatch sb;

    protected int topMargin;
    protected int leftMargin;
    protected String actualName;

    public SaveState(GameStateManager gsm, GameInputManager gim) {
        super(gsm, gim);
    }

    // https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
    @Override
    public void init() {
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        topMargin = MasterOfMonsGame.HEIGHT / 100;
        leftMargin = MasterOfMonsGame.WIDTH / 100;
        actualName = String.format("MOM - %s", new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date()));
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
        float fontLineHeight = MasterOfMonsGame.gs.getNormalFont().getLineHeight();
        sr.rect(quartWidth,  halfHeight - quartHeight / 2, halfWidth, quartHeight); // TODO : Variables would be great here and there
        sr.rect(quartWidth + leftMargin, halfHeight + quartHeight / 2 - 2 * fontLineHeight - 3 * topMargin, halfWidth - 2 * leftMargin, fontLineHeight + 2 * topMargin);
        sr.end();
        sb.begin();
        MasterOfMonsGame.gs.getNormalFont().draw(sb, SAVE_STR, halfWidth - SAVE_STR.length() * MasterOfMonsGame.gs.getNormalFont().getXHeight() / 2, halfHeight + quartHeight / 2 - topMargin);
        MasterOfMonsGame.gs.getNormalFont().draw(sb, actualName + ".mom", quartWidth + 2 * leftMargin, halfHeight + quartHeight / 2 - 2 * fontLineHeight - 3 * topMargin + MasterOfMonsGame.gs.getNormalFont().getLineHeight());
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    // https://stackoverflow.com/a/44956044
    @Override
    public void handleInput() {
        boolean upper = false;
        if (gim.isKey(Input.Keys.SHIFT_LEFT | Input.Keys.SHIFT_RIGHT, KeyStatus.Pressed))
            upper = true;
        for (int key : IntStream.rangeClosed(Input.Keys.A, Input.Keys.Z).toArray())
            if (gim.isKey(key, KeyStatus.Pressed))
                actualName += upper ? Input.Keys.toString(key) : Input.Keys.toString(key).toLowerCase();
        if (gim.isKey(Input.Keys.BACKSPACE, KeyStatus.Pressed))
            actualName = actualName.substring(0, actualName.length() - 1);
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed)) {
            actualName += ".mom";
            // TODO : Call the save object and save the essential parts of the game.
            gsm.removeFirstState();
        }
    }

    @Override
    public void dispose() {

    }
}
