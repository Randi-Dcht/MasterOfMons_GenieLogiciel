package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Animations.Animation;

import java.util.*;

/**
 * Gère toutes les animations du jeu et les mets à jour.
 * @author Guillaume Cardoen
 */
public class AnimationManager {

    public static AnimationManager instance;

    public static AnimationManager getInstance() {
        if (instance == null)
            instance = new AnimationManager();
        return instance;
    }

    /**
     * Le lien entre le nom d'une animation et l'animation en elle-même.
     */
    protected Map<String, Animation> animations;

    /**
     * Crée un nouveau gestionnaire d'animation.
     */
    protected AnimationManager() {
        animations = new HashMap<>();
    }

    /**
     * Ajoute l'animation <code>anim</code> sous le nom <code>animName</code>
     * @param animName Le nom de l'animation.
     * @param anim L'animation à ajouter.
     */
    public void addAnAnimation(String animName, Animation anim) {
        animations.put(animName, anim);
    }

    /**
     * Retourne l'animation avec le nom <code>animName</code>
     * @param animName Le nom de l'animation.
     * @return L'animation avec le nom <code>animName</code>
     */
    public Animation getAnAnimation(String animName) {
        return animations.get(animName);
    }

    /**
     * Mets à jour toutes les animations en fonction de <code>dt</code>.
     * @param dt Le temps entre l'appel précédent de cette fonction et cet appel-ci.
     */
    public void update(float dt) {
        Animation[] animationArray = animations.values().toArray(new Animation[0]);
        for (Animation animation : animationArray)
            animation.update(dt);

        for (Iterator<String> it = animations.keySet().iterator(); it.hasNext();) { // Done in 2 times because of ConcurrentModificationException
            String key = it.next();
            Animation a = animations.get(key);
            if (a.isFinished())
                it.remove();
        }
    }

}
