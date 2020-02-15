package be.ac.umons.sgl.mom.Enums;


/***/
public enum MobileType
{
    /***/
    Strong("Strong",0.7,0.1,0.2),
    /***/
    Athletic("Athletic",0.2,0.7,0.1),
    /***/
    Lambda("Lambda",0.33,0.33,0.34);


    /***/
    private double percentStrength;
    /***/
    private double percentAgility;
    /***/
    private double percentDefence;
    /***/
    private String name;


    /***/
    private MobileType(String name, double percentStrength, double percentAgility, double percentDefence)
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
    public double getAgility()
    {
        return percentAgility;
    }


    /***/
    public double getDefence()
    {
        return percentDefence;
    }


    /***/
    public double getStrength()
    {
        return percentStrength;
    }
}
