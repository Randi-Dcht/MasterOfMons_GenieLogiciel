package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notification;

import java.io.Serializable;

/**
*This abstract class allows define a no player, it is a character pilot by computer.
*@author Randy Dauchot (étudiant en Sciences informatique Umons)
*/

public abstract class Mobile extends Character implements Serializable
{
  final String name;

  public Mobile(String name)
  {
    super(Type.beefy); //TODO changer
    this.name = name;
  }

  public Actions meet(People people)
  {
    return null;
  }//TODO à modier pour mettre un event


  public void update(Notification notify){}

}
