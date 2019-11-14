package be.ac.umons.sgl.mom.Managers;

import be.ac.umons.sgl.mom.Animations.Animation;
import be.ac.umons.sgl.mom.Animations.DoubleAnimation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Test si AnimationManager se comporte comme il devrait.
 */
public class AnimationManagerTest {
    /**
     * Le lien entre le nom d'une animation et l'animation en elle-même.
     */
    private Map<String, Animation> animations;

    @BeforeEach
    public void init() {
        animations = new HashMap<>();
    }

    /**
     * Mets à jour toutes les animations en fonction de <code>dt</code>.
     * @param dt Le temps entre l'appel précédent de cette fonction et cet appel-ci.
     */
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

    @Test
    public void updateTest() {
        DoubleAnimation da = new DoubleAnimation(0,1,1000);
        DoubleAnimation da2 = new DoubleAnimation(1,1,1000);
        animations.put("Test1", da);
        animations.put("Test2", da2);
        update(.5f);
        Assertions.assertTrue(animations.containsKey("Test1"));
        Assertions.assertFalse(animations.containsKey("Test2"));
        update(.5f);
        Assertions.assertFalse(animations.containsKey("Test1"));
        Assertions.assertFalse(animations.containsKey("Test2"));
    }


}
