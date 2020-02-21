package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class BattleForPlace extends UnderQuest
    {
      public BattleForPlace(Quest q, int nb, People people)
      {
        super("BattleForPlace",nb,q,people );
      }


      @Override
      public void evenActivity(Notification notify) {
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
