package be.ac.umons.sgl.mom.Objects;

public interface Speak {
    public void called(Speak other);
    public String[] interaction();
    public void dialog(Speak other);
}
