package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Quests.Quest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

public class BattlePeople extends UnderQuest
{

    public BattlePeople(String name, double max, Quest master)
    {
        super(name, max, master);
    }

    @Override
    public void evenActivity() {

    }



    @Override
    public Quest[] getSubQuests() {
        return new Quest[0];
    }

    @Override
    public int getTotalSubQuestsNumber() {
        return 0;
    }
}