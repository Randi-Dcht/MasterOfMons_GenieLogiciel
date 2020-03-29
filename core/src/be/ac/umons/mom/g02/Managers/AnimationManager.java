package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Animations.Animation;

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
     * Add the animation <code>anim</code> under a random name
     * @param anim Animation to add.
     */
    public void addAnAnimation(Animation anim) {
        String name = "a";
        Random rand = new Random();
        while (animations.containsKey(name)) {
            name += (char)('a' + rand.nextInt('z'));
        }
        animations.put(name, anim);
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

    /**
     * Remove the animation if it exists.
     * @param animationName The name given when the animation was added.
     */
    public void remove(String animationName) {
        animations.remove(animationName);
    }

    /**
     * @param animationName The name given when the animation was added.
     * @return The animation with the given name.
     */
    public Animation get(String animationName) {
        return animations.get(animationName);
    }

}
