package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToTest extends UnderQuest
    {
      public GoToTest(Quest q,int nb)
      {
        super("GoToTest",nb,q);
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
