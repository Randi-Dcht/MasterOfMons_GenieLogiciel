package be.ac.umons.sgl.mom.Objects;
import java.util.*;

/**
*La UnderQuest est une sous classe d'une MasterQuest ou d'elle-même.
*Chaque underQuest est un objectif que le joueur doit faire.
*Le joueur ne doit pas forcément réussir à 100% une underQuest pour réussir la MasterQuest
*Chaque underQuest sont indépendant et existe en même temps dans la MasterQuest.
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public abstract class UnderQuest implements Quest
{
/*Nom de la sous quête*/
  final String name;
/*pourcentage maximal que peut avoir cette sous quête*/
  final double percentMax;
/*état de la quête pour le moments*/
  protected double progress = 0;
/*pour savoir si cette sous quête est terminer*/
  protected boolean finish = false;
/*pour savoir */
  protected Quest master;

  public UnderQuest(String name,double max,Quest master)
  {
    this.name   = name;
    percentMax  = max;
    this.master = master;
  }

  public double getProgress()
  {
    return (progress/percentMax);
  }

  private void finished()
  {
    if(progress >= percentMax)
     finish = true;
  }

  public void addProgress(double many)
  {
    progress = progress + many;
    master.addProgress(many);
  }

  public double getAdvancement()
  {
    return progress;
  }

  public boolean isActive()
  {
    return !finish;
  }

  public boolean isFinished()
  {
    return finish;
  }

  public abstract void evenActivity();
  public abstract Quest[] getSubQuests();
  public String getName()
  {
    return name;
  }
  public abstract int getTotalSubQuestsNumber();
}
