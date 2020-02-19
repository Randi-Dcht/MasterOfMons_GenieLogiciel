package be.ac.umons.sgl.mom.GraphicalObjects.Controls;

import be.ac.umons.sgl.mom.Helpers.StringHelper;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.xml.bind.DatatypeConverter;
import java.awt.*;

public class ColorSelector extends Control {

    protected TextBox tb;
    protected ShapeRenderer sr;
    protected Color selectedColor;

    public ColorSelector(GameInputManager gim, GraphicalSettings gs) { // TODO TESTS
        super(gim, gs);
        selectedColor = gs.getTransparentBackgroundColor();
        tb = new TextBox(gim, gs);
        tb.setAcceptOnlyHexadecimal(true);
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    protected ColorSelector() {}

    @Override
    public void draw(Batch batch, Point pos, Point size) {
        super.draw(batch, pos, size);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(selectedColor);
        sr.ellipse(pos.x + leftMargin, pos.y + topMargin, size.y - 2 * topMargin, size.y - 2 * topMargin);
        sr.end();
        tb.draw(batch, new Point(pos.x + size.y - 2 * topMargin + leftMargin, pos.y), new Point(size.x - size.y + 2 * topMargin - leftMargin, size.y));
    }

    @Override
    public void handleInput() {
        tb.handleInput();
        updateSelectedColor();
    }

    protected void updateSelectedColor() {
        if (tb.getText().length() == 6 || tb.getText().length() == 8) {
            selectedColor = StringHelper.getColorFromString(tb.getText());
        }
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    @Override
    public void dispose() {
        sr.dispose();
        tb.dispose();
    }
}
