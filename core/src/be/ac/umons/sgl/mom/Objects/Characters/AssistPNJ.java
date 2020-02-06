package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;

public class AssistPNJ extends Mobile
{
  public AssistPNJ()
  {
    super("aider les autres");
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
