package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.Type;

public class AssistPNJ extends Mobile
{
  public AssistPNJ(Bloc playerBloc, MobileType type)
  {
    super("aider les autres",playerBloc,type);
  }

  public Actions meet(People people)
  {
    return null;
  }

  @Override
  public Actions getAction()
  {
    return Actions.HelpOther;
  }
}
