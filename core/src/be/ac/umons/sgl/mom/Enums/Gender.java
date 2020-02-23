package be.ac.umons.sgl.mom.Enums;


/**
 * This class define the gender of people (player human)
 * @author Umons_Group_2_ComputerScience
 */
public enum Gender
{
    Women('F',"Women"),
    Men('H',"Men");

    private char name;
    private String full;


    /**
     * This constructor define the gender
     * @param name is a char of gender
     * @param full is the name in english
     */
    private Gender(char name,String full)
    {
        this.name=name;
        this.full= full;
    }

    /**
     * This method return the name in english
     * @return the name
     */
    @Override
    public String toString()
    {
        return full;
    }


    /**
     * This method return the char of the gender
     * @return a char of gender
     */
    public char getName()
    {
        return name;
    }
}
