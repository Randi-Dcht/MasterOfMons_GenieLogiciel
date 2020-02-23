package be.ac.umons.sgl.mom.Enums;


/**
 * This class define the type of the player in the game
 * @author Umons_Group_2_ComputerScience
 */
public enum Type
{

  beefy("Costaud",8,5,6),
  athletic("Athlètique",6,8,5),
  loser("Nul",4,4,4),
  normal("lambda",6,6,6);

  final String name;
  final int strength;
  final int defence;
  final int agility;


  /**
   * This constructor define the type of player
   * @param defence is the defence of character
   * @param agility is the agility of character
   * @param name is the name of the type
   * @param strength is the strength of character
   */
  private Type(String name,int strength,int agility,int defence)
  {
    this.name     = name;
    this.strength = strength;
    this.agility  = agility;
    this.defence  = defence;
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
}
