package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;

public class MovingInMons extends MasterQuest
{
    final UnderQuest[] underQuests = {};
    protected People playerTwo;

    public MovingInMons(People playerOne,People playerTwo, Difficulty difficulty)
    {
        super(null,playerOne, Bloc.Extend,difficulty);
        this.playerTwo = playerTwo;
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