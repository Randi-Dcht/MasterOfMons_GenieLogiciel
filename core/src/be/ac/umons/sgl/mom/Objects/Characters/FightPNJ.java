package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;

public class FightPNJ extends Mobile
{
  public FightPNJ()
  {
    super("Bagarre");
  }

  public Actions meet(People people)
  {
    return null;
  }

  @Override
  public Actions getAction()
  {
    return Actions.Attack;
  }
}
