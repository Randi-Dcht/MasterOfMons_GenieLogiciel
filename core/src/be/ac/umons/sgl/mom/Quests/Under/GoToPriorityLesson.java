package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToPriorityLesson extends UnderQuest
    {
      public GoToPriorityLesson(Quest q, int nb, People people)
      {
        super("GoToPriorityLesson",nb,q,people);
      }


      @Override
      public void evenActivity(Notification notify) {
      }

      private void checkLesson(){}
      private void studyAtKot(){}
      private void checkGoToCourse(){}

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
