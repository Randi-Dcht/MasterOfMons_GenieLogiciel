package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;
/**
 * Cette classe permet d'intancier un PNJ contolé par ordinateur qui a besoin d'aide pour étudier
 * @author Randy Dauchot (étudiant en Science Informatique)
 * */
public class StudPNJ extends PNJ
{
  public StudPNJ()
  {
    super("Bagarre");
  }

  public Actions meet(People people)
  {
    return null;
  }
}
