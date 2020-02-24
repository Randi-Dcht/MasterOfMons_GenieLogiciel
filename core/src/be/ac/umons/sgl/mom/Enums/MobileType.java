package be.ac.umons.sgl.mom.Enums;


/***/
public enum MobileType
{
    /***/
    Strong("Strong",0.7,0.1,0.2,Type.beefy),
    /***/
    Athletic("Athletic",0.2,0.7,0.1,Type.athletic),
    /***/
    Lambda("Lambda",0.33,0.33,0.34,Type.normal),
    /***/
    Loser("Loser",0.33,0.33,0.34,Type.loser);


    /***/
    final double percentStrength;
    /***/
    final double percentAgility;
    /***/
    final double percentDefence;
    /***/
    final String name;
    /***/
    final Type type;


    /**
     * This constructor define the mobile of type
     */
    private MobileType(String name, double percentStrength, double percentAgility, double percentDefence,Type type)
    {
        this.percentAgility  = percentAgility;
        this.percentDefence  = percentDefence;
        this.percentStrength = percentStrength;
        this.name            = name;
        this.type            = type;
    }


    /**
     * This method return the name of the mobile type
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * This method returns the percent of agility of the mobile
     * @return the percent of agility
     */
    public double getAgility()
    {
        return percentAgility;
    }


    /**
     * This method returns the percent of defence of mobile
     * @return the percent of defence
     */
    public double getDefence()
    {
        return percentDefence;
    }


    /**
     * This method return the percent of strength of mobile
     * @return the percent of strength
     */
    public double getStrength()
    {
        return percentStrength;
    }


    /**
     * This method return the type of this type mobile
     * @return the type
     */
    public Type getType()
    {
        return type;
    }
}
