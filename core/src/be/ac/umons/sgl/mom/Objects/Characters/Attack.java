package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Type;


/**
 * This class define the method of the attacker character
 * @author Umons_Group_2_ComputerScience
 */
public interface Attack
{
    /**
     * This method calculates the possibility to dodge the attacker.
     * @return the percent of dodge
     */
    public double dodge();


    /**
     * This method calculates the time between two attack
     * @return the time between two attacks
     */
    public double recovery();


    /**
     * This method took of the life of the victim
     * @param lose is the life lose after attack
     */
    public void loseAttack(double lose);


    /**
     * This method return the agility of character
     * @return the agility of the character
     */
    public int getAgility();


    /**
     * This method return the strength of character
     * @return the strength of the character
     */
    public int getStrength();


    /**
     * This method return the defence of character
     * @return the defence of the character
     */
    public int getDefence();


    /**
     * This method return the level of character
     * @return the level of the character
     */
    public int getLevel();


    /**
     * This method return the type of character (Human or Computer)
     * Human is equal PlayerHuman
     * Computer is equal PlayerComputer
     */
    public Character.TypePlayer getType();


    /**
     * This method return if the character have a gun
     * @return boolean if have gun*/
    public boolean howGun();


    /**
     * This method return the damage of the gun
     * @return the damage of the gun
     */
    public double damageGun();


    /**
     * This method return the type of characteristic
     * @return the type of the characteristic
     */
    public Type getCharacteristic();

}