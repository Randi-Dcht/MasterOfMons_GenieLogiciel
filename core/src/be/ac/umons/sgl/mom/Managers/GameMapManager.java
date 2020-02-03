package be.ac.umons.sgl.mom.Managers;

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
     * Toutes les cartes chargées.
     */
    protected Map<String, TiledMap> maps;
    /**
     * Tout les "renderer" des cartes déjà chargées.
     */
    protected Map<String, IsometricTiledMapRenderer> tiledMapsRenderer;
    /**
     * Toutes les cartes à charger.
     */
    protected List<String> mapsToLoad;
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

    /**
     * Crée un nouveau gestionnaire de carte.
     */
    public GameMapManager() {
        maps = new HashMap<>();
        tiledMapsRenderer = new HashMap<>();
        mapsToLoad = new LinkedList<>();
        mapLoader = new TmxMapLoader();
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
        if (loadIterator == null)
            loadIterator = mapsToLoad.iterator();
        if (! loadIterator.hasNext())
            return true;
        String path = loadIterator.next();
        boolean lastOne = ! loadIterator.hasNext();
        if (new File(path).exists()) {
            String name = new File(path).getName();
            TiledMap map = mapLoader.load(path);
            try {
                maps.put(name, map);
                tiledMapsRenderer.put(name, new IsometricTiledMapRenderer(map));
            } catch (UnsatisfiedLinkError e) { // Added for the tests.
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        mapsLoaded++;
        return lastOne;
    }

    /**
     * Ajoute les cartes <code>mapsPath</code> à la liste des cartes à charger.
     * @param mapsPath Le lien vers les cartes à charger.
     */
    public void addMapsToLoad(String... mapsPath) {
        Collections.addAll(mapsToLoad, mapsPath);
    }

    /**
     * Retourne la progression du chargement des cartes.
     * @return La progression du chargement des cartes.
     */
    public double getProgress() {
        return (double)mapsLoaded / mapsToLoad.size();
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
        for (TiledMap map : maps.values())
            map.dispose();
    }
}
