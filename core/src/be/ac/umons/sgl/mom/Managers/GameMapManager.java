package be.ac.umons.sgl.mom.Managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

import java.io.File;
import java.util.*;

/**
 * Représente le gestionnaire de carte.
 */
public class GameMapManager {
    /**
     * Tout les "renderer" des cartes déjà chargées.
     */
    protected Map<String, IsometricTiledMapRenderer> tiledMapsRenderer;
    /**
     * L'itérateur indiquant la position actuelle des cartes déjà chargées.
     */
    protected Iterator<String> loadIterator;
    /**
     * Le chargeur de carte.
     */
    protected TmxMapLoader mapLoader;
    /**
     * Le renderer a utilisé à ce stade-ci.
     */
    protected IsometricTiledMapRenderer actualItmr;
    /**
     * La caméra sur laquelle afficher la carte.
     */
    protected OrthographicCamera view;
    /**
     * Le nombre de cartes déjà chargées.
     */
    protected int mapsLoaded = 0;

    protected AssetManager am;

    /**
     * Crée un nouveau gestionnaire de carte.
     */
    public GameMapManager() {
        tiledMapsRenderer = new HashMap<>();
        mapLoader = new TmxMapLoader(new LocalFileHandleResolver());
        am = new AssetManager();
        am.setLoader(TiledMap.class, mapLoader);
    }

    /**
     * Défini la carte a dessiner.
     * @param mapName Le nom de la carte a dessiner.
     */
    public void setMap(String mapName) {
        if (tiledMapsRenderer.containsKey(mapName))
            actualItmr = tiledMapsRenderer.get(mapName);
    }

    /**
     * Dessine la carte actuelle.
     */
    public void render() {
        if (actualItmr != null) {
            actualItmr.setView(view);
            actualItmr.render();
        }
    }

    /**
     * Défini la caméra sur laquelle afficher la carte.
     * @param cam La caméra sur laquelle afficher la carte.
     */
    public void setView(OrthographicCamera cam) {
        view = cam;
    }

    /**
     * Charge la carte suivante, sauf si toutes les cartes ont été chargées.
     * @return Si toutes les cartes ont été chargées.
     */
    public boolean loadNextMap() {
        boolean finish = am.update();
        if (finish) {
            for (String name : am.getAssetNames()) {
                if (am.getAssetType(name).equals(TiledMap.class))
                    tiledMapsRenderer.put(name, new IsometricTiledMapRenderer(am.get(name)));
            }
        }
        return finish;
    }

    /**
     * Ajoute les cartes <code>mapsPath</code> à la liste des cartes à charger.
     * @param mapsPath Le lien vers les cartes à charger.
     */
    public void addMapsToLoad(String... mapsPath) {
        for (String map : mapsPath)
            am.load(map, TiledMap.class);
    }

    /**
     * Retourne la progression du chargement des cartes.
     * @return La progression du chargement des cartes.
     */
    public double getProgress() {
        return am.getProgress();
    }

    /**
     * Retourne la carte actuelle.
     * @return La carte actuelle.
     */
    public TiledMap getActualMap() {
        return actualItmr.getMap();
    }

    /**
     * Libère les ressources allouées lors de l'utilisation du gestionnaire.
     */
    public void dispose() {
        for (IsometricTiledMapRenderer itmr : tiledMapsRenderer.values())
            itmr.dispose();
        am.dispose();
    }
}
