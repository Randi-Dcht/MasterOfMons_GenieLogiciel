package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Supervisor;
import be.ac.umons.sgl.mom.Quests.Quest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import java.io.Serializable;
import java.util.ArrayList;


/**
 *La MasterQuest est une classe abstraite qui contient elle même des underQuest.
 *Chaque joueur peut jouer sur un seul MasterQuest à la fois.
 *Pour réusir une MasterQuest, on doit réusir un maximun de underQuest qui font augmenter le % de réussite.
 *Chaque MasterQuest possède un parent et/ou un enfant.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public abstract class MasterQuest implements Quest,Serializable,Observer
{
    /*place in the list of MasterQuest*/
    protected static int numberQuest = 1;
    /*interrogation qui doit encore passer*/
    protected ArrayList<Lesson> interrogation = new ArrayList<Lesson>(); //les interrogations qui doit encore passer.
    /*objet disponible dans son sac à dos*/
    protected ArrayList<Items> availableObject = new ArrayList<Items>(); //objet disponible sur la maps pour lui prendre
    /*the percent to advance this quest*/
    protected double percent = 0;
    /*the percent of maximum of this quest to succeed this*/
    protected double maxPercent;
    /*the masterQuest who is before this (parent MasterQuest)*/
    final MasterQuest before;
    /*the people who play this masterQuest*/
    final People people;
    /*a list of lesson for this masterQuest*/
    protected Lesson[] course; //cours que le personnage doit prendre pour cette quête
    /*the next masterQuest after this (son MasterQuest)*/
    protected MasterQuest after = null;
    /*it is a list of underquest of this quest (goal)*/
    protected UnderQuest[] underQuest;
    /*masterQuest is finished*/
    protected boolean finished = false;
    /*The bloc of this MasterQuest*/
    protected Bloc bloc;
    /*Memory the MasterQuest when the level isn't up*/
    protected MasterQuest memory;
    /*this the instance of the graphic*/
    protected GraphicalSettings graphic;
    /***/
    protected Difficulty difficulty;


    /**
     * This constructor allows to define a masterQuest
     *@param before who is the before masterQuest
     *@param people who is the people who play this masterQuest
     */
    public MasterQuest(MasterQuest before, People people, Bloc bloc, GraphicalSettings graphic, Difficulty difficulty)
    {
        this.before = before;
        numberQuest++;
        this.people = people;
        this.bloc = bloc;
        this.graphic = graphic;
        this.maxPercent = difficulty.getMaxPercent();
        this.difficulty = difficulty;
    }


    /**
     * This method allows to add a new MasterQuest after this.
     * This method check the level of player and if this quest is finished
     * @param after is the next MasterQuest.
     */
    public void newQuest(MasterQuest after)
    {
        if(finished)
        {
            if (after.bloc.getMinPeople() <= people.getLevel())
            {
                this.after = after;
                people.newQuest(after);
            }
            else
            {
                SuperviserNormally.getSupervisor().getEvent().add(Events.UpLevel,this);
                memory = after;
            }
        }
    }


    /**
     * This method allows to define the next MasterQuest
     */
    public abstract void nextQuest();


    /**
     *This method allows to add a new underQuest in this Quest
     * Only the underQuest
     * @param list is a list of the underQuest
     */
    public void addUnderQuest(UnderQuest ... list)
    {
        underQuest = list;
    }


    /**
     * This method returns if this quest is finished
     * @return boolean if the quest is finished
     */
    public boolean isFinished()
    {
        return finished;
    }


    /**
     * this method return a list of course for this quest
     *@return list of lesson
     */
    public Lesson[] getLesson()
    {
        return course;
    }


    /**
     * This method allows to add the lesson in this Quest
     * @param course is a list of the course
     */
    protected void ObligationLesson(Lesson[] course)
    {
        this.course = course;
    }


    /**
     * This method returns the percent of advancement between 0 and 1.
     * @return the number between 0 and 1.
     */
    public double getProgress()
    {
        return (percent/maxPercent);
    }


    /**
     *this method return the percent of advancement of this Quest
     *@return percent of advancement
     */
    public double getAdvancement()
    {
        return percent;
    }


    /**
     * This method returns the name of this Quest
     * @return the name of the quest
     */
    public abstract String getName();


    /**
     * This method allows to know if this quest is active
     * @return boolean if the quest is active
     */
    public boolean isActive()
    {
        return !finished;
    }


    /**
     *This method return the number of underQuest for this Quest
     * @return number of underQuest
     */
    public int getTotalSubQuestsNumber()
    {
        return getTotalSubQuestsNumber(true);
    }


    /**
     *This method allows to say if the underQuest is present
     * @param main is the boolean for say the quest is recursive
     */
    protected int getTotalSubQuestsNumber(boolean main)
    {
        return underQuest.length + (main ? 1 : 0);
    }


    /**
     *This method allows to add the lesson who are missed.
     *@param list is a list of the lesson who is missed
     */
    public void retake(ArrayList<Lesson> list)
    {
        interrogation.addAll(list);
    }


    /**
     *This method allows to notify the underQuest, there are changes in the people or other in maps.
     * @param notify is a notification of change
     */
    public void eventMaps(Notification notify)
    {
        for(UnderQuest uq : underQuest)
        {
            uq.evenActivity(notify);
        }
    }


    /**
     *This method allows to add the progress to this quest and check if it finishes
     *@param many is the percent to add (succeeded)
     */
    public void addProgress(double many)
    {
        if(!finished)
        {
            percent = percent + many;
            if(percent >= maxPercent)
            {
                finished = true;
                nextQuest();
                Supervisor.changedQuest();
            }
        }
    }


    /**
     *This method allows to give the before masterQuest
     *@return before who is a before masterQuest
     */
    public MasterQuest getParent()
    {
        return before;
    }


    /**
     *This method allows to give the masterQuest after this
     @return after who is the next masterQuest
     */
    public MasterQuest getChildren()
    {
        return after;
    }


    /**
     *This method allows to give a people who play this masterQuest
     *@return people who play
     */
    public People getPlayer()
    {
        return people;
    }


    /**
     *This method allows to give the interrogation of this masterQuest
     *@return a list of the Lesson where there are the interrogations
     */
    public ArrayList<Lesson> getInterrogation()
    {
        return interrogation;
    }


    /**
     *This method return the under quest of this, this underQuest is the goal.
     *@return a list of Quest
     */
    public Quest[] getSubQuests()
    {
        return underQuest;
    }


    /**
     * This method return the maximum percent of this Quest
     * @return number of maximum percent
     */
    public double getMaximun()
    {
        return maxPercent;
    }


    /**
     * This method allows to receive the notification and update this quest
     * @param notify is the notification with event
     */
    @Override
    public void update(Notification notify)
    {
        eventMaps(notify);
        if(notify.getEvents().equals(Events.UpLevel) && memory != null)
        {
            this.after = memory;
            people.newQuest(memory);
            memory = null;
           // SuperviserNormally.getSupervisor().getEvent().remove(notify.getEvents(),this);TODO error the current time action
        }
    }


    /**
     *This method allow to say what is this quest
     *@return a goal (=question) of this quest
     */
    public abstract String question();


    /**
     * This method return the all items for this quest
     * @return list of items
     */
    public abstract ArrayList<Items> whatItem();


    /**
     * This method return the all mobile and PNJ for this quest
     * @return list of mobile
     */
    public abstract ArrayList<Mobile> whatMobile();


    /**
     * This method return the place for this Quest
     * @return list of the place
     */
    public abstract Place[] whatPlace();


    /**
     * This method return the year of school of this people in the university
     * @return bloc of people
     */
    public Bloc getBloc()
    {
        return bloc;
    }
}
