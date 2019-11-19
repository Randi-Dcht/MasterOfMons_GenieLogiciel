package be.ac.umons.sgl.mom.Objects.Under;
import be.ac.umons.sgl.mom.Objects.*;

public class BattleForPlace extends UnderQuest
    {
      public BattleForPlace(Quest q)
      {
        super("BattleForPlace",25,q);
      }

      public void evenActivity()
      {
        //System.out.println("UnderQuest BattleForPlace");
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
