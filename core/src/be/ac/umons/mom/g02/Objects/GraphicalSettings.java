package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Helpers.FileHelper;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Enums.Languages;
import be.ac.umons.mom.g02.Managers.GameColorManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.io.File;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.logging.FileHandler;

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
    private AssetManager assetManager;
    /**
     * A BitmapFont generator.
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp;

    /**
     * The bundle containing all the strings to use in the game in the chosen language.
     */
    private static I18NBundle bundle;
    /**
     * If the map coordinates needs to be showed to the user.
     */
    private boolean showMapCoordinates = false;

    GameColorManager gcm;

    public GraphicalSettings() {
        init();
    }

    /**
     * Initialize the graphical settings.
     */
    public void init() {
        gcm = GameColorManager.getInstance();
        assetManager = new AssetManager();
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.color = Color.WHITE;
        refreshColors();
    }

    /**
     * Refresh all the colors by taking the one in the settings.
     */
    public void refreshColors() {
        if (smallFont != null)
            smallFont.setColor(gcm.getColorFor("foreground"));
        if (normalFont != null)
            normalFont.setColor(gcm.getColorFor("foreground"));
        if (titleFont != null)
            titleFont.setColor(gcm.getColorFor("foreground"));
        if (questFont != null)
            questFont.setColor(gcm.getColorFor("foreground"));
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
        smallFont.setColor(gcm.getColorFor("foreground"));
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
        normalFont.setColor(gcm.getColorFor("foreground"));
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
        titleFont.setColor(gcm.getColorFor("foreground"));
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
        questFont.setColor(gcm.getColorFor("foreground"));
    }

    /**
     * @return The font used to draw quest's name.
     */
    public BitmapFont getQuestFont() {
        return questFont;
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
            assetManager.load(f.file.path(), f.getTypeOfFile());
        }
    }

    /**
     * Return the string corresponding with the given id in the previously configured language (or the default one).
     * @param id The string's id.
     * @return The string corresponding with the given id in the previously configured language (or the default one).
     */
    public static String getStringFromId(String id) {
        try {
            return bundle.get(id);
        } catch (MissingResourceException e) {
            Gdx.app.error("GraphicalSettings", "Bundle key not found", e);
            return "Error";
        }
    }

    /**
     * Set the locale that need to be used and load the corresponding bundle.
     * @param loc The locale.
     */
    public static void setLocale(Locale loc) {
        bundle = I18NBundle.createBundle(Gdx.files.internal("Conversations/Conversations"), loc);
    }

    /**
     * Set the language that need to be used and change the local.
     * @param lang The language that need to be used.
     */
    public static void setLanguage(Languages lang) {
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
