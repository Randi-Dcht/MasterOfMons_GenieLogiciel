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
public abstract class PNJ extends Character implements Serializable
{
  final String name;

  public PNJ(String name)
  {
    super(Type.beefy); //TODO changer
    this.name = name;
  }

  public abstract Actions meet(People people); //TODO à modier pour mettre un event


  @Override
  public void attack() {

  }

  public void update(Events evt){}

}
