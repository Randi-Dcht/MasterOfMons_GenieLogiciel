package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Items.Guns;


/**
 * This class is an melange between logic mobile and graphical character
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class PNJ implements Attack
{
    /**
     * This is the instance of a mobile in the logic code
     */
    protected Mobile logic;
    /**
     * This is the instance of the graphic character
     */
    protected be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphic;
    /***/
    protected Player player;



    /**
     * This method calculates the possibility to dodge the attacker.
     * @return the percent of dodge
     */
    @Override
    public double dodge()
    {
        return logic.dodge();
    }


    /**
     * This method calculates the time between two attack
     * @return the time between two attacks
     */
    @Override
    public double recovery()
    {
        return logic.recovery();
    }


    /**
     * This method took of the life of the victim
     * @param lose is the life lose after attack
     */
    @Override
    public void loseAttack(double lose)
    {
        logic.loseAttack(lose);
    }


    /**
     * This method return the agility of character
     * @return the agility of the character
     */
    @Override
    public int getAgility()
    {
        return logic.getAgility();
    }


    /**
     * This method return the strength of character
     * @return the strength of the character
     */
    @Override
    public int getStrength()
    {
        return logic.getStrength();
    }


    /**
     * This method return the defence of character
     * @return the defence of the character
     */
    @Override
    public int getDefence()
    {
        return logic.getDefence();
    }


    /**
     * This method return the level of character
     * @return the level of the character
     */
    @Override
    public int getLevel()
    {
        return logic.getDefence();
    }


    /**
     * This method return the type of character (Human or Computer)
     * Human is equal PlayerHuman
     * Computer is equal PlayerComputer
     */
    @Override
    public Character.TypePlayer getType()
    {
        return logic.getType();
    }

    /**
     * This method return if the character have a gun
     * @return boolean if have gun
     */
    @Override
    public boolean howGun()
    {
        return logic.howGun();
    }


    /**
     * This method return the damage of the gun
     * @return the damage of the gun
     */
    @Override
    public double damageGun()
    {
        return logic.damageGun();
    }


    /**
     * This method allows to give the actual gun of the attacker
     * @return gun of the attacker
     */
    @Override
    public Guns getGun()
    {
        return logic.getGun();
    }


    /**
     * This method return the type of characteristic
     * @return the type of the characteristic
     */
    @Override
    public Type getCharacteristic()
    {
        return logic.getCharacteristic();
    }


    /**
     * This method allows to say if the character can to attacker the other
     * @return boolean if can attack (true) else false
     */
    @Override
    public boolean canAttack()
    {
        return logic.canAttack();
    }


    public void initAttack()
    {
        if (logic.getActualLife() > 0 )
            graphic.move(player.getPosX(),player.getPosY());
    }
}
