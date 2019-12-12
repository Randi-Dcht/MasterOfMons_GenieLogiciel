package be.ac.umons.sgl.mom.Objects.Characters;

/**
 * This interface allows two speak to have a speech together
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */

public interface Speak {
    public void called(Speak other);
    public String[] interaction();
    public void dialog(Speak other);
}
