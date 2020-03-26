package be.ac.umons.mom.g02.GraphicalObjects.Controls;

import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.awt.*;

/**
 * A <code>TextBox</code> designed to select color.
 */
public class ColorSelector extends Control {

    /**
     * The <code>TextBox</code> for entering the code value.
     */
    protected TextBox tb;
    /**
     * Allow to draw shapes.
     */
    protected ShapeRenderer sr;
    /**
     * The selected color.
     */
    protected Color selectedColor;

    /**
     * @param gim The game's input manager.
     * @param gs The game's graphical settings.
     */
    public ColorSelector(GameInputManager gim, GraphicalSettings gs) {
        super(gim, gs);
        selectedColor = gs.getTransparentBackgroundColor();
        tb = new TextBox(gim, gs);
        tb.setAcceptOnlyHexadecimal(true);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * USES ONLY FOR TEST !
     */
    protected ColorSelector() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        super.draw(batch, pos, size);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(selectedColor);
        sr.ellipse(pos.x + leftMargin, pos.y + topMargin, size.y - 2 * topMargin, size.y - 2 * topMargin);
        sr.end();
        tb.draw(batch, new Point(pos.x + size.y - 2 * topMargin + 2 * leftMargin, pos.y), new Point(size.x - size.y + 2 * topMargin - 2 * leftMargin, size.y));
        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    @Override
    public void handleInput() {
        tb.handleInput();
        updateSelectedColor();
    }

    /**
     * Update the selected color with the entered code value if it's valid.
     */
    protected void updateSelectedColor() {
        if (tb.getText().length() == 6 || tb.getText().length() == 8) {
            selectedColor = StringHelper.getColorFromString(tb.getText());
        }
    }

    /**
     * Set the selected color.
     * @param selectedColor The selected color.
     */
    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        tb.setText(selectedColor.toString());
    }

    /**
     * @return The selected color.
     */
    public Color getSelectedColor() {
        return selectedColor;
    }

    @Override
    public void dispose() {
        sr.dispose();
        tb.dispose();
    }
}
