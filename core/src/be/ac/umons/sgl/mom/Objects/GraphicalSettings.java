package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Languages;
import be.ac.umons.sgl.mom.Helpers.StringHelper;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.io.File;
import java.util.Locale;
import java.util.Objects;

/**
 * Represent the graphical settings of the game.
 */
public class GraphicalSettings {
    /**
     * The font used to draw titles.
     */
    private BitmapFont titleFont;
    /**
     * The font used to draw text in general.
     */
    private BitmapFont normalFont;
    /**
     * The font used to draw quest's name.
     */
    private BitmapFont questFont;
    /**
     * The font used to draw small text.
     */
    private BitmapFont smallFont;
    /**
     * An asset manager to load assets.
     */
    private AssetManager assetManager; // TODO : Why did I put that here ?
    /**
     * A BitmapFont generator.
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp;

    /**
     * The bundle containing all the strings to use in the game in the chosen language.
     */
    private I18NBundle bundle;
    /**
     * If the map coordinates needs to be showed to the user.
     */
    private boolean showMapCoordinates = false;

    protected Color backgroundColor;

    public GraphicalSettings() {
        init();
    }

    /**
     * Initialize the graphical settings.
     */
    public void init() {
        assetManager = new AssetManager();
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.color = Color.WHITE;
        prepareAssetManagerForLoading();
        backgroundColor = StringHelper.getColorFromString(MasterOfMonsGame.settings.getBackgroundColor());
    }

    /**
     * Set the font used to draw small text.
     * @param fontPath The font's path.
     * @param size The size of the font.
     */
    public void setSmallFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        smallFont = ftfg.generateFont(ftfp);
    }

    /**
     * @return The font used to draw text smaller.
     */
    public BitmapFont getSmallFont() {
        return smallFont;
    }

    /**
     * Set the font used to draw text in general.
     * @param fontPath The font's path.
     * @param size The font's size.
     */
    public void setNormalFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        normalFont = ftfg.generateFont(ftfp);
    }

    /**
     * @return The font used to draw text in general.
     */
    public BitmapFont getNormalFont() {
        return normalFont;
    }

    /**
     * Set the font used to draw titles.
     * @param fontPath The font's path.
     * @param size The font's size.
     */
    public void setTitleFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        titleFont = ftfg.generateFont(ftfp);
    }

    /**
     * @return The font used to draw titles.
     */
    public BitmapFont getTitleFont() {
        return titleFont;
    }

    /**
     * Set the font used to draw quest's name.
     * @param fontPath The font's path.
     * @param size The font's size.
     */
    public void setQuestFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        questFont = ftfg.generateFont(ftfp);
    }

    /**
     * @return The font used to draw quest's name.
     */
    public BitmapFont getQuestFont() {
        return questFont;
    }

    /**
     * Prepare the asset managers to load every needed resources.
     */
    private void prepareAssetManagerForLoading() {
        File[] fi = Gdx.files.internal("Pictures/").file().listFiles(File::isDirectory);
        for (File folder : Objects.requireNonNull(Gdx.files.internal("Pictures/").file().listFiles(File::isDirectory))) {
            for (File f : Objects.requireNonNull(folder.listFiles(File::isFile))) {
                assetManager.load(f.getPath(), Texture.class);
            }
        }
        for (File f : Objects.requireNonNull(Gdx.files.internal("Pictures/").file().listFiles(File::isFile))) {
            assetManager.load(f.getPath(), Texture.class);
        }
    }

    /**
     * @return The asset manager.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Dispose every resources for this object.
     */
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

    /**
     * Add the given file to the AssetManager loading's list.
     * @param files The files to load.
     */
    @SuppressWarnings("unchecked")
    public void addFilesToLoad(LoadFile... files) {
        for (LoadFile f : files) {
            assetManager.load(f.file.getPath(), f.typeOfFile);
        }
    }

    /**
     * Return the string corresponding with the given id in the previously configured language (or the default one).
     * @param id The string's id.
     * @return The string corresponding with the given id in the previously configured language (or the default one).
     */
    public String getStringFromId(String id) {
        return bundle.get(id);
    }

    /**
     * Set the locale that need to be used and load the corresponding bundle.
     * @param loc The locale.
     */
    public void setLocale(Locale loc) {
        bundle = I18NBundle.createBundle(Gdx.files.internal("Conversations/Conversations"), loc);
    }

    /**
     * Set the language that need to be used and change the local.
     * @param lang The language that need to be used.
     */
    public void setLanguage(Languages lang) {
        String loc = lang.getLocale();
        setLocale(new Locale(loc));
    }

    /**
     * @return If the player's coordinates must be showed.
     */
    public boolean mustShowMapCoordinates() {
        return showMapCoordinates;
    }

    /**
     * Set if the player's coordinate must be showed.
     * @param showMapCoordinates If the player's coordinate must be showed.
     */
    public void setShowMapCoordinates(boolean showMapCoordinates) {
        this.showMapCoordinates = showMapCoordinates;
    }
}
