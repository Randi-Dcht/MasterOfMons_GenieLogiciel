package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class FreeHeadInMons extends UnderQuest
    {
      public FreeHeadInMons(Quest q,int nb)
      {
        super("FreeHeadInMons",nb,q);
      }

      @Override
      public void evenActivity() {
      }

      private void goToMons(){}
      private void goToDrink(){}
      private void friendPnj(){}

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
