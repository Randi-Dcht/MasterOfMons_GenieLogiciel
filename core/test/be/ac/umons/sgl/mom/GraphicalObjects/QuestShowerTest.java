package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.Quest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
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
        int max = mainQuest.getName().length() + defaultMargin; // Pour des raisons de simpliciter de texte, on suppose que 1 caractère à une longueur de 1.
        for (Quest q: mainQuest.getSubQuests()) {
            int i = getMaximumQuestNameWidth(q, max + BETWEEN_QUEST_MARGIN_WIDTH);
            if (i > max)
                max = i;
        }
        return max;
    }

    @Test
    public void questNameWidthTest() {
        Quest q1 = new Quest("Test");
        Quest q2 = new Quest("Test", q1);
        Quest q3 = new Quest("Test", q2);
        Quest q = new Quest("Test", q3);
        Assertions.assertEquals(22, getMaximumQuestNameWidth(q, 0));
        Assertions.assertEquals(16, getMaximumQuestNameWidth(q3, 0));
        Assertions.assertEquals(10, getMaximumQuestNameWidth(q2, 0));
        Assertions.assertEquals(4, getMaximumQuestNameWidth(q1, 0));
    }
}
