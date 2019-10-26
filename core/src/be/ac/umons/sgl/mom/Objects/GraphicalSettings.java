package be.ac.umons.sgl.mom.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class GraphicalSettings {
    private BitmapFont titleFont;
    private BitmapFont normalFont;
    private BitmapFont questFont;
    FreeTypeFontGenerator.FreeTypeFontParameter ftfp;

    public GraphicalSettings() {
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.color = Color.WHITE;
    }

    public void setNormalFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        normalFont = ftfg.generateFont(ftfp);
    }

    public BitmapFont getNormalFont() {
        return normalFont;
    }

    public void setTitleFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        titleFont = ftfg.generateFont(ftfp);
    }

    public BitmapFont getTitleFont() {
        return titleFont;
    }

    public void setQuestFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        questFont = ftfg.generateFont(ftfp);
    }

    public BitmapFont getQuestFont() {
        return questFont;
    }

    public void dispose() {
        if (titleFont != null)
            titleFont.dispose();
        if (normalFont != null)
            normalFont.dispose();
        if (questFont != null)
            questFont.dispose();
    }
}
