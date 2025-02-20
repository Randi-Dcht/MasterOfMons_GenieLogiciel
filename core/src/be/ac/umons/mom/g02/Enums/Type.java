package be.ac.umons.mom.g02.Enums;


/**
 * This class define the type of the player in the game
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum Type
{

  /**
   * The people is beefy when the strength up
   */
  beefy(   "Beefy",   8,5,6,3,0,1),
  /**
   * The people is athletic when the agility up
   */
  athletic("Athletic",6,8,5,1,3,0),
  /**
   * The people is loser when the three characteristic low
   */
  loser(   "Null",    4,4,4,-1,-1,-1),
  /**
   * This is the normally people
   */
  normal(  "Lambda",  6,6,6,1,1,1);


  /**The characteristic of the type's people*/
  final String name;
  final int strength;
  final int defence;
  final int agility;


  /**The characteristic of the type during the attack*/
  final int strengthBonus;
  final int agilityBonus;
  final int defenceBonus;


  /**
   * This constructor define the type of player
   * @param defence   is the defence of character
   * @param agility   is the agility of character
   * @param name      is the name of the type
   * @param strength  is the strength of character
   *
   * @param agilBonus is the bonus agility during attack
   * @param strBonus  is the bonus strength during attack
   * @param defBonus  is the bonus defence during attack
   */
  private Type(String name,int strength,int agility,int defence,int strBonus,int agilBonus,int defBonus)
  {
    this.name     = name;
    this.strength = strength;
    this.agility  = agility;
    this.defence  = defence;

    strengthBonus = strBonus;
    agilityBonus  = agilBonus;
    defenceBonus  = defBonus;
  }


  /**
   * This method return the number of strength
   * @return integer of strength
   */
  public int getStrength()
  {
    return strength;
  }


  /**
   * This method return the number of defence
   * @return integer of defence
   */
  public int getDefence()
  {
    return defence;
  }


  /**
   * This method return the number of agility
   * @return integer of agility
   */
  public int getAgility()
  {
    return agility;
  }


  /**
   * This method return the bonus of the agility
   * @return agility bonus
   */
  public int getAgilityBonus()
  {
    return agilityBonus;
  }


  /**
   * This method return the bonus of the defence
   * @return defence bonus
   */
  public int getDefenceBonus()
  {
    return defenceBonus;
  }


  /**
   * This method return the bonus of the strength
   * @return strength bonus
   */
  public int getStrengthBonus()
  {
    return strengthBonus;
  }
}
