package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

public class GoToLesson extends UnderQuest
    {

      public GoToLesson(Quest quest, int nb, People people)
      {
        super("GoToLesson",nb,quest,people);
      }


      @Override
      public void evenActivity(Notification notify)
      {

      }

      private void checkExam(){}
      private void GoToPlaceStudy(){}

      public Quest[] getSubQuests()
      {
        return new Quest[]{};
      }

      public int getTotalSubQuestsNumber()
      {
        return 0;
      }
    }
