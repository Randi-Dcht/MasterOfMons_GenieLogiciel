package be.ac.umons.sgl.mom.Enums;

/**
 * An enumeration of all languages available in the game.
 * @author Guillaume Cardoen
 */
public enum Languages {
    English("en"),  // TODO : Delete if not included
    French("fr"); // TODO : Delete if not included

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
