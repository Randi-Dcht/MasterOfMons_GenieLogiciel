package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Type;

public class FightPNJ extends Mobile
{
  public FightPNJ(Bloc playerBloc,Type playerType)
  {
    super("Bagarre",playerBloc,playerType);
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
