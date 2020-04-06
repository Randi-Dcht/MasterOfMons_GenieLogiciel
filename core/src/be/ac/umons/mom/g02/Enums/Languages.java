package be.ac.umons.mom.g02.Enums;

/**
 * An enumeration of all languages available in the game.
 * @author Guillaume Cardoen
 */
public enum Languages {
    English("en"),
    French("fr");

    /**
     * The locale.
     */
    String loc;

    Languages(String loc) {
        this.loc = loc;
    }

    /**
     * @return The locale associated.
     */
    public String getLocale() {
        return loc;
    }
}
