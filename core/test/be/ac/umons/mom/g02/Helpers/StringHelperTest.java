package be.ac.umons.mom.g02.Helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringHelperTest {

    /**
     * Test if the text is converted to the expected int values
     */
    @Test
    public void textToHexTest() {
        int[] i = StringHelper.decodeHexStringToInt("FFFFFF");
        Assertions.assertEquals(3, i.length);
        Assertions.assertEquals(i[0], 255);
        Assertions.assertEquals(i[1], 255);
        Assertions.assertEquals(i[2], 255);
        Assertions.assertThrows(IllegalArgumentException.class, () -> StringHelper.decodeHexStringToInt("FFFFF"));
        i = StringHelper.decodeHexStringToInt("007FFF");
        Assertions.assertEquals(3, i.length);
        Assertions.assertEquals(i[0], 0);
        Assertions.assertEquals(i[1], 127);
        Assertions.assertEquals(i[2], 255);
    }

}
