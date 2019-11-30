package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Objects.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class BattleForPlace extends UnderQuest
    {
      public BattleForPlace(Quest q,int nb)
      {
        super("BattleForPlace",nb,q);
      }

      public void evenActivity(Actions action)
      {
        //System.out.println("UnderQuest BattleForPlace");
      }

      public Quest[] getSubQuests()
      {
        Quest[] q = {};
        return q;
      }

      private void check(){}

      public int getTotalSubQuestsNumber()
      {
        return getSubQuests().length;
      }
    }
