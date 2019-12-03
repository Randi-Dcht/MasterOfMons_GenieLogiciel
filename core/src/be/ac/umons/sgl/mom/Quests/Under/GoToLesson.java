package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToLesson extends UnderQuest
    {
      //final Quest[] q = new Quest[1];
      final Quest[] q = {};

      public GoToLesson(Quest quest,int nb)
      {
        super("GoToLesson",nb,quest);
        //q[0] =  new FollowLesson(this,100);
      }


      @Override
      public void evenActivity() {

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
