package be.ac.umons.mom.g02.GraphicalObjects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import be.ac.umons.mom.g02.Quests.Quest;

import java.awt.*;

/**
 * Cette classe représente les tests de la classe QuestShower.
 */
public class QuestShowerTest extends QuestShower {

    /**
     * Vérifie si getMaximumQuestWidth retourne la valeur attendue.
     */
    @Test
    public void questNameWidthTest() {
        Quest q2 = new Quest() {
            @Override
            public double getProgress() {
                return 0;
            }

            @Override
            public double getAdvancement() {
                return 0;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public Quest[] getSubQuests() {
                return new Quest[0];
            }

            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public int getTotalSubQuestsNumber() {
                return 0;
            }

            @Override
            public void addProgress(double many) {

            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
        Quest q1 = new Quest() {
            @Override
            public double getProgress() {
                return 0;
            }

            @Override
            public double getAdvancement() {
                return 0;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public Quest[] getSubQuests() {
                return new Quest[] {q2};
            }

            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public int getTotalSubQuestsNumber() {
                return 0;
            }

            @Override
            public void addProgress(double many) {}

            @Override
            public boolean isFinished() {
                return false;
            }
        };
        Assertions.assertEquals(4 + 4 + BETWEEN_CIRCLE_AND_TEXT_MARGIN, getMaximumQuestNameWidth(q1, 4));
        Assertions.assertEquals(4, getMaximumQuestNameWidth(q2, 0));
    }

    /**
     * Overrided to avoid an error during the test. It doesn't change anything for the test.
     * @param text The text
     * @return The length of the text.
     */
    @Override
    protected Point getTextSize(String text) {
        return new Point(text.length(), 10);
    }
}
