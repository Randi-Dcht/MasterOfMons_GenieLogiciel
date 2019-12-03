package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class CheckStudy extends UnderQuest
{
    final Quest[] list = {};

    public CheckStudy(Quest master,double max)
    {
        super("CheckStudy", max, master);
    }

    @Override
    public void evenActivity() {
    }

    public void studyAtKot(){}
    public void goToStudy(){}

    @Override
    public Quest[] getSubQuests()
    {
        return list;
    }

    @Override
    public int getTotalSubQuestsNumber()
    {
        return list.length;
    }
}
