package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.Quest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Cette classe représente les tests de la classe QuestShower.
 */
public class QuestShowerTest {
    /**
     * La marge de décalage horizontale entre une quête et sa sous-quête.
     */
    public static final int BETWEEN_QUEST_MARGIN_WIDTH = 2;

    /**
     * Retourne la taille hozizontale maximale qui sera utilisée par l'affichage de la quête <code>mainQuest</code> et de ces sous-quêtes.
     * @param mainQuest La quête principale.
     * @param defaultMargin La marge de départ.
     * @return La taille hozizontale maximale qui sera utilisée par l'affichage d'une quête <code>mainQuest</code> et de ces sous-quêtes.
     */
    protected int getMaximumQuestNameWidth(Quest mainQuest, int defaultMargin) {
        int max = mainQuest.getName().length() + defaultMargin; // Pour des raisons de simplicité de test, on suppose que 1 caractère à une longueur de 1.
        for (Quest q: mainQuest.getSubQuests()) {
            int i = getMaximumQuestNameWidth(q, max + BETWEEN_QUEST_MARGIN_WIDTH);
            if (i > max)
                max = i;
        }
        return max;
    }

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
            public void addProgress(double many) {

            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };
        Assertions.assertEquals(10, getMaximumQuestNameWidth(q1, 0));
        Assertions.assertEquals(4, getMaximumQuestNameWidth(q2, 0));
    }
}
