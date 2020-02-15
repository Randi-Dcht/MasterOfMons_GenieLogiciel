package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.PlayerType;
import be.ac.umons.sgl.mom.Enums.Type;
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


    /**
     * This constructor allows define the mobile/PNJ
     * @param name is the name of the mobile
     * @param playerBloc is the bloc of the player
     */
    public Mobile(String name, Bloc playerBloc, MobileType type)
    {
        super(name);
        this.level = new Random().nextInt((playerBloc.getMaxMob()-playerBloc.getMinMob()+1)+playerBloc.getMinMob()); //TODO tester
        this.playerBloc  = playerBloc;
        calculusPoint(type);
    }


    /***/
    public void calculusPoint(MobileType type)
    {
        int number = (level-1)*3; int[] listN = new int[3]; int total;
        listN[0] =(int)( number  * type.getStrength());
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
     * @param playerType is the type of the player
     */
    public void upgrade(Bloc playerBloc,Type playerType)
    {
        this.playerBloc  = playerBloc;
        this.strength    = playerType.getStrength();
        this.defence     = playerType.getDefence();
        this.agility     = playerType.getAgility();
    }


    /**
     * This method allows to attack the other attacker automatic
     * @param victim is the other character of the attack
     */
    public void nextAttack(Attack victim)
    {//TODO ajouter un timer avec le temps d'esquive pour refaire une attaque
        if(living)
            SuperviserNormally.getSupervisor().attackMethod(this,victim);
    }

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
}
