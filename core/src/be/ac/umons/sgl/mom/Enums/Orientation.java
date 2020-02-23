package be.ac.umons.sgl.mom.Enums;

/***
 * Enumerations of possible orientation in the game.
 * @author Guillaume Cardoen
 */
public enum Orientation {
    Top(0), Left(90), Bottom(180), Right(270);

    public int degrees;
    Orientation(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
