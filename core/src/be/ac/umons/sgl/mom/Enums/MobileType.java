package be.ac.umons.sgl.mom.Enums;


/***/
public enum MobileType
{
    /***/
    Strong("Strong",70,10,20),
    /***/
    Athletic("Athletic",20,70,10),
    /***/
    Lambda("Lambda",33,33,34);


    /***/
    private int percentStrength;
    /***/
    private int percentAgility;
    /***/
    private int percentDefence;
    /***/
    private String name;


    /***/
    private MobileType(String name, int percentStrength, int percentAgility, int percentDefence)
    {
        this.percentAgility  = percentAgility;
        this.percentDefence  = percentDefence;
        this.percentStrength = percentStrength;
        this.name            = name;
    }


    /***/
    public String getName()
    {
        return name;
    }


    /***/
    public int getAgility()
    {
        return percentAgility;
    }


    /***/
    public int getDefence()
    {
        return percentDefence;
    }


    /***/
    public int getStrength()
    {
        return percentStrength;
    }
}
