package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import java.io.Serializable;
import be.ac.umons.sgl.mom.Quests.Quest;

/**
*Cette classe permet de définir un personnage qui est piloté par l'ordianteur
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public abstract class PNJ implements Serializable
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

  public abstract void meet(People people);

}
