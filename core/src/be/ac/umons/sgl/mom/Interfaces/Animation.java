package be.ac.umons.sgl.mom.Interfaces;

public interface Animation {
    /**
     * Met à jour la valeur actuelle.
     * @param dt (Delta Time) le temps écoulé depuis le dernier appel.
     */
    void update(double dt);
    /**
     * Si l'animation est finie ou non.
     * @return Si l'animation est finie ou non.
     */
    boolean isFinished();
    /**
     * Retourne l'action à éxécuter une fois l'animation terminée.
     * @return L'action à éxécuter une fois l'animation terminée.
     */
    Runnable getEndingAction();
    /**
     * Retourne l'action à éxécuter à chaque mise à jour de la valeur
     * @return L'action à éxécuter à chaque mise à jour de la valeur
     */
    Runnable getRunningAction();
    /**
     * Défini l'action à éxécuter une fois l'animation terminée.
     * @param action L'action à éxécuter une fois l'animation terminée.
     */
    void setEndingAction(Runnable action);

    /**
     * Défini l'action à éxécuter à chaque mise à jour de la valeur.
     * @param action L'action à éxécuter à chaque mise à jour de la valeur.
     */
    void setRunningAction(Runnable action);
}
