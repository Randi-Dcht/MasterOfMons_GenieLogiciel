package be.ac.umons.mom.g02.GameStates.Dialogs;

import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Events.Notifications.Answer;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Represent the in-game dialog with the player.
 */
public class InGameDialogState extends DialogState {

    /**
     * Animation for the text.
     */
    private StringAnimation sa;

    /**
     * Background's color
     */
    protected Color backgroundColor = gs.getTransparentBackgroundColor();

    /**
     *
     * @param gsm The GameStateManager to use.
     * @param gim The GameInputManager to use.
     * @param gs The GraphicalSettings to use.
     */
    public InGameDialogState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        sa.update(dt);
    }

    @Override
    public void draw() {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        float height = (float) MasterOfMonsGame.HEIGHT / 6;
        float rectY = (float)topMargin;
        sr.setColor(backgroundColor);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect((float)leftMargin, rectY, (float)(MasterOfMonsGame.WIDTH - 2 * leftMargin), height);
        sr.end();

        Set<String> strings = whenSelectedActions.keySet();
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getSmallFont(), getMaximumTextLength(strings.toArray(new String[0])));
        double width = gl.width + 2 * leftMargin;
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(sb, new Point((int)(MasterOfMonsGame.WIDTH - width - leftMargin), // pos.x
                    (int)(rectY + height + topMargin + (buttons.size() - 1 - i) * (gl.height + 2 * topMargin))), new Point( //pos.y
                    (int)width, //width
                    (int)(gl.height + 2 * topMargin))); // height
        }

        sb.begin();
        if (! sa.isFinished())
            gs.getSmallFont().draw(sb, StringHelper.adaptTextToWidth(gs.getSmallFont(), sa.getActual(), (int)(MasterOfMonsGame.WIDTH - 4 * leftMargin)), (float)(2 * leftMargin), (float)(rectY + height - topMargin));
        else
            gs.getSmallFont().draw(sb, text, (float)(2 * leftMargin), (float)(rectY + height - topMargin));


        sb.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        if (gim.isKey(Input.Keys.SPACE, KeyStatus.Pressed) || gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed))
            sa.finishNow();
        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed))
            Supervisor.getSupervisor().getEvent().notify(new Answer("ESC"));
    }

    @Override
    public void setText(String text) {
        text = gs.getStringFromId(text);
        this.text = StringHelper.adaptTextToWidth(gs.getSmallFont(), text, (int)(MasterOfMonsGame.WIDTH - 4 * leftMargin));
        sa = new StringAnimation(text, 20 * text.length());
        whenSelectedActions = new HashMap<>();
        buttons = new ArrayList<>();
        selectedButtonIndex = 0;
    }

    /**
     * Return the maximal length found in a list of String.
     * @param strings The list of String.
     * @return The maximal length found.
     */
    public String getMaximumTextLength(String[] strings) {
        String max = "";
        for (String s : strings) {
            if (s.length() > max.length())
                max = s;
        }
        return max;
    }
}
