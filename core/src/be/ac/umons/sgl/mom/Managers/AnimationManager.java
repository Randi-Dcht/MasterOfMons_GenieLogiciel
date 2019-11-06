package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Interfaces.Animation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnimationManager {
    protected Map<String, Animation> animationsList;

    public AnimationManager() {
        animationsList = new HashMap<>();
    }

    public void addAnAnimation(String animName, Animation anim) {
        animationsList.put(animName, anim);
    }

    public void update(float dt) {
        for (Animation a : animationsList.values())
            a.update(dt);

        for (Iterator<String> it = animationsList.keySet().iterator(); it.hasNext();) { // Done in 2 times because of ConcurrentModificationException
            String key = it.next();
            Animation a = animationsList.get(key);
            if (a.isFinished())
                it.remove();
        }
    }

}
