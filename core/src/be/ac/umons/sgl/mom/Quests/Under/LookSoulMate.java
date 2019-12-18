package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class LookSoulMate extends UnderQuest
    {
      public LookSoulMate(Quest q, int nb)
      {
        super("LookSoulMate",nb,q);
      }


      @Override
      public void evenActivity() {
      }

      private void randomChooseGirl(){}
      private void timeForCouple(){}
      private void checkGirl(){}
      private void goToHerMons(){}

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
