package be.ac.umons.sgl.mom.Enums;

/**
 * This class allows define an enum of Action for a people or a Mobile
 * @author Umons_Group_2_ComputerScience
 */
public enum Actions
{
    Attack("attack",10),
    Dialog("dialog",5),
    Never("staticObject",0);

    final String name;
    final int importance;

    /**
     * This constructor who define a name of Action
     * @param name who is name of action
     */
    private Actions(String name, int importance)
    {
        this.name = name;
        this.importance = importance;
    }


    /***/
    public Actions comparable(Actions action)
    {
        if (equals(action))
            return action;
        if (this.importance > action.importance)
            return this;
        return action;
    }


    /***/
    @Override
    public String toString()
    {
        return name;
    }


    /***/
    public boolean equals(Actions other)
    {
        return other.name.equals(this.name);
    }
}