package be.ac.umons.sgl.mom.Animations;

import be.ac.umons.sgl.mom.Interfaces.Animation;

/**
 * Cette classe implémente Animation. Elle permet d'animer un Double dans un temps donné et entre 2 valeurs données.
 */
public class DoubleAnimation implements Animation {
    /**
     * Représente la valeur actuelle.
     */
    private double actualState;
    /**
     * Valeur maximum du Double.
     */
    private double to;
    /**
     * Valeur à ajouter à chaque seconde.
     */
    private double toAddBySecond;
    /**
     * Action à éxécuter à chaque mise à jour de la valeur.
     */
    private Runnable runningAction;
    /**
     * Action à éxécuter une fois l'animation terminée.
     */
    private Runnable endingAction;

    /**
     * Initialise une nouvelle animation.
     * @param from La valeur de départ.
     * @param to La valeur d'arrivée.
     * @param time La durée (en ms) de l'animation.
     */
    public DoubleAnimation(double from, double to, double time) {
        this.actualState = from;
        this.to = to;

        toAddBySecond = (to - from) / (time / 1000);
    }

    /**
     * Initialise une nouvelle animation.
     * @param from La valeur de départ.
     * @param to La valeur d'arrivée.
     * @param time La durée (en ms) de l'animation.
     * @param runningAction L'action à éxécuter à chaque mise à jour de la valeur.
     * @param endCallback L'action à éxécuter une fois l'animation terminée.
     */
    public DoubleAnimation(double from, double to, double time, Runnable runningAction, Runnable endCallback) {
        this(from, to, time);
        this.runningAction = runningAction;
        this.endingAction = endCallback;
    }

    /**
     * Met à jour la valeur actuelle.
     * @param dt (Delta Time) le temps écoulé depuis le dernier appel.
     */
    public void update(double dt) {
        if (actualState >= to)
            return;

        actualState = actualState + toAddBySecond * dt;
        if (actualState > to) {
            actualState = to;
            if (endingAction != null)
                endingAction.run();
            return;
        }
        if (runningAction != null)
            runningAction.run();

    }

    /**
     * Retourne la valeur actuelle.
     * @return La valeur actuelle.
     */
    public double getActual() {
        return actualState;
    }

    /**
     * Si l'animation est finie ou non.
     * @return Si l'animation est finie ou non.
     */
    public boolean isFinished() {
        return Double.compare(actualState, to) == 0;
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
}
