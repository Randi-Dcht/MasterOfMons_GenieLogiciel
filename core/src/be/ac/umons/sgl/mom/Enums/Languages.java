package be.ac.umons.sgl.mom.Enums;

public enum Languages {
    English("en"),
    French("fr"); // TODO : Delete if not included

    String loc;

    Languages(String loc) {
        this.loc = loc;
    }

    public String getLocale() {
        return loc;
    }
}
