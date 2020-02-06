package be.ac.umons.sgl.mom.Objects.Characters;

public interface Attack
{
    /**
     * This method calculates the possibility to dodge the attacker.
     */
    public double dodge();


    /**
     * This method calculates the time between two attack
     */
    public double recovery();


    /**
     * This method took of the life of the victim
     */
    public void loseAttack(double lose);


    /**
     * This method return the agility of character
     */
    public int getAgility();


    /**
     * This method return the strength of character
     */
    public int getStrength();


    /**
     * This method return the defence of character
     */
    public int getDefence();


    /**
     * This method return the level of character
     */
    public int getLevel();


    /**
     * This method return the type of character (Human or Computer)
     * Human is equal 'H'
     * Computer is equal 'C'
     */
    public char getType();

}