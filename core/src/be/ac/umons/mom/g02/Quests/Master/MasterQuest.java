package be.ac.umons.mom.g02.Quests.Master;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Quest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * The MasterQuest is an abstract class which itself contains underQuest.
 * Each player can play only one MasterQuest at a time.
 * To pass a MasterQuest, you must pass a maximum of underQuest which increase the% of success.
 * Each MasterQuest has a parent and / or a child.
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public abstract class MasterQuest implements Quest,Serializable,Observer
{
    /**
     * Maps in the list of MasterQuest
     */
    protected static int numberQuest = 1;
    /**
     * The percent to advance this quest
     */
    protected double percent = 0;
    /**
     * The percent of maximum of this quest to succeed this
     */
    protected double maxPercent;
    /**
     * The masterQuest who is before this (parent MasterQuest)
     */
    protected final MasterQuest before;
    /**
     * The people who play this masterQuest
     */
    protected final People people;
    /**
     * The next masterQuest after this (son MasterQuest)
     */
    protected MasterQuest after = null;
    /**
     * It is a list of underQuest of this quest (goal)
     */
    protected UnderQuest[] underQuest;
    /**
     * MasterQuest is finished
     */
    protected boolean finished = false;
    /**
     * The bloc of this MasterQuest
     */
    protected Bloc bloc;
    /**
     * Memory the MasterQuest when the level isn't up
     */
    protected MasterQuest memory;
    /**
     * This is the difficulty of the quest
     */
    protected Difficulty difficulty;
    /**
     * This list is a list of the Mobiles
     */
    protected ArrayList<Mobile> listMobs;
    /**
     * This list is a list of the items
     */
    protected ArrayList<Items> listItems;


    /**
     * This constructor allows to define a masterQuest
     *@param before who is the before masterQuest
     *@param people who is the people who play this masterQuest
     */
    public MasterQuest(MasterQuest before, People people, Bloc bloc,Difficulty difficulty)
    {
        this.before = before;
        numberQuest++;
        this.people = people;
        this.bloc = bloc;
        this.maxPercent = difficulty.getMaxPercent();
        this.difficulty = difficulty;
    }


    /**
     * This method allows to add a new MasterQuest after this.
     * This method check the level of player and if this quest is finished
     * @param after is the next MasterQuest.
     */
    protected void newQuest(MasterQuest after)
    {
        if(finished)
        {
            if (after.bloc.getMinPeople()-difficulty.getUpLevel() <= people.getLevel())
            {
                this.after = after;
                people.newQuest(after);
                Supervisor.getSupervisor().refreshQuest();
            }
            else
            {
                Supervisor.getEvent().add(Events.UpLevel,this);
                memory = after;
            }
        }
    }


    /**
     * This method allows to create the number of mobile with the param
     * @param number   is the number of mobile type
     * @param listAc   is the action of mobile
     * @param listDia  is the dialog of mobile
     * @param listT    is the mobile type of mobile
     * @param listMaps is the maps where the mobile is (Maps or null)
     *
     * @return a list with the all mobile
     */
    public ArrayList<Mobile> createRdMobile(int[] number, MobileType[] listT,Actions[] listAc,NameDialog[] listDia,Maps[] listMaps,boolean itm) throws Exception
    {
        if (listT.length != number.length && listT.length != listAc.length && listAc.length != listDia.length && listDia.length != listMaps.length)
            throw new Exception();
        ArrayList<Mobile> listMb = new ArrayList<>();Mobile mbl;
        for (int i = 0 ; i < number.length ; i++)
        {
            for (int  j = 0; j < number[i]; j++)
            {
                if (listMaps[i] == null)
                    listMb.add(mbl=new Mobile(getBloc(),listT[i],listAc[i],listDia[i]));
                else
                {
                    listMb.add(mbl = new Mobile(getBloc(),listT[i],listAc[i],listDia[i]));
                    mbl.setMaps(listMaps[i]);
                }
                if (itm)
                    addItemToMobile(0,2,mbl);
            }
        }
        return listMb;
    }


    /**
     * This method allows to pass this Quest (#debug#)
     */
    public void passQuest()
    {
        percent  = maxPercent;
        finished = true;
        nextQuest();
        for (UnderQuest under : underQuest)
            under.passQuest();
        Supervisor.getSupervisor().refreshQuest();
    }


    /**
     * This method allows to create the number item
     * @param list is a list of the item class to init
     * @param cmb  is the number of the item
     * @param maps is the maps of the item (Maps or null)
     *
     * @return a list with the all item instance
     */
    protected ArrayList<Items> createListItems(Class<Items>[] list, int[] cmb, Maps[] maps) throws Exception
    {
        if (list.length != cmb.length && maps.length != list.length)
            throw new Exception();
        else
        {
            ArrayList<Items> listI = new ArrayList<>();Items itm;
            for (int j = 0; j < list.length; j++)
            {
                for (int i=0; i <cmb[j];i++ )
                {
                    if (maps[j] == null)
                        listI.add(list[j].getConstructor().newInstance());
                    else
                    {
                        listI.add(itm = list[j].getConstructor().newInstance());
                        itm.setMaps(maps[j]);
                    }
                }
            }
            return listI;
        }
    }


    /**
     * This method allows to give the item to the mobile
     * @param min  is the minimum of the item for the mobile
     * @param max  is the maximum of the item for the mobile
     * @param mb is a list of the mobile to give the item
     */
    protected void addItemToMobile(int min, int max,Mobile mb)
    {
        if (listItems == null)
            getListItems();
        for (int i = min; i <= max;i++)
            mb.addObject(listItems.get(new Random().nextInt(listItems.size())));

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
    public ArrayList<Lesson> getLesson()
    {
        return Supervisor.getSupervisor().getLesson(bloc);
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
            if (percent+many < 0)
                percent = 0;
            else
                percent += many;
            testFinish();
        }
    }


    /**
     * This method allows to test if the Quest is finish
     */
    protected void testFinish()
    {
        if(percent >= maxPercent)
        {
            finished = true;
            nextQuest();
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
     *This method return the under quest of this, this underQuest is the goal.
     *@return a list of Quest
     */
    public Quest[] getSubQuests()
    {
        return underQuest;
    }


    /**
     * This method allows to receive the notification and update this quest
     * @param notify is the notification with event
     */
    @Override
    public void update(Notification notify)
    {
        eventMaps(notify);
        if(notify.getEvents().equals(Events.UpLevel) && memory != null && memory.bloc.getMinPeople()-difficulty.getUpLevel() <= people.getLevel())
        {
            this.after = memory;
            people.newQuest(memory);
            memory = null;
        }
    }


    /**
     *This method allow to say what is this quest
     *@return a goal (=question) of this quest
     */
    public abstract String question();


    /**
     * This method allows to create the list of the items
     */
    protected abstract void createListItems() throws Exception;


    /**
     * This method allows to create the list of the mobiles
     */
    protected abstract void createListMobiles() throws Exception;


    /**
     * This method return the all items for this quest
     * @return list of items
     */
    public ArrayList<Items> getListItems()
    {
        if (listItems == null)
        {
            try
            {
                createListItems();
            }
            catch (Exception e)
            {
                Gdx.app.error("Error in MasterQuest to init the list of Item", String.valueOf(e));
            }
        }
        return listItems;
    }


    /**
     * This method return the all mobile and PNJ for this quest
     * @return list of mobile
     */
    public ArrayList<Mobile> getListPnj()
    {
        if (listMobs == null)
        {
            try
            {
                createListMobiles();
            }
            catch (Exception e)
            {
                return new ArrayList<>();
               // Gdx.app.error("Error in the MasterQuest to init the list of Mobile",String.valueOf(e));

            }
        }
        return listMobs;
    }


    /**
     * This method return the maps for this Quest
     * @return list of the maps
     */
    public abstract Maps[] getListMaps();


    /**
     * This method return the year of school of this people in the university
     * @return bloc of people
     */
    public Bloc getBloc()
    {
        return bloc;
    }


}
