package be.ac.umons.sgl.mom.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une quête du jeu.
 */
public class Quest {
    /**
     * Le nom de la quête qui sera affiché à l'utilisateur.
     */
    protected String name;
    /**
     * La liste des sous-quêtes de cette quête.
     */
    protected List<Quest> subQuests;
    /**
     * Est-ce que la quête est active ?
     */
    protected boolean active = false;
    /**
     * La quête parente si cette quête est une sous-quête.
     */
    protected Quest parentQuest;
    /**
     * Est-ce que la quête est terminée.
     */
    protected boolean finished = false;
    /**
     * Le progrés de cette quête compris dans l'intervalle [0, 1].
     */
    protected float progress = 0;
    /**
     * Le nombre total de quête et de sous-quête qu'il faudra afficher.
     */
    protected int totalQuest = 1;

    /**
     * Crée une nouvelle quête.
     * @param name Le nom de la quête.
     */
    public Quest(String name) {
        this.name = name;
        subQuests = new ArrayList<>();
    }

    /**
     * Crée une nouvelle quête.
     * @param name Le nom de la quête.
     * @param subQuest La/Les sous-quête(s) de la quête.
     */
    public Quest(String name, Quest... subQuest) {
        this.name = name;
        subQuests = new ArrayList<>();
        addSubQuests(subQuest);
    }

    /**
     * Retourne le nom de la quête.
     * @return Le nom de la quête.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne une liste ses sous-quêtes de cette quête.
     * @return Une liste ses sous-quêtes de cette quête
     */
    public List<Quest> getSubQuests() {
        return subQuests;
    }

    /**
     * Ajoute des sous-quêtes à cette quête.
     * @param subQuest Les sous-quêtes à ajouter.
     */
    public void addSubQuests(Quest... subQuest) {
        for (Quest q :
                subQuest) {
            subQuests.add(q);
            q.setParentQuest(this);
        }
        totalQuest = calculateTotalSubQuestsNumber() + 1;
    }

    /**
     * Retourne si la quête est active ?
     * @return Si la quête est active ?
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Active la quête.
     */
    public void activate() {
        if (parentQuest != null)
            parentQuest.activate();
        active = true;
    }

    /**
     * Désactive la quête.
     */
    public void desactivate() {
        active = true;
        for (Quest q :
                subQuests) {
            q.desactivate();
        }
//        parentQuest.desactivate();
    }

    /**
     * Marque la quête comme terminée.
     */
    public void finish() {
        finished = true;
        progress = 1;
        if (parentQuest != null)
            parentQuest.calculateProgress();
    }

    /**
     * Retourne si la quête est terminée.
     * @return Si la quête est terminée.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Défini la quête parente à celle-ci. (ATTENTION : Cette méthode ne doit être appelée que par la quête parente à celle-ci).
     * @param parentQuest La quête parente.
     */
    private void setParentQuest(Quest parentQuest) {
        this.parentQuest = parentQuest;
        if (active)
            parentQuest.activate();
    }

    /**
     * Retourne le nombre total de quête et de sous-quête qu'il faudra afficher.
     * @return Le nombre total de quête et de sous-quête qu'il faudra afficher.
     */
    public int getTotalSubQuestsNumber() {
        return totalQuest;
    }

    /**
     * Calcule le nombre total de quête et de sous-quête qu'il faudra afficher.
     * @return Le nombre total de quête et de sous-quête qu'il faudra afficher.
     */
    protected int calculateTotalSubQuestsNumber() {
        int res = subQuests.size();
        for (Quest q :
                subQuests) {
            res += q.getTotalSubQuestsNumber();
        }
        return res;
    }

    /**
     * Retourne le progrés de la quête compris dans l'interval [0,1].
     * @return Le progrés de la quête compris dans l'interval [0,1].
     */
    public float getProgress() {
        return progress;
    }

    /**
     * Calcule le progrés de la quête actuelle ainsi que de ces sous-quêtes.
     */
    protected void calculateProgress() {
        int questFinished = 0;
        int totalQuest = 0;
        for (Quest q :
                subQuests) {
            totalQuest++;
            if (q.isFinished())
                questFinished++;
        }
        progress = (float)questFinished / totalQuest;
    }
}
