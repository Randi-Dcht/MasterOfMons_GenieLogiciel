package be.ac.umons.sgl.mom.Animations;

public abstract class Animation {

    /**
     * Action à éxécuter à chaque mise à jour de la valeur.
     */
    protected Runnable runningAction;
    /**
     * Action à éxécuter une fois l'animation terminée.
     */
    protected Runnable endingAction;

    /**
     * Met à jour la valeur actuelle.
     * @param dt (Delta Time) le temps écoulé depuis le dernier appel.
     */
    public abstract void update(double dt);
    /**
     * Si l'animation est finie ou non.
     * @return Si l'animation est finie ou non.
     */
    public abstract boolean isFinished();
    /**
     * Défini l'action à éxécuter à chaque mise à jour de la valeur.
     * @param run L'action à éxécuter à chaque mise à jour de la valeur.
     */
    public void setRunningAction(Runnable run) {
        this.runningAction = run;
    }

    /**
     * Défini l'action à éxécuter une fois l'animation terminée.
     * @param run L'action à éxécuter une fois l'animation terminée.
     */
    public void setEndingAction(Runnable run) {
        this.endingAction = run;
    }

    /**
     * Retourne l'action à éxécuter à chaque mise à jour de la valeur
     * @return L'action à éxécuter à chaque mise à jour de la valeur
     */
    public Runnable getRunningAction() {
        return runningAction;
    }

    /**
     * Retourne l'action à éxécuter une fois l'animation terminée.
     * @return L'action à éxécuter une fois l'animation terminée.
     */
    public Runnable getEndingAction() {
        return endingAction;
    }
}
