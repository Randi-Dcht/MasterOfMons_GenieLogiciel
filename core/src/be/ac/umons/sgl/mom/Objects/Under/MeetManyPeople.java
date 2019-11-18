package be.ac.umons.sgl.mom.Objects.Under;
import be.ac.umons.sgl.mom.Objects.*;

public class MeetManyPeople extends UnderQuest
    {
      public MeetManyPeople(Quest q)
      {
        super("MeetManyPeople",25,q);
      }
      public void evenActivity()
      {
        //System.out.println("UnderQuest MeetManyPeople");
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
