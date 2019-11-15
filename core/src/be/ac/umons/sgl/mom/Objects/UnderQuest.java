package be.ac.umons.sgl.mom.Objects;
import java.util.*;
/**
*Cette classe définit ce qu'est une quête de bachelier 1
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public abstract class UnderQuest implements Quest
{
/*Nom de la sous quête*/
  final String name;
/*pourcentage maximal que peut avoir cette sous quête*/
  final double percentMax;
/*état de la quête pour le moments*/
  protected double progress;
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

  public abstract void evenMap();
  public abstract Quest[] getSubQuests();
  public String getName()
  {
    return name;
  }
  public abstract int getTotalSubQuestsNumber();
}
