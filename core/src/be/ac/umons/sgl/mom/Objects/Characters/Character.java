package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Observer;

public abstract class Character implements Attack, Observer, Social
{
    /*caracteristique physique du personnage*/
    protected int strength;
    protected int defence;
    protected int agility;
    /*caracteristique autre du personnage*/
    protected double life;
    protected int level = 1; /*between 1 and 40*/
    /**
     * This constructor allows to create a new people who pilot by a player
     * @param type who is the characteristic of this people (Enums)
     */
    public Character(Type type)
    {
        this.strength = type.getStrength();
        this.defence  = type.getDefence();
        this.agility  = type.getAgility();
        this.life     = lifemax();
    }

    /**
     *This method allows to increase the life
     */
    public void regeneration()
    {
    }

    public Actions meet(PNJ other)
    {
        return null;
    }

    public double getLife()
    {
        return life;
    }

    public int getStrength()
    {
        return strength;
    }
    public int getDefence()
    {
        return defence;
    }
    public int getAgility()
    {
        return agility;
    }
    public int getLevel(){return level;}

    /**
     *This method allows define the characteristic of a people.
     *@param strength who is strength of people.
     *@param defence who is defence of people.
     *@param agility who is the capacity of avoid the attack.
     */
    public void updateType(int strength, int defence, int agility)
    {
        this.strength = strength;
        this.defence = defence;
        this.agility = agility;
    }

    /**
     *This method allows to calculated the maximum of life people.
     *@return max life.
     */
    public double lifemax()
    {
        return (strength*20);
    }

    @Override
    public void loseAttack(double lose)
    {
        life = life - lose;
    }
    /**
     *This method allows to calculated ...
     */
    public double displacement()
    {
        return (2 +(agility/20));
    }


    /**
     *This method allows to say the time between two attack of this people
     * @return the time between two attack
     */
    @Override
    public double recovery()
    {
        return (0.5 + (agility/40));
    }

    /**
     * This method allows to say the probability of dodge the other attack
     * @return the probability of dodge
     */
    @Override
    public double dodge() /*esquive*/
    {
        return Math.min((agility/100),0.75);
    }
}
