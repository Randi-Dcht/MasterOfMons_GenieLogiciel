package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

public class DisplacementMons extends MasterQuest
{
    final UnderQuest[] underQuests = {};
    public DisplacementMons(People people, MasterQuest before)
    {
        super(before,people, Bloc.Extend);
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