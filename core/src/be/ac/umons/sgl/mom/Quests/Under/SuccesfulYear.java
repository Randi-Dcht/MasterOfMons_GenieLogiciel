package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Objects.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class SuccesfulYear extends UnderQuest
    {
      public SuccesfulYear(Quest q,int nb)
      {
        super("SuccesfulYear",nb,q);
      }


      @Override
      public void evenActivity(Actions action) {

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
