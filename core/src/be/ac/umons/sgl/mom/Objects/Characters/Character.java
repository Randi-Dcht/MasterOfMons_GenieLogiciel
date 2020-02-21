package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Notifications.Dead;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Items.Gun;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Character implements Attack, Social, Serializable
{
    /**The enum for the player of the character*/
    public enum TypePlayer{Computer,Human;};


    /*characteristic of the character*/
    protected int strength;
    protected ArrayList<Items> myObject = new ArrayList<Items>(); //objet dans son sac à dos
    protected int defence;
    protected int agility;
    protected double life;
    protected Place place;
    protected Gun gun;
    protected boolean living = true;
    protected int level = 1; /*between 1 and 40*/
    protected ReadConversation conversation;
    final String name;


    /**
     * This constructor allows to create a new people who pilot by a player
     * @param name is the name of the character
     */
    public Character(String name)
    {
        this.name     = name;
        conversation  = ReadConversation.getInstance();
    }


    /**
     *This method allows to increase the life
     * @param dt is the time between two frame
     */
    public void regeneration(double dt)
    {
        if((life+dt)<=lifeMax())
            life = life + dt+10;
        if(!living && life > lifeMax()*0.6)
            living = true;
    }


    /***/
    public double getLife()
    {
        return life;
    }


    /***/
    public int getStrength()
    {
        return strength;
    }


    /***/
    public int getDefence()
    {
        return defence;
    }


    /***/
    public int getAgility()
    {
        return agility;
    }


    /***/
    public int getLevel()
    {
        return level;
    }


    /**
     * This method return the place of the people
     * @return place in maps (TMX)
     */
    public Place getPlace()
    {
        return place;
    }


    /**
     * This method allows to add the place to the mobile
     * @param place is the place of the mobile
     */
    public void setPlace(Place place)
    {
        this.place = place;
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
        this.defence  = defence;
        this.agility  = agility;
        this.life     = lifeMax();
    }


    /**
     *This method allows to calculated the maximum of life people.
     *@return max life.
     */
    public double lifeMax()
    {
        return (strength*20);
    }


    /**
     * This method allows to decrease the life of the character when he lose the dodge
     * @param lose is the number of decrease the life
     */
    @Override
    public void loseAttack(double lose)
    {
        life = life - lose;
        if(life <= 0)
            dead();
    }


    /**
     *This method allows to calculated speed the displacement of the character
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


    /**
     * This method is call when the character is dead
     * And this method warn the other class with the <code>Events.Dead</code>
     */
    public void dead()
    {
        living = false;
        SuperviserNormally.getSupervisor().getEvent().notify(new Dead(this));
    }


    /**
     * This method allows to calculus the damage of the gun
     * @return the damage
     */
    @Override
    public double damageGun()
    {
        return 2*7+Math.max(5,level-3);
    }


    /**
     * This method allows to know if the people have a gun
     * @return boolean if true else false
     */
    @Override
    public boolean howGun()
    {
        return gun != null;
    }


    /**
     * This method allows to give the name of the people
     * @return name of the character
     */
    @Override
    public String toString()
    {
        return name;
    }


    /**
     * This method allows if the character live
     * @return boolean if living true else false
     */
    public boolean isLiving()
    {
        return living;
    }
}
