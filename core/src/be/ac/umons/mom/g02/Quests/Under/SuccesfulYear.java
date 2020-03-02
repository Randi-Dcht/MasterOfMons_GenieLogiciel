package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

public class SuccesfulYear extends UnderQuest
    {
      public SuccesfulYear(Quest q, int nb, People people)
      {
        super("SuccesfulYear",nb,q, people);
      }


      @Override
      public void evenActivity(Notification notify) {
      }

      private void goToStudy(){}
      private void goToLearn(){}
      private void myCourse(){}

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
