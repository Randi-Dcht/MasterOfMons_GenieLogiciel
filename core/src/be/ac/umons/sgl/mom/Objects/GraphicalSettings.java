package be.ac.umons.sgl.mom.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.io.File;
import java.io.FileFilter;
import java.util.Objects;

public class GraphicalSettings {
    private BitmapFont titleFont;
    private BitmapFont normalFont;
    private BitmapFont questFont;
    private AssetManager assetManager;
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp;

    public GraphicalSettings() {
        assetManager = new AssetManager();
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.color = Color.WHITE;
        prepareAssetManagerForLoading();
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

    protected void prepareAssetManagerForLoading() {
        for (File folder : Objects.requireNonNull(Gdx.files.internal("Pictures/").file().listFiles(File::isDirectory))) {
            for (File f : Objects.requireNonNull(folder.listFiles(File::isFile))) {
                assetManager.load(f.getPath(), Texture.class);
            }
        }
        for (File f : Objects.requireNonNull(Gdx.files.internal("Pictures/").file().listFiles(File::isFile))) {
            assetManager.load(f.getPath(), Texture.class);
        }
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose() {
        if (titleFont != null)
            titleFont.dispose();
        if (normalFont != null)
            normalFont.dispose();
        if (questFont != null)
            questFont.dispose();
        if (assetManager != null)
            assetManager.dispose();
    }
}
