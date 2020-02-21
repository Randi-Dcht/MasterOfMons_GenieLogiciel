package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToLesson extends UnderQuest
    {
      //final Quest[] q = new Quest[1];
      final Quest[] q = {};

      public GoToLesson(Quest quest, int nb, People people)
      {
        super("GoToLesson",nb,quest,people);
        //q[0] =  new FollowLesson(this,100);
      }


      @Override
      public void evenActivity(Notification notify) {

      }

      private void checkExam(){}
      private void GoToPlaceStudy(){}

      public Quest[] getSubQuests()
      {
        return q;
      }

      public int getTotalSubQuestsNumber()
      {
        return q.length;
      }
    }
