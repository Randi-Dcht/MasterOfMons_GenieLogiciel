package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The state where the user can choose to save the actual game.
 * @author Guillaume Cardoen
 */
public class SaveState extends GameState {
    /**
     * The extension used for saving in a file.
     */
    protected final String SAVE_FILE_EXTENSION = ".mom";

    /**
     * Allow to draw.
     */
    protected SpriteBatch sb;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;

    /**
     * The <code>TextBox</code> corresponding of the save's name.
     */
    protected TextBox nameBox;
    /**
     * The save button.
     */
    protected Button saveButton;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings
     */
    public SaveState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    /**
     * Initialize the state.
     * A part of this code is from (https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/)
     */
    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        nameBox = new TextBox(gim, gs);
        nameBox.setText(String.format("MOM - %s", new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(new Date())));
        nameBox.setSuffix(SAVE_FILE_EXTENSION);
        saveButton = new Button(gim, gs);
        saveButton.setText(gs.getStringFromId("save"));
        saveButton.setOnClick(this::save);
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
        gs.getNormalFont().draw(sb, gs.getStringFromId("save"), (int)(halfWidth - gs.getStringFromId("save").length() * gs.getNormalFont().getXHeight() / 2), (int)(halfHeight + quartHeight / 2 - topMargin));
        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
        int nameBoxY = (int)(halfHeight + quartHeight / 2 - 2 * fontLineHeight - 3 * topMargin);
        int controlHeight = (int)(fontLineHeight + 2 * topMargin);
        int controlWidth = (int)(halfWidth - 2 * leftMargin);
        nameBox.draw(sb, new Point((int)(quartWidth + leftMargin), nameBoxY), new Point(controlWidth, controlHeight));
        saveButton.draw(sb, new Point((int)(quartWidth + leftMargin), (int)(nameBoxY - controlHeight - topMargin)), new Point(controlWidth, controlHeight));
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
            save();
        else if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
        nameBox.handleInput();
        saveButton.handleInput();
    }

    @Override
    public void dispose() {
        sb.dispose();
        sr.dispose();
    }

    /**
     * Save the actual game and quit the state.
     */
    private void save() {
        // TODO : Call the save object and save the essential parts of the game. (nameBox.getText() will returns the choosed name + ".mom")
        gsm.removeFirstState();
    }
}
