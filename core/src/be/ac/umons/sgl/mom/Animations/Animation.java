package be.ac.umons.sgl.mom.Animations;

/**
 * Classe asbtraite représentant la base d'une animation.
 * @author Guillaume Cardoen
 */
public abstract class Animation<T> {

    /**
     * Action à éxécuter à chaque mise à jour de la valeur.
     */
    protected Runnable runningAction;
    /**
     * Action à éxécuter une fois l'animation terminée.
     */
    protected Runnable endingAction;

    /**
     * La valeur actuelle
     */
    protected T actual;

    /**
     * La valeur finale.
     */
    protected T to;

    /**
     * La durée de l'animation.
     */
    protected double time;

    /**
     * Initialise une nouvelle animation.
     * @param from La valeur de départ.
     * @param to La valeur d'arrivée.
     * @param time La durée (en ms) de l'animation.
     */
    public Animation(T from, T to, double time) {
        this.actual = from;
        this.to = to;
        this.time = time;
    }

    /**
     * Initialise une nouvelle animation.
     * @param from La valeur de départ.
     * @param to La valeur d'arrivée.
     * @param time La durée (en ms) de l'animation.
     * @param runningAction L'action à éxécuter à chaque mise à jour de la valeur.
     * @param endCallback L'action à éxécuter une fois l'animation terminée.
     */
    public Animation(T from, T to, double time, Runnable runningAction, Runnable endCallback) {
        this(from, to, time);
        this.runningAction = runningAction;
        this.endingAction = endCallback;
    }

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
     * Retourne la valeur actuelle.
     * @return La valeur actuelle.
     */
    public T getActual() {
        return actual;
    }
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

    public void finishNow() {
        actual = to;
    }
}
