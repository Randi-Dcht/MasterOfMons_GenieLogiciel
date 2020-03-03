package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.*;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.ChooseSubject;
import be.ac.umons.mom.g02.Quests.Under.GoToPriorityLesson;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;

import java.util.ArrayList;
import java.util.Random;

/**
 *This class define the four year in the University of Mons with the goals.
 *@author Maxime Renversez  (étudiant en Sciences informatique)
 */
public class PreparedCompany extends MasterQuest
{
    /*This tab is a list of the course of the people for this year*/
   //final Lesson[] lesson ={Lesson.res2,Lesson.lecred,Lesson.proglog,Lesson.bda,Lesson.comp,Lesson.algbio,Lesson.sofev,Lesson.angl};
    /*This is the goals of this MasterQuest*/
    final UnderQuest[] underQuest = {/*new Traineeship(this),*/new GoToPriorityLesson(this,50,people),new ChooseSubject(this,people)};


    /**
     * This constructor define a Master 1 of Umons
     * @param people who is the people goes to course
     * @param before who is the MasterQuest before them, (null or MasterQuest)
     */
    public PreparedCompany(People people, MasterQuest before, GraphicalSettings graphic, Difficulty difficulty)
    {
        super(before,people, Bloc.MA1,graphic,difficulty);
        addUnderQuest(underQuest);
    }

    /**
     * This method allows to define to next Quest
     */
    public void nextQuest()
    {
        newQuest(new FinishUniversity(people,this,graphic,difficulty));
    }


    /**
     *This method allows to say the ask of this MasterQuest
     * @return a question who is a string
     */
    public String question()
    {
        return graphic.getStringFromId("answerMasterOne");
    }

    @Override
    protected void createListItems() {
        listItems = new ArrayList<>();
        Maps[] type = Maps.values();
        for (int i=0 ; i < 4; i++) {
            Items pen=new Pen();
            Items gun=new Gun();
            pen.setMaps(Maps.GrandAmphi);
            gun.setMaps(Maps.Nimy);
            listItems.add(pen);
            listItems.add(gun);
        }
        for (int i=0;i<4;i++)
        {
            Items pen=new Pen();
            Items gun=new Gun();
            Items paper=new PaperHelp();
            pen.setMaps(Maps.DeVinci);
            gun.setMaps(Maps.Mons);
            paper.setMaps(type[new Random().nextInt(type.length)]);
            listItems.add(pen);
            listItems.add(gun);
            listItems.add(new PaperHelp());
        }
        Items synthesis=new Synthesis();
        Items sport=new Sportswear();
        sport.setMaps(type[new Random().nextInt(type.length)]);
        synthesis.setMaps(type[new Random().nextInt(type.length)]);
        listItems.add(synthesis);
        listItems.add(sport);
    }

    @Override
    protected void createListMobiles() {
        listMobs = new ArrayList<>();
        MobileType[] type = MobileType.values();
        Maps[] typemap = Maps.values();
        for(int i=0; i < 30;i++) {
            Mobile mb = new Mobile("Student",getBloc(),type[new Random().nextInt(type.length)], Actions.Dialog,NameDialog.Lambda);
            mb.setMaps(typemap[new Random().nextInt(type.length)]);
            listMobs.add(mb);
            Mobile mob=new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda);
            mob.setMaps(typemap[new Random().nextInt(type.length)]);
            listMobs.add(mob);
            Mobile mobi=new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda);
            mobi.setMaps(typemap[new Random().nextInt(type.length)]);
            listMobs.add(mobi);
            Mobile mobil=new Mobile("Fight",getBloc(),MobileType.Athletic, Actions.Attack,NameDialog.Lambda);
            mobil.setMaps(typemap[new Random().nextInt(type.length)]);
            listMobs.add(mobil);
        }
    }

    /**
     * This method return all of the place for this quest
     * @return the different place for this quest
     */
    @Override
    public Maps[] getListMaps() {
        return new Maps[]{Maps.Nimy, Maps.DeVinci, Maps.Mons,Maps.GrandAmphi};
    }


    /**
     * This method allows to return the name of MasterQuest
     * @return name who is a string
     */
    public String getName()
    {
        return graphic.getStringFromId("nameMasterOne");
    }
}

