package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

public class MovingInMons extends MasterQuest
{
    final UnderQuest[] underQuests = {};
    public MovingInMons(People people, MasterQuest before, GraphicalSettings graphic)
    {
        super(before,people, Bloc.Extend,graphic);
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

    public void meetOther(People people)
    {

    }

    @Override
    public String question()
    {
        return "Si tu accepte ta quête sera de parcourir la ville de Mons en défiant ton adversaire";
    }

    @Override
    public Items[] whatItem() {
        return new Items[0];
    }

    @Override
    public Mobile[] whatMobile() {
        return new Mobile[0];
    }

    @Override
    public void update(Notification notify) {

    }
}