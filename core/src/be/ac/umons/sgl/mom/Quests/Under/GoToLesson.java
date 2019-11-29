package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Objects.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToLesson extends UnderQuest
    {
      public GoToLesson(Quest q,int nb)
      {
        super("GoToLesson",nb,q);
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
