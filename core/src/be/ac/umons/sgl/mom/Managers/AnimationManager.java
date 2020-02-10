package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Animations.Animation;

import java.util.*;

/**
 * Manage all animations in the game.
 * @author Guillaume Cardoen
 */
public class AnimationManager {

    /**
     * The instance of AnimationManager.
     */
    public static AnimationManager instance;

    /**
     * @return The instance of AnimationManager.
     */
    public static AnimationManager getInstance() {
        if (instance == null)
            instance = new AnimationManager();
        return instance;
    }

    /**
     * The link between the animation's name and the Animation object.
     */
    protected Map<String, Animation> animations;

    protected AnimationManager() {
        animations = new HashMap<>();
    }

    /**
     * Add the animation <code>anim</code> under the name <code>animName</code>
     * @param animName Animation's name.
     * @param anim Animation to add.
     */
    public void addAnAnimation(String animName, Animation anim) {
        animations.put(animName, anim);
    }

    /**
     * Update all the animations with the given delta-time.
     * @param dt The delta-time.
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
