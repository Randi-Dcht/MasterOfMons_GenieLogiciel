package be.ac.umons.mom.g02.GameStates.Dialogs;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
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
     * What to do when an answer is selected by the user.
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
     * If the state must be removed as soon as the answer was given (enter was pressed).
     */
    protected boolean mustQuitWhenAnswered = true;

    /**
     * Create a new dialog.
     * @param gs Game's graphical settings
     */
    public DialogState(GraphicalSettings gs) {
        super(gs);
        whenSelectedActions = new HashMap<>();
        buttons = new ArrayList<>();
    }

    @Override
    public void init() {
        super.init();
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

        if (!buttons.isEmpty()) {
            if (gim.isKey(Input.Keys.UP, KeyStatus.Pressed)) {
                buttons.get(selectedButtonIndex).setSelected(false);
                selectedButtonIndex--;
                if (selectedButtonIndex < 0)
                    selectedButtonIndex = buttons.size() - 1;
                buttons.get(selectedButtonIndex).setSelected(true);
            }
            if (gim.isKey(Input.Keys.DOWN, KeyStatus.Pressed)) {
                buttons.get(selectedButtonIndex).setSelected(false);
                selectedButtonIndex++;
                if (selectedButtonIndex >= buttons.size())
                    selectedButtonIndex = 0;
                buttons.get(selectedButtonIndex).setSelected(true);
            }
            if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed)) {
                buttons.get(selectedButtonIndex).getOnClick().run();
            }
        }
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
        addNonInternationalizedAnswer(gs.getStringFromId(answer), run);
    }
    /**
     * Add an answer and set the action to do if this answer is selected.
     * @param answer The answer
     * @param run The action
     */
    public void addNonInternationalizedAnswer(String answer, Runnable run) {
        whenSelectedActions.put(answer, run);
        Button b = new Button(gim, gs);
        b.setText(answer);
        b.setOnClick(() -> {
            if (run != null)
                run.run();
            if (mustQuitWhenAnswered)
                gsm.removeFirstStateFromStack();
        });
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
        this.text = StringHelper.adaptTextToWidth(gs.getNormalFont(), text, (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
        selectedButtonIndex = 0;
    }

    /**
     * @param mustQuitWhenAnswered If the state must be quit as soon as an answer was given.
     */
    public void setMustQuitWhenAnswered(boolean mustQuitWhenAnswered) {
        this.mustQuitWhenAnswered = mustQuitWhenAnswered;
    }
}
