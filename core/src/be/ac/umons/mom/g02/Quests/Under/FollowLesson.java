package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class define the goals if the player go to follow the lesson on auditory
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class FollowLesson extends UnderQuest implements FrameTime
{
    /**
     * This is a memory to the actual course of the player
     */
    private Course nowCourse;


        /**
         * This constructor define the class of follow course
         * @param master is the master class of this
         * @param max    is the maximum percent of this class
         * @param people is the player when play the underQuest
         */
        public FollowLesson(Quest master, int max, People people)
        {
           super("FollowLesson",max,master,people);
        }


        /**
         * This method allows to receive the notification of the game
         * @param notify is the notification in the game
         */
      @Override
      public void evenActivity(Notification notify)
      {
          if (notify.getEvents().equals(Events.UseItems) && notify.bufferNotEmpty())
              helpByItems(((UseItem)notify).getBuffer());
      }


      /**
       * This method check if the player go to learn the lesson in the auditory
       */
      private void checkLesson()
      {
          if (nowCourse.isGo() && (nowCourse.getDate().getMin()+15) >= Supervisor.getSupervisor().getTime().getDate().getMin())
              addProgress(0.6);
      }


      /**
       * This method check if the player uses the item to progress in this Quest
       */
      private void helpByItems(Items item)
      {
          if (item.getClass().equals(OldExam.class))
              addProgress(1);
          if (item.getClass().equals(PaperHelp.class))
              addProgress(0.6);
      }


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


    /**
     * This method allows to give the time between two frames
     * This method allows to refresh the class every call
     * @param dt us the time between two frame
     */
    @Override
    public void update(double dt)
    {
        if (nowCourse != null)
            checkLesson();
    }


    /**
     * Explain the goal of this underQuest to succeed this or not
     * @return the explication of underQuest
     */
    @Override
    public String explainGoal()
    {
        return "NONE";
    }
}
