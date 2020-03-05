package be.ac.umons.mom.g02.Quests.Under;


import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;

public class HelpMe extends UnderQuest
    {
      /**
       * This constructor allows to define a goals of help an other people (PNJ)
       * @param q is the above this
       * @param nb is the maximum percent to succeed this
       */
      public HelpMe(Quest q, int nb, People people)

      {
        super("HelpMe",nb,q,people);
      }


      /**
       * This method allows to give the activity of the player in the game
       * @param notify is the notification in the game
       */
      @Override
      public void evenActivity(Notification notify)
      {
      }


      /**
       * This method check if the people go to speak with a PNJ who have help
       * @param mobile is the mobile who have help
       */
      private void goToPnj(Mobile mobile)
      {}



      /**
       * This method check if the people give a syntheses to help the PNJ in the game
       */
      private void percentPassPnj()
      {}


      /**
       * Ask to the PNJ if he want to study with the people
       */
      private void studyWithPnj()
      {}


      /**
       * This method allows to give the goals Quest of this UnderQuest
       * @return list of underQuest
       */
      public Quest[] getSubQuests()
      {
        Quest[] q = {};
        return q;
      }


      /**
       * This method allows to say the number of the underQuest under this
       * @return the number of under
       */
      public int getTotalSubQuestsNumber()
      {
        return 0;
      }
    }
