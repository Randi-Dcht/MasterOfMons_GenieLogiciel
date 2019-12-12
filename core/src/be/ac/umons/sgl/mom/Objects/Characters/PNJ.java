package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.People;

import java.io.Serializable;

/**
*Cette classe permet de définir un personnage qui est piloté par l'ordianteur
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public abstract class PNJ implements Serializable, Attack
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

  @Override
  public double dodge() {
    return 0;
  }

  @Override
  public double recovery() {
    return 0;
  }

  @Override
  public void attack() {

  }
}
