package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;

/**
 * Cette classe permet d'intancier un Mobile contolé par ordinateur qui a besoin d'aide pour étudier
 * @author Randy Dauchot (étudiant en Science Informatique)
 * */
public class StudPNJ extends Mobile
{
  public StudPNJ(Bloc playerBloc,Type playerType)
  {
    super("Bagarre",playerBloc,playerType);
  }

  @Override
  public Actions getAction() {
    return Actions.NeedHelp;
  }
}
