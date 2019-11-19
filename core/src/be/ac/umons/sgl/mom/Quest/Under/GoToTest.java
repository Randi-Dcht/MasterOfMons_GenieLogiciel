package be.ac.umons.sgl.mom.Objects.Under;
import be.ac.umons.sgl.mom.Objects.*;

public class GoToTest extends UnderQuest
    {
      public GoToTest(Quest q)
      {
        super("GoToTest",25,q);
      }
      public void evenActivity()
      {
        //System.out.println("UnderQuest GoToTest");
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
