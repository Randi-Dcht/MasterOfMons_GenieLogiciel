package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Objects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class HelpMe extends UnderQuest
    {
      public HelpMe(Quest q,int nb)
      {
        super("HelpMe",nb,q);
      }

      public void evenActivity()
      {
        //System.out.println("UnderQuest BattleForPlace");
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
