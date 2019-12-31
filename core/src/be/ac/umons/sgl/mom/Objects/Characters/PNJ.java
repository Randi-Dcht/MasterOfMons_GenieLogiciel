package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.People;

import java.io.Serializable;

/**
*This abstract class allows define a no player, it is a character pilot by computer.
*@author Randy Dauchot (étudiant en Sciences informatique Umons)
*/
public abstract class PNJ implements Serializable, Attack, Observer, Social
{
  final String name;
  private double x;
  private double y;

  private int agility;
  private int strength;
  private int defence;

  public PNJ(String name)
  {
    this.name = name;
  }

  public void move(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void caracteristique(int strength, int defence, int agility)
  {
    this.agility  = agility;
    this.defence  = defence;
    this.strength = strength;
  }

  public abstract Actions meet(People people); //TODO à modier pour mettre un event

  @Override
  public int getAgility() {
    return agility;
  }

  @Override
  public int getDefence() {
    return defence;
  }

  @Override
  public int getStrength() {
    return strength;
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

  @Override
  public void attack() {

  }

  public void update(Events evt){}

  /**
   * This method allows to say the probability of dodge the other attack
   * @return the probability of dodge
   */
  @Override
  public double dodge() /*esquive*/
  {
    return Math.min((agility/100),0.75);
  }

}
