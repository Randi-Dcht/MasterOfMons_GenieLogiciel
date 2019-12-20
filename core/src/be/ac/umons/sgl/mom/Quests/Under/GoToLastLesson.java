package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Quests.Quest;

public class GoToLastLesson extends UnderQuest
{
    public GoToLastLesson(Quest master) {
        super("goToLastLesson", 95, master);
    }

    @Override
    public void evenActivity()
    {
        /*code ici*/
    }

    @Override
    public Quest[] getSubQuests()
    {
        /*code ici*/
        return new Quest[0];
    }

    @Override
    public int getTotalSubQuestsNumber()
    {
        /*code ici*/
        return 0;
    }
}
