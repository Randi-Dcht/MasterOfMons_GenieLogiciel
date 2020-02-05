package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Character implements Attack, Observer, Social
{
    /*caracteristique physique du personnage*/
    private int strength;
    private int defence;
    private int agility;
    /*caracteristique autre du personnage*/
    private double life;
    private double experience = 0;
    private int level = 1;
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

    public double getExperience()
    {
        return experience;
    }

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

    @Override
    public void attack() {

    }

    public int level()
    {
        return 0;
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

    @Override
    public void update(Events event) {

    }

    @Override
    public Actions getAction() {
        return null; //TODO etre demander via l'interface graphique
    }
}
