package be.ac.umons.sgl.mom.Animations;

public class StringAnimation extends Animation<String> {

    double toAddBySecond;
    double actualNbr = 0;

    public StringAnimation(String to, double time) {
        super("", to, time);
        toAddBySecond = (to.length()) / (time / 1000);
    }

    public StringAnimation(String to, double time, Runnable runningAction, Runnable endCallback) {
        super("", to, time, runningAction, endCallback);
    }

    @Override
    public void update(double dt) {
        if ((toAddBySecond > 0 && actual.length() >= to.length()) || (toAddBySecond < 0 && actual.length() <= to.length()))
            return;

        actualNbr += dt * toAddBySecond;
        if (actualNbr > to.length())
            actualNbr = to.length();
        actual = to.substring(0, (int)actualNbr);
        if (runningAction != null)
            runningAction.run();
        if ((toAddBySecond > 0 && actual.length() >= to.length()) || (toAddBySecond < 0 && actual.length() <= to.length())) {
            actual = to;
            if (endingAction != null)
                endingAction.run();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
