package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

public class FreeTimeMons extends UnderQuest
    {
      public FreeTimeMons(Quest q, int nb, People people)
      {
        super("FreeTimeMons",nb,q,people);
      }

      @Override
      public void evenActivity(Notification notify) {
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
