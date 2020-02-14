package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Quests.Quest;

public class FollowLesson extends UnderQuest
    {
      //final

      public FollowLesson(Quest q,int nb)
      {
        super("FollowLesson",nb,q);
      }

      @Override
      public void evenActivity(Notification notify) {

      }

      private void checkLesson(){}
      private void StudyAtKot(){}
      private void helpByItems(){}
      private void workTeam(){}

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
