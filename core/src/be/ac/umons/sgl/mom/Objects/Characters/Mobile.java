package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.PlayerType;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import java.io.Serializable;
import java.util.Random;

/**
 *This abstract class allows define a no player, it is a character pilot by computer.
 *@author Randy Dauchot (étudiant en Sciences informatique Umons)
 */
public class Mobile extends Character implements Serializable
{
    /*save the bloc of player*/
    protected Bloc playerBloc;
    protected MobileType type;
    protected Place place; //TODO mettre dans character voir avec People


    /**
     * This constructor allows define the mobile/PNJ
     * @param name is the name of the mobile
     * @param playerBloc is the bloc of the player
     */
    public Mobile(String name, Bloc playerBloc, MobileType type)
    {
        super(name);
        this.level = calculus(playerBloc);
        this.playerBloc  = playerBloc;
        this.type = type;
        calculusPoint(type);
    }


    /***/
    private int calculus(Bloc playerBloc)
    {
        int rd = new Random().nextInt((playerBloc.getMaxMob()-playerBloc.getMinMob()+1))+playerBloc.getMinMob(); //TODO tester
        if (rd >= playerBloc.getMinMob() && rd <= playerBloc.getMaxMob())
            return rd;
        return playerBloc.getMinMob();
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
     * This method return the place of this mobile
     * @return  place of the mobile
     */
    public Place getPlace()
    {
        return place;
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
    public PlayerType getType()
    {
        return PlayerType.ComputerPlayer;
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
    {//TODO relier avec le temps entre chaque attaque
        if(living)
            SuperviserNormally.getSupervisor().attackMethod(this,victim);
    }


    /***/
    @Override
    public Actions getAction()
    {
        return Actions.Never;
    }


    /***/
    public String getDialog(String answer)
    {
        return conversation.getDialogPNJ().get(answer);
    }


    @Override
    public String toString()
    {
        return name + ":" + life;
    }
}
