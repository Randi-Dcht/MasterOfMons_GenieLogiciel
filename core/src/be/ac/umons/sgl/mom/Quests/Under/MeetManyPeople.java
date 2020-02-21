package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

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
