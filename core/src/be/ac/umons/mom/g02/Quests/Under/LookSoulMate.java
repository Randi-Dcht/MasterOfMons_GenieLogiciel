package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

public class LookSoulMate extends UnderQuest
    {
      public LookSoulMate(Quest q, int nb, People people)
      {
        super("LookSoulMate",nb,q,people);
      }


      @Override
      public void evenActivity(Notification notify) {
      }

      private void randomChooseOther(){}
      private void timeForSaoulMate(){}
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
