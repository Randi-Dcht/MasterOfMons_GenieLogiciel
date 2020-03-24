package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Quests.Quest;


/**
 * This class define the goals when the people go to the priority lesson of this years
 * @author Umons_Group_2_ComputerScience
 */
public class GoToPriorityLesson extends UnderQuest
    {

      /**
       * This constructor define the class of check go to course
       * @param master is the master class of this
       * @param max    is the maximum percent of this class
       * @param people is the player when play the underQuest
       */
      public GoToPriorityLesson(Quest master, int max, People people)
      {
        super("GoToPriorityLesson",max,master,people);
      }


      /**
       * This method allows to receive the notification of the game
       * @param notify is the notification in the game
       */
      @Override
      public void evenActivity(Notification notify)
      {
      }


      /***/
      private void checkLesson()
      {}


      /***/
      private void studyAtKot()
      {}


      /***/
      private void checkGoToCourse(){}


      /**
       * This method return the list of the under quest of this
       * @return list of under quest
       */
      public Quest[] getSubQuests()
      {
        return new Quest[]{};
      }


      /**
       * This method return the number of recursion on under this
       * @return number recursion
       */
      public int getTotalSubQuestsNumber()
      {
        return 0;
      }
    }
