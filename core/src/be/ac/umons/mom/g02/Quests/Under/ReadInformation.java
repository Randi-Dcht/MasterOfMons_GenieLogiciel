package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Events.Notifications.Notification;

public class ReadInformation extends UnderQuest
    {
      public ReadInformation(Quest q, int nb, People people)
      {
        super("GoToTest",nb,q,people );
      }


      @Override
      public void evenActivity(Notification notify) {

      }

      private void checkPeriod(){}
      private void percentOfGoLesson(){}
      private void percentOfStudies(){}

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
