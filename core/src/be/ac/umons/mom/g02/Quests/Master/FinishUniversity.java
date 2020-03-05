package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Gun;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Quests.Under.GoToLesson;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Quests.Under.WriteMemory;
import com.badlogic.gdx.maps.Map;

import java.util.ArrayList;

/**
 *This class define the five year in the University of Mons with the goals.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class FinishUniversity extends MasterQuest {
    /*This tab is a list of the course of the people for this year*/
    final UnderQuest[] underQuest = {/*TODO rajouter le combat contre le boss (ca rapporte 15) sans oublier de mettre le boss dans les mobiles */ new GoToLesson(this, 5, people), new WriteMemory(this, people)}; //il manquait le joueur


    /**
     * This constructor define a Master 1 of Umons
     *
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public FinishUniversity(People people, MasterQuest before, GraphicalSettings grahic, Difficulty difficulty) {
        super(before, people, Bloc.MA2, grahic, difficulty);
        addUnderQuest(underQuest);
    }


    /**
     * This method allows to define to next Quest
     */
    public void nextQuest() {/*END*/}


    /**
     * This method allows to say the ask of this MasterQuest
     *
     * @return a question who is a string
     */
    public String question() {
        return SuperviserNormally.getSupervisor().getGraphic().getStringFromId("answerMasterSecond");
    }

    @Override
    protected void createListItems() {
        // TODO
    }

    @Override
    protected void createListMobiles() {
        // TODO

    }

    @Override
    public Maps[] getListMaps() {
        return new Maps[]{Maps.Nimy, Maps.DeVinci, Maps.GrandAmphi};
    }


    /***/
    public ArrayList<Items> whatItem() {
        ArrayList<Items> list = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            list.add(new Pen());
        for (int i = 0; i < 2; i++)
            list.add(new Gun());

        return list;
    }


    /***/
    public ArrayList<Mobile> whatMobile() {
        ArrayList<Mobile> list = new ArrayList<>();
        return list;
    }


    /**
     * This method allows to return the name of MasterQuest
     *
     * @return name who is a string
     */
    public String getName() {
        return SuperviserNormally.getSupervisor().getGraphic().getStringFromId("nameMasterSecond");
    }
}

