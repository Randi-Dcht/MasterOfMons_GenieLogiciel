package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Interfaces.Animation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnimationManager {
    private Map<String, Animation> animations;

    public AnimationManager() {
        animations = new HashMap<>();
    }

    public void addAnAnimation(String animName, Animation anim) {
        animations.put(animName, anim);
    }

    public Animation getAnAnimation(String animName) {
        return animations.get(animName);
    }

    public void update(float dt) {
        for (Animation a : animations.values())
            a.update(dt);

        for (Iterator<String> it = animations.keySet().iterator(); it.hasNext();) { // Done in 2 times because of ConcurrentModificationException
            String key = it.next();
            Animation a = animations.get(key);
            if (a.isFinished())
                it.remove();
        }
    }

}
