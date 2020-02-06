package be.ac.umons.sgl.mom.Enums;

/**
 * This class allows define an enum of Action for a people or a Mobile
 * @author : Randy Dauchot(étudiant en sciences Informatique Umons).
 */
public enum Actions
{
    Attack("attack"),
    Call("call"),
    HelpOther("helpOther"),
    NeedHelp("needHelp"),
    Study("study"),
    Flirt("flirt"),
    Sleep("sleep"),
    Walk("walk"),
    Play("play"),
    Listen("listen"),
    Drink("drink"),
    Speak("speak"),
    Never("staticObject");

    final String name;

    /**
     * This constructor who define a name of Action
     * @param name who is name of action
     */
    private Actions(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(Actions other)
    {
        return other.name.equals(this.name);
    }
}