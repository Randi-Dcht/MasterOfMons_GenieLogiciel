package be.ac.umons.sgl.mom.Animations;

/**
 * Cette classe implémente Animation. Elle permet d'animer un Double dans un temps donné et entre 2 valeurs données.
 * @author Guillaume Cardoen
 */
public class DoubleAnimation extends Animation<Double> {
    /**
     * Valeur à ajouter à chaque seconde.
     */
    private double toAddBySecond;


    /**
     * Initialise une nouvelle animation.
     * @param from La valeur de départ.
     * @param to La valeur d'arrivée.
     * @param time La durée (en ms) de l'animation.
     */
    public DoubleAnimation(double from, double to, double time) {
        super(from, to, time);

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
        if ((toAddBySecond > 0 && actual >= to) || (toAddBySecond < 0 && actual <= to))
            return;

        actual = actual + toAddBySecond * dt;
        if (runningAction != null)
            runningAction.run();
        if ((toAddBySecond > 0 && actual >= to) || (toAddBySecond < 0 && actual <= to)) {
            actual = to;
            if (endingAction != null)
                endingAction.run();
        }
    }

    /**
     * Si l'animation est finie ou non.
     * @return Si l'animation est finie ou non.
     */
    public boolean isFinished() {
        return Double.compare(actual, to) == 0;
    }
}
