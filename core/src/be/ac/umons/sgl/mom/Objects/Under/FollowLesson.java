package be.ac.umons.sgl.mom.Objects.Under;
import be.ac.umons.sgl.mom.Objects.*;

public class FollowLesson extends UnderQuest
    {
      public FollowLesson(Quest q)
      {
        super("FollowLesson",25,q);
      }
      public void evenActivity()
      {
        //System.out.println("UnderQuest FollowLesson");
      }
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
