package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

public class MovingInMons extends MasterQuest
{
    final UnderQuest[] underQuests = {};
    public MovingInMons(People people, MasterQuest before)
    {
        super(before,people);
        addUnderQuest(underQuest);
    }

    @Override
    public void nextQuest()
    {

    }

    @Override
    public String getName()
    {
        return "Displacement in the city of Mons";
    }

    @Override
    public void meetOther(PNJ other)
    {

    }

    public void meetOther(People people)
    {

    }

    @Override
    public String question()
    {
        return "Si tu accepte ta quête sera de parcourir la ville de Mons en défiant ton adversaire";
    }
}