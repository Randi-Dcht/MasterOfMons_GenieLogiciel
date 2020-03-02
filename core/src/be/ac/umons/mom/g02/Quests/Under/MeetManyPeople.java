package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;

public class MeetManyPeople extends UnderQuest
    {
      public MeetManyPeople(Quest q, int nb, People people)
      {
        super("MeetManyPeople",nb,q,people);
      }


      @Override
      public void evenActivity(Notification notify) {

      }

      private void meetPNJ(){}
      private void goToSpeak(){}
      private void helpByPhone(){}

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
