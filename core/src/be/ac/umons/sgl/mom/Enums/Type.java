package be.ac.umons.sgl.mom.Enums;

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

  private Type(String name,int strength,int agility,int defence)
  {
    this.name     = name;
    this.strength = strength;
    this.agility  = agility;
    this.defence  = defence;
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
}
