package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Quests.Quest;

public class HelpMe extends UnderQuest
    {
      public HelpMe(Quest q,int nb)
      {
        super("HelpMe",nb,q);
      }


      @Override
      public void evenActivity(Notification notify) {
      }

      private void goToPnj(){}
      private void percentPassPnj(){}
      private void itemsHelpMe(){}
      private void studyWithPnj(){}

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
