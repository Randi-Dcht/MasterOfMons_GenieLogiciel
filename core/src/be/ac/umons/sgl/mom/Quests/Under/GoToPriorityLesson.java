package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToPriorityLesson extends UnderQuest
    {
      public GoToPriorityLesson(Quest q,int nb)
      {
        super("GoToPriorityLesson",nb,q);
      }


      @Override
      public void evenActivity() {
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
