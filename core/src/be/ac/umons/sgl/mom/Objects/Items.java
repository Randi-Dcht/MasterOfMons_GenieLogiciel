package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

/**
*Cette classe permet d'implémente les objet que le personnage pourra prendre lors de parcours
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public abstract class Items
{
  protected double positionX;
  protected double positionY;
  protected boolean visible = true;
  final String name;

  public Objet(double x,double y,String name)
  {
    positionX = x;
    positionY = y;
    this.name = name;
  }

  public void visibly()
  {
    visible = false;
  }

  public abstract void used(People pp);
  public abstract void make();
  public abstract double getObsolete();

}
