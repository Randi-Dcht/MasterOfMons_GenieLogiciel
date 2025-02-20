package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Other.RandomName;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Events.Notifications.AddFriend;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 *This abstract class allows define a no player, it is a character pilot by computer.
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class Mobile extends Character implements Serializable, FrameTime
{
    /**
     * Save the bloc of human player of the quest
     */
    protected Bloc playerBloc;
    /**
     * This is the type of the Mobile
     */
    protected MobileType type;
    /**
     * This is the time between two attacks
     */
    protected double time;
    /**
     * The other characters to attack
     */
    protected Attack victim = null;
    /**
     * The action of this mobile in the game
     */
    protected Actions action;
    /**
     * If this mobile can add the human friend
     */
    protected Boolean addFriend = true;
    /**
     * The name of the dialog of this PNJ
     */
    protected NameDialog nameDialog;


    /**
     * This constructor allows define the mobile/PNJ
     * @param name is the name of the mobile
     * @param playerBloc is the bloc of the player
     * @param type is the type of the mobile
     * @param myAction is the action of this mobile
     */
    public Mobile(String name, Bloc playerBloc, MobileType type,Actions myAction,NameDialog nameDialog)
    {
        super(name,type.getType());
        this.level = calculus(playerBloc);
        this.playerBloc  = playerBloc;
        this.type = type;
        this.nameDialog = nameDialog;
        action = myAction;
        calculusPoint(type);
        myMoney = new Random().nextInt(10-1) + 1;
    }


    /***/
    public Mobile( Bloc playerBloc, MobileType type,Actions myAction,NameDialog nameDialog)
    {
        this(RandomName.giveName(),playerBloc,type,myAction,nameDialog);
    }


    /**
     * This method allows to update the variable of the mobile with the time
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        if (victim != null)
            time -= dt;

        if (time <= 0 && victim != null && living)
        {
            Attack t = victim;
            this.victim = null;
            nextAttack(t);
        }

        if (!living)
            victim = null;
    }


    /**
     * This method return if the mobile attacker now
     * @return boolean if mobile attack other
     */
    public boolean inAttack()
    {
        return victim != null;
    }


    /**
     * This method allows to say if the character can attack the other
     * @return a boolean
     */
    @Override
    public boolean canAttack()
    {
        return true;
    }


    /**
     * This method allows to add the friend human player at this mobile
     * @return boolean of can add friend
     */
    public boolean setFriend()
    {
        if(addFriend)
        {
            Supervisor.getEvent().notify(new AddFriend(this));
            addFriend = false;
            return true;
        }
        return false;
    }


    /**
     * This method allows to launch a new attack automatic
     */
    public void letsGo(Attack victim)
    {
        time = recovery();
        this.victim = victim;
    }


    /**
     *This method allows to increase the life
     * @param dt is the time between two frame
     */
    public void regeneration(double dt)
    {
        if (!living)
        {
            regenerationLife(dt*0.3);
        }

        if(!living && actualLife >= lifeMax())
        {
            living = true;
        }
    }


    /**
     * This method calculus the level of the mobile in the game
     * @param playerBloc  is the type of the mobile
     */
    private int calculus(Bloc playerBloc)
    {
        if (playerBloc == null)
            return 1;
        int rd = new Random().nextInt((playerBloc.getMaxMob()-playerBloc.getMinMob()+1))+playerBloc.getMinMob();
        if (rd >= playerBloc.getMinMob() && rd <= playerBloc.getMaxMob())
            return rd;
        return playerBloc.getMinMob();
    }


    /**
     * This method calculate the point of the mobile in strength,defence,agility
     * @param type is the type of mobile
     */
    public void calculusPoint(MobileType type)
    {
        int number = (level-1)*3; int[] listN = new int[3]; int total;
        listN[0] =(int)( number  * type.getStrength())+1;
        listN[1] =(int)( number  * type.getDefence());
        listN[2] =(int)( number  * type.getAgility());
        if((total  =listN[0]+listN[1]+listN[2]) != number && number > 0)
        {
            int random = new Random().nextInt(2);
            listN[random] = listN[random] +  (number - total);
        }
        updateType(listN[0],listN[1],listN[2]);
    }


    /**
     * Mobile is a player computer so the type is computerPlayer
     * @return playerType of this instance
     */
    @Override
    public TypePlayer getType()
    {
        return TypePlayer.Computer;
    }


    /**
     * This method allows to attack the other attacker automatic
     * @param victim is the other character of the attack
     */
    public void nextAttack(Attack victim)
    {
        if(living)
            Supervisor.getSupervisor().attackMethod((Attack)this, victim, false);

    }

    public String getName()
    {
        return name;
    }


    /**
     * This method allows to give the action of the mobile
     * @return the action of the mobile
     */
    public Actions getAction()
    {
        return action;
    }


    /**
     * This method return the name of the dialog of this PNJ
     * @return the name of the dialog (enum)
     */
    public NameDialog getDialog()
    {
        return nameDialog;
    }


    /**
     * This method allows to add the items to the bag of the mobile
     * @param items is the items
     */
    public void addObject(Items ... items)
    {
        myObject.addAll(Arrays.asList(items));
    }


    /**
     * This method allows to say the picture of the mobile
     * @return the start name of the picture
     */
    @Override
    public String toString()
    {
        return "bh_H_sa_";
    }

    public Bloc getPlayerBloc() {
        return playerBloc;
    }

    public MobileType getMobileType() {
        return type;
    }

}
