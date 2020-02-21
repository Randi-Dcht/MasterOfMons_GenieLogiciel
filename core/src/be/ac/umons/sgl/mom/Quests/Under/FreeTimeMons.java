package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

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
