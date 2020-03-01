package be.ac.umons.sgl.mom.Extensions.Dual;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Maps;
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

    /**
     * This method allows to create the list of the items
     */
    @Override
    protected void createListItems() {

    }

    /**
     * This method allows to create the list of the mobiles
     */
    @Override
    protected void createListMobiles() {

    }

    @Override
    public ArrayList<Items> getListItems() {
        return null;
    }

    @Override
    public ArrayList<Mobile> getListPnj() {
        return null;
    }

    /**
     * This method return the maps for this Quest
     *
     * @return list of the maps
     */
    @Override
    public Maps[] getListMaps() {
        return new Maps[0];
    }

    @Override
    public void update(Notification notify) {

    }
}