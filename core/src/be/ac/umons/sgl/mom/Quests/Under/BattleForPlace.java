package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class BattleForPlace extends UnderQuest
    {
      public BattleForPlace(Quest q,int nb)
      {
        super("BattleForPlace",nb,q);
      }


      @Override
      public void evenActivity() {
      }

      private void attackOther(){}
      private void firstInPlace(){}
      private void chooseGoodRoad(){}

      public Quest[] getSubQuests()
      {
        return new Quest[]{};
      }

      private void check(){}

      public int getTotalSubQuestsNumber()
      {
        return getSubQuests().length;
      }
    }
