package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import java.io.Serializable;

/**
*This abstract class allows define a no player, it is a character pilot by computer.
*@author Randy Dauchot (étudiant en Sciences informatique Umons)
*/

public class Mobile extends Character implements Serializable
{
  /*name of this*/
  final String name;
  /*save the bloc of player*/
  protected Bloc playerBloc;
  /*save the level of player*/
  protected int playerLevel;


  /**
   * This constructor allows define the mobile/PNJ
   * @param name is the name of the mobile
   * @param playerBloc is the bloc of the player
   * @param playerLevel  is the level of the player
   * @param playerType is the type of the player
   */
  public Mobile(String name, Bloc playerBloc, int playerLevel,Type playerType)
  {
    super(playerType);
    this.name        = name;
    this.playerBloc  = playerBloc;
    this.playerLevel = playerLevel;
  }


  /**
   * Mobile is a player computer so the type is 'C'
   */
  @Override
  public char getType()
  {
    return 'C';
  }


  /**
   * When the player up in the level or the bloc, the mobile/PNJ must upgrade
   * @param playerBloc is the bloc of the player
   * @param playerLevel  is the level of the player
   * @param playerType is the type of the player
   */
  public void upgrade(Bloc playerBloc, int playerLevel,Type playerType)
  {
    this.playerBloc  = playerBloc;
    this.playerLevel = playerLevel;
    this.strength    = playerType.getStrength();
    this.defence     = playerType.getDefence();
    this.agility     = playerType.getAgility();
  }


  /**
   * This method allows to attack the other attacker automatic
   * @param victim is the other character of the attack
   */
  public void nextAttack(Attack victim)
  {//TODO ajouter un timer (voir avec Guillaume)
    if(living)
      SuperviserNormally.getSupervisor().attackMethod(this,victim);
  }

  @Override
  public Actions getAction()
  {
    return Actions.Never;
  }
}
