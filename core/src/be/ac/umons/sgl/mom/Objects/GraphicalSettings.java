package be.ac.umons.sgl.mom.Objects;

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
 * Représente les paramètres graphiques du jeu.
 */
public class GraphicalSettings {
    /**
     * La police d'écriture utilisée pour les titres.
     */
    private BitmapFont titleFont;
    /**
     * La police d'écriture utilisée pour le texte normal.
     */
    private BitmapFont normalFont;
    /**
     * La police d'écriture utilisée pour les quêtes.
     */
    private BitmapFont questFont;
    /**
     * La police d'écriture utilisée pour les textes plus petit.
     */
    private BitmapFont smallFont;
    /**
     * L'AssetManager (gestionnaire de ressources) du jeu.
     */
    private AssetManager assetManager;
    /**
     * Le générateur de BitmapFont afin d'éviter la pixelisation.
     */
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp;

    private Locale loc;

    private I18NBundle bundle;

    /**
     * Crée de nouveaux paramètres graphiques.
     */
    public GraphicalSettings() {
        init();
    }

    public void init() {
        assetManager = new AssetManager();
        ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ftfp.color = Color.WHITE;
        prepareAssetManagerForLoading();
        loc = Locale.ROOT;
        bundle = I18NBundle.createBundle(Gdx.files.internal("Conversations/Conversations"), loc); // TODO : Gdx.files NULL ???
    }

    /**
     * Défini la police d'écriture utilisée pour les textes plus petit.
     * @param fontPath Le lien vers la police d'écriture utilisée pour les textes plus petit.
     * @param size La taille de la police.
     */
    public void setSmallFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        smallFont = ftfg.generateFont(ftfp);
    }

    /**
     * Retourne la police d'écriture utilisée pour les textes plus petit.
     * @return La police d'écriture utilisée pour les textes plus petit.
     */
    public BitmapFont getSmallFont() {
        return smallFont;
    }

    /**
     * Défini la police d'écriture utilisée pour le texte normal.
     * @param fontPath Le lien vers la police d'écriture utilisée pour le texte normal.
     * @param size La taille de la police.
     */
    public void setNormalFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        normalFont = ftfg.generateFont(ftfp);
    }

    /**
     * Retourne la police d'écriture utilisée pour le texte normal.
     * @return La police d'écriture utilisée pour le texte normal.
     */
    public BitmapFont getNormalFont() {
        return normalFont;
    }

    /**
     * Défini la police d'écriture utilisée pour les titres.
     * @param fontPath Le lien vers la police d'écriture utilisée pour les titres.
     * @param size La taille de la police.
     */
    public void setTitleFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        titleFont = ftfg.generateFont(ftfp);
    }

    /**
     * Retourne la police d'écriture utilisée pour les titres.
     * @return La police d'écriture utilisée pour les titres.
     */
    public BitmapFont getTitleFont() {
        return titleFont;
    }

    /**
     * Défini la police d'écriture utilisée pour les quêtes.
     * @param fontPath Le lien vers la police d'écriture utilisée pour les quêtes.
     * @param size La taille de la police.
     */
    public void setQuestFont(String fontPath, int size) {
        FreeTypeFontGenerator ftfg = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        ftfp.size = size;
        questFont = ftfg.generateFont(ftfp);
    }

    /**
     * Retourne la police d'écriture utilisée pour les quêtes.
     * @return La police d'écriture utilisée pour les quêtes.
     */
    public BitmapFont getQuestFont() {
        return questFont;
    }

    /**
     * Initialise les fichiers que le gestionnaire de ressources devra charger durant l'écran de chargement.
     */
    private void prepareAssetManagerForLoading() {
        if (Gdx.files == null)
            return;
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
     * Retourne le gestionnaire de ressources.
     * @return Le gestionnaire de ressources.
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * Libère les ressources alloué lors de l'utilisation de l'état.
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

    public void addFilesToLoad(LoadFile... files) {
        for (LoadFile f : files) {
            assetManager.load(f.file.getPath(), f.typeOfFile);
        }
    }

    public String getStringFromId(String id) {
        return bundle.get(id);
    }

    public void setLocale(Locale loc) {
        this.loc = loc;
        bundle = I18NBundle.createBundle(Gdx.files.internal("Conversations/Conversations"), loc);
    }
}
