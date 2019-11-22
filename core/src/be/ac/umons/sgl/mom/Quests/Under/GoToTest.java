package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Objects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToTest extends UnderQuest
    {
      public GoToTest(Quest q,int nb)
      {
        super("GoToTest",nb,q);
      }
      public void evenActivity()
      {
        //System.out.println("UnderQuest GoToTest");
      }
      public Quest[] getSubQuests()
      {
        Quest[] q = {};
        return q;
      }
      public int getTotalSubQuestsNumber()
      {
        return getSubQuests().length;
      }
    }
