package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

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
