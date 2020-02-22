package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;

import java.util.ArrayList;

public class MovingInMons extends MasterQuest
{
    final UnderQuest[] underQuests = {};
    public MovingInMons(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people, Bloc.Extend,graphic,difficulty);
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
    public ArrayList<Items> whatItem() {
        return null;
    }

    @Override
    public ArrayList<Mobile> whatMobile() {
        return null;
    }

    /**
     * This method return the place for this Quest
     *
     * @return list of the place
     */
    @Override
    public Place[] whatPlace() {
        return new Place[0];
    }

    @Override
    public void update(Notification notify) {

    }
}