package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

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
