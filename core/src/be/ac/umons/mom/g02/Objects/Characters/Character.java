package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.LifeChanged;
import be.ac.umons.mom.g02.Events.Notifications.LowSomething;
import be.ac.umons.mom.g02.Objects.Items.Guns;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * This class define a character who have life,strength,agility,defence and level in the game
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public abstract class Character implements Attack, Serializable
{

    /**
     * The enum for the player of the character
     */
    public enum TypePlayer{Computer,Human;};
    /**
     * characteristic of the character strength
     */
    protected int strength;
    /**
     * This is the list of the items in the bag of the character
     */
    protected ArrayList<Items> myObject = new ArrayList<>();
    /**
     * characteristic of the character defence
     */
    protected int defence;
    /**
     * characteristic of the character agility
     */
    protected int agility;
    /**
     * This is the actual life of the character
     */
    protected double actualLife = 0;
    /**
     * This is the location on the word (TMX)
     */
    protected Maps maps;
    /**
     * This is the specific object
     * This is the gun of the character
     */
    protected Guns gun;
    /**
     * This is the mobile living
     */
    protected boolean living = true;
    /**
     * This is the level of the character
     * between 1 and 40
     */
    protected int level = 1;
    /**
     * The speed of the character
     */
    protected double speed;
    /**
     * The name of the character
     */
    final String name;
    /**
     * The type of character when it is instantiate this
     */
    final Type type;
    /**
     * This is the money of the player in the game
     */
    protected int myMoney;


    /**
     * This constructor allows to create a new people who pilot by a player
     * @param name is the name of the character
     * @param type is the specific type of the character
     */
    public Character(String name, Type type)
    {
        this.name = name;
        this.type = type;
    }


    /**
     * This method allows to add the point on the life
     * @param cmb is the point to add
     */
    protected void regenerationLife(double cmb)
    {
        if (actualLife + cmb >= lifeMax())
            actualLife = lifeMax();
        else
            actualLife += cmb;
        Supervisor.getEvent().notify(new LifeChanged(this, actualLife));
    }



    /**
     * This method allows to change the speed of the people
     * @param cmb if cmb equal 0 the speed is reinitialise
     *            if cmb doesn't equal 0 the speed is multiply by cmb
     */
    public void setSpeed(int cmb)
    {
        if (cmb != 0)
            speed *= cmb;
        else
            speed = displacement();
    }


    /**
     * This method allows to give the speed of the people
     * @return the speed
     */
    public double getSpeed()
    {
        return speed*4;
    }


    /***/
    @Override
    public abstract TypePlayer getType();


    public abstract Actions getAction();

    /**
     * This method return the total of the money
     * @return the number of money
     */
    public int getMyMoney()
    {
        return myMoney;
    }


    /**
     * This method allows to pull the money to the character
     * @return if the people can pull money and pull money if true
     */
    public boolean pullMoney(int many)
    {
        if (myMoney + many < 0)
            return false;
        myMoney -= many;
        return true;
    }


    /**
     * This method returns the number of the life of this character
     * @return the life of character
     */
    public double getActualLife()
    {
        return actualLife;
    }

    /**
     * This method sets the life of this character
     * @param actualLife  the life of character
     * @param notify If a event must be raised
     */
    public void setActualLife(double actualLife, boolean notify)
    {
        this.actualLife = actualLife;
        if (actualLife <= 0)
            dead();
        if (notify)
            Supervisor.getEvent().notify(new LifeChanged(this, actualLife));
    }

    /**
     * This method sets the life of this character
     * @param actualLife  the life of character
     */
    public void setActualLife(double actualLife)
    {
        setActualLife(actualLife, true);
    }

    /**
     * This method returns the number of the strength of this character
     * @return the number of strength
     */
    public int getStrength()
    {
        return strength;
    }


    /**
     * This method returns the number of the defence of this character
     * @return number of the defence
     */
    public int getDefence()
    {
        return defence;
    }


    /**
     * This method returns the number of the agility of this character
     * @return number of agility
     */
    public int getAgility()
    {
        return agility;
    }


    /**
     * This method return the number of the level of this character
     * @return the number of level
     */
    public int getLevel()
    {
        return level;
    }


    /**
     * This method return the characteristic of the character
     * @return the type of the character
     */
    @Override
    public Type getCharacteristic()
    {
        return type;
    }


    /**
     * This method return the maps of the people
     * @return maps in maps (TMX)
     */
    public Maps getMaps()
    {
        return maps;
    }


    /**
     * This method allows to add the maps to the mobile
     * @param maps is the maps of the mobile
     */
    public void setMaps(Maps maps)
    {
        this.maps = maps;
    }


    /**
     *This method allows define the characteristic of a people.
     *@param strength who is strength of people.
     *@param defence who is defence of people.
     *@param agility who is the capacity of avoid the attack.
     */
    public void updateType(int strength, int defence, int agility)
    {
        this.strength += strength;
        this.defence  += defence;
        this.agility  += agility;
        this.speed     = displacement();

        if (actualLife == 0)
            actualLife = lifeMax();
        Supervisor.getEvent().notify(new LifeChanged(this, actualLife));
    }


    /**
     *This method allows the remove a object in the bag of people.
     *@return true of the object is remove and false otherwise
     */
    public boolean removeObject(Items object)
    {
        if(myObject.size()==0 || !myObject.contains(object))
            return false;
        myObject.remove(object);
        return true;
    }


    /**
     * This method allows to give the all inventory of this people
     * @return The inventory of the player
     */
    public List<Items> getInventory()
    {
        return myObject;
    }


    /***/
    public int getPointType(int level)
    {
        return (level-1)*3;
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
        if (actualLife - lose < 0)
            actualLife = 0;
        else
            actualLife -= lose;

        if (actualLife <= lifeMax()*0.1 && this.getClass().equals(People.class))
            Supervisor.getEvent().notify(new LowSomething(LowSomething.TypeLow.Life));

        if (actualLife <= 0)
            dead();
        Supervisor.getEvent().notify(new LifeChanged(this, actualLife));
    }


    /**
     *This method allows to calculated speed the displacement of the character
     * @return the speed of the character
     */
    private double displacement()
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
        return (1.5-(agility/40));
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


    public String getName()
    {
        return name;
    }

    /**
     * This method is call when the character is dead
     * And this method warn the other class with the <code>Events.Dead</code>
     */
    public void dead()
    {
        living = false;
        Supervisor.getEvent().notify(new Dead(this));
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
        return (gun != null /*&& ((Items)gun).getObsolete()*/);
    }


    /**
     * This method return the gun of the attack during the attack
     * @return gun of the attacker
     */
    @Override
    public Guns getGun()
    {
        return gun;
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
