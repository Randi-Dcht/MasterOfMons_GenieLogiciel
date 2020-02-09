package be.ac.umons.sgl.mom.GameStates.Dialogs;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract class representing a dialog between the game and the user.
 */
public abstract class DialogState extends GameState {
    /**
     * The text to show.
     */
    protected String text = "";
    /**
     * Which answer is selected.
     */
    protected String selected = null;
    /**
     * What to do when a answer is selected by the user.
     */
    protected HashMap<String, Runnable> whenSelectedActions;
    /**
     * Allow to draw shape.
     */
    protected ShapeRenderer sr;
    /**
     * Allow to draw.
     */
    protected SpriteBatch sb;

    /**
     * A list of buttons created by this state.
     */
    protected List<Button> buttons;
    /**
     * An index of the selected item.
     */
    protected int selectedButtonIndex = 0;

    /**
     * Create a new dialog.
     * @param gsm Game's state manager
     * @param gim Game's input manager
     * @param gs Game's graphical settings
     */
    public DialogState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        whenSelectedActions = new HashMap<>();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        sb = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            gsm.removeFirstState();
        for (Button b: buttons)
            b.handleInput();
    }

    @Override
    public void dispose() {
        sr.dispose();
        sb.dispose();
    }

    /**
     * Add an answer to the dialog
     * @param answer The answer to add
     */
    public void addAnswer(String... answer) {
        for (String s : answer)
            addAnswer(s, null);
    }

    /**
     * Add an answer and set the action to do if this answer is selected.
     * @param answer The answer
     * @param run The action
     */
    public void addAnswer(String answer, Runnable run) {
        whenSelectedActions.put(answer, run);
        Button b = new Button(gim, gs);
        b.setText(answer);
        b.setOnClick(run);
        b.setFont(gs.getSmallFont());
        buttons.add(b);
        buttons.get(0).setSelected(true);
    }

    /**
     * @return The user's selection.
     */
    public String getResult() {
        return selected;
    }

    /**
     * Set the action to do if <code>answer</code> is selected.
     * @param answer The answer associated
     * @param run The action to do.
     */
    public void setWhenSelected(String answer, Runnable run) {
        whenSelectedActions.put(answer, run);
    }

    /**
     * Set the text to show.
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Add '-' or begins a new line when necessary. The returned String has a width of <code>width</code> with the font <code>font</code>.
     * @param font The font associated with the text.
     * @param text The text
     * @param width The maximum width.
     * @return A String with a width of <code>width</code> with the font <code>font</code>.
     */
    public String adaptTextToWidth(BitmapFont font, String text, int width) {
        StringBuilder res = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "A");
        double charWidth = layout.width;
        for (String s : text.split(" ")) {
            layout.setText(font, s);
            if (layout.width > width) {
                while (s.length() > 0) {
                    int charsNbr = (int)(width / charWidth) - 1 - tmp.length(); // -1 for -
                    if (charsNbr > s.length())
                        charsNbr = s.length();
                    res.append(s, 0, charsNbr);
                    res.append("-\n");
                    tmp = new StringBuilder();
                    s = s.substring(charsNbr);
                }
                continue;
            }

            tmp.append(s);
            tmp.append(" ");
            layout.setText(font, tmp.toString());
            if (layout.width > width) {
                res.append('\n');
                res.append(s);
                res.append(" ");
                tmp = new StringBuilder();
                tmp.append(s);
                tmp.append(" ");
            } else {
                res.append(s);
                res.append(" ");
            }
        }
        return res.toString();
    }
}
