package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Testing class for <code>AnimationManager</code>
 */
public class AnimationManagerTest {

    AnimationManager am;

    @BeforeEach
    public void init() {
        am = new AnimationManager();
        am.animations = new HashMap<>();
    }

    /**
     * Test if the update method works as expected
     */
    @Test
    public void updateTest() {
        DoubleAnimation da = new DoubleAnimation(0,1,1000);
        DoubleAnimation da2 = new DoubleAnimation(1,1,1000);
        am.animations.put("Test1", da);
        am.animations.put("Test2", da2);
        am.update(.5f);
        Assertions.assertTrue(am.animations.containsKey("Test1"));
        Assertions.assertFalse(am.animations.containsKey("Test2"));
        am.update(.5f);
        Assertions.assertFalse(am.animations.containsKey("Test1"));
        Assertions.assertFalse(am.animations.containsKey("Test2"));
    }


}
