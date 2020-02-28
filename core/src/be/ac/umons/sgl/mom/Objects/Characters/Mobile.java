package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Events.Notifications.AddFriend;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.FrameTime;
import java.io.Serializable;
import java.util.Random;

/**
 *This abstract class allows define a no player, it is a character pilot by computer.
 *@author Umons_Group_2_ComputerScience
 */
public class Mobile extends Character implements Serializable,FrameTime
{
    /*save the bloc of player*/
    protected Bloc playerBloc;
    protected MobileType type;
    protected double time;
    protected Attack victim = null;
    protected Actions action;
    protected Boolean addFriend = true;


    /**
     * This constructor allows define the mobile/PNJ
     * @param name is the name of the mobile
     * @param playerBloc is the bloc of the player
     * @param type is the type of the mobile
     * @param myAction is the action of this mobile
     */
    public Mobile(String name, Bloc playerBloc, MobileType type,Actions myAction)
    {
        super(name,type.getType());
        this.level = calculus(playerBloc);
        this.playerBloc  = playerBloc;
        this.type = type;
        action = myAction;
        calculusPoint(type);
    }


    /**
     * This method allows to update the variable of the mobile with the time
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        time = time - dt;
        if (time <= 0 && victim != null)
        {
            nextAttack(victim);
            victim = null;
        }
    }


    /***/
    @Override
    public boolean canAttacker()
    {
        return true;
    }


    /***/
    public boolean setFriend()
    {
        if(addFriend)
        {
            SuperviserNormally.getSupervisor().getEvent().notify(new AddFriend(this));
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
     * This method calculus the level of the mobile in the game
     * @param playerBloc  is the type of the mobile
     */
    private int calculus(Bloc playerBloc)
    {
        int rd = new Random().nextInt((playerBloc.getMaxMob()-playerBloc.getMinMob()+1))+playerBloc.getMinMob(); //TODO tester
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
     * When the player up in the level or the bloc, the mobile/PNJ must upgrade
     * @param playerBloc is the bloc of the player
     */
    public void upgrade(Bloc playerBloc)
    {
        this.playerBloc  = playerBloc;
        calculusPoint(type);
    }


    /**
     * This method allows to attack the other attacker automatic
     * @param victim is the other character of the attack
     */
    public void nextAttack(Attack victim)
    {
        if(living)
            SuperviserNormally.getSupervisor().attackMethod(this,victim);
    }


    /**
     * This method allows to give the action of the mobile
     * @return the action of the mobile
     */
    @Override
    public Actions getAction()
    {
        return action;
    }


    /**
     * This method allows to give the dialog id of the mobile
     * @param answer is the answer of the other character
     */
    public String getDialog(String answer)
    {
        return conversation.getDialogPNJ().get(answer);
    }


    //TODO mettre les type pour les mobile
}
