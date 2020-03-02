package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Animations.DoubleAnimation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Test si AnimationManager se comporte comme il devrait.
 */
public class AnimationManagerTest extends AnimationManager {

    @BeforeEach
    public void init() {
        animations = new HashMap<>();
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
