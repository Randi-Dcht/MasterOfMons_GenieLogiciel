package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;

/**
 * Cette classe permet d'intancier un Mobile contolé par ordinateur qui a besoin d'aide pour étudier
 * @author Randy Dauchot (étudiant en Science Informatique)
 * */
public class StudPNJ extends Mobile
{
  public StudPNJ(Bloc playerBloc,MobileType type)
  {
    super("Bagarre",playerBloc,type);
  }

  @Override
  public Actions getAction()
  {
    return Actions.Dialog;
  }
}
