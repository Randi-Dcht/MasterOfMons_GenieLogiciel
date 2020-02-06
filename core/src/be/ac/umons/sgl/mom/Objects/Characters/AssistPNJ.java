package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Type;

public class AssistPNJ extends Mobile
{
  public AssistPNJ(Bloc playerBloc,Type playerType)
  {
    super("aider les autres",playerBloc,playerType);
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
