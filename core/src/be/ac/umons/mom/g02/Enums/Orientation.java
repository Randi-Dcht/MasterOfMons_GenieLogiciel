package be.ac.umons.mom.g02.Enums;

/***
 * Enumerations of possible orientation in the game.
 * @author Guillaume Cardoen
 */
public enum Orientation {
    Top(0), Left(90), Bottom(180), Right(270);

    /**
     * The degrees represented by the orientation.
     */
    public int degrees;

    /**
     * @param degrees The degrees represented by the orientation.
     */
    Orientation(int degrees) {
        this.degrees = degrees;
    }

    /**
     * @return The degrees represented by the orientation.
     */
    public int getDegrees() {
        return degrees;
    }
}
