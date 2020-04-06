package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Gun;
import be.ac.umons.mom.g02.Objects.Items.Guns;
import be.ac.umons.mom.g02.Other.HyperPlanning;
import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Enums.State;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *This class allows to define a people with all characteristic.
 *This is a logic party of people.
 *@author Umons_Group_2_ComputerScience
 */
public class People extends Character implements Serializable, Observer, FrameTime
{
    /**
     * The energy of the player
     */
    private double energy = 100;
    /**
     * The actual state of the player on the maps
     */
    private State state = State.normal;
    /**
     * The threshold to have to pass level
     */
    private double threshold;
    /**
     * The experience of the player to have in the quest and attack
     */
    private double experience = 0;
    /**
     * The actual MasterQuest of player plays
     */
    private MasterQuest myQuest;
    /**
     * The actual bloc of player (Ba1 to Ma2)
     */
    private Bloc year;
    /**
     * If the player can not be attacker
     */
    private boolean invincible = false;
    /**
     * The difficulty of the game
     */
    private Difficulty difficulty;
    /**
     * The gender of the player
     */
    private Gender gender;
    /**
     * The list of the course with the day course
     */
    private HashMap<Integer,ArrayList<Course>> myPlanning;
    /**
     * The succeed for every lesson
     */
    private HashMap<Lesson,Integer> percentSucceedCourse;
    /**
     * The actual list of the course
     */
    private ArrayList<Lesson> key = new ArrayList<>();
    /**
     * The number of friend on the all maps
     */
    private int friend = 0;
    /**
     * The soul mate of the player
     */
    private SaoulMatePNJ soulMate;
    /**
     * The mobile to meet now
     */
    private Mobile bufferMobile;
    /**
     * The total of the point of characteristic
     */
    private int actual = 0;
    /**
     * The actual place of the player
     */
    private Places place;
    /**
     * The actual item to use
     */
    private Items use;
    /***
     * The size of list item
     */
    private int maxItem;


    /**
     * This constructor allows to create a new people who pilot by a player
     * @param name who is the name of player
     * @param type who is the characteristic of this people (Enums)
     * @param difficulty is the difficulty of the game
     * @param gender is the gender of the people
     */
    public People(String name, Type type, Gender gender, Difficulty difficulty)
    {
        super(name,type);
        Supervisor.getEvent().add(this, Events.MeetOther,Events.PlaceInMons,Events.ChangeMonth,Events.EntryPlace,Events.GoLesson);
        updateType(type.getStrength(),type.getDefence(),type.getAgility());
        this.threshold = minExperience(level+1);
        this.difficulty = difficulty;
        this.gender = gender;
        myMoney = 5;
        maxItem = difficulty.getManyItem();
    }


    /**
     * This method return the experience of this people
     * @return experience of this people
     */
    public double getExperience()
    {
        return experience;
    }


    /**
     * This method allows to upgrade the characteristic of the player
     * @param agility is the strength  to add
     * @param defence is the defence to add
     * @param strength is the strength to add
     */
    public void updateUpLevel(int strength, int defence, int agility)
    {
        super.updateType(strength, defence, agility);
        actual += strength+defence+agility;
    }


    /**
     * This method allows to add the money to the player
     * @param add is the money more
     */
    public void addMoney(int add)
    {
        myMoney += add;
        Supervisor.getEvent().notify(new MoneyChanged(this, myMoney));
    }


    @Override
    public boolean pullMoney(int many)
    {
        Supervisor.getEvent().notify(new MoneyChanged(this, myMoney));
        return super.pullMoney(many);
    }


    /**
     * @param money The money of the player
     */
    public void setMoney(int money)
    {
        myMoney = money;
    }


    /**
     * This method allows to give the point with the actual level
     * @return total of point for characteristic
     */
    public int getPointLevel()
    {
        int mem;
        if((mem = getPointType(level)-actual) > 0)
            return mem;
        return 0;
    }


    /**
     * This method return the gender of the people
     * @return gender of people
     */
    public Gender getGender()
    {
        return gender;
    }


    /**
     * This method return the difficulty of this game for this player
     * @return the difficulty
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }


    /**
     * This method allows to decrease the life
     * @param lose is the number of life
     */
    @Override
    public void loseAttack(double lose)
    {
        if(!invincible)
            super.loseAttack(lose);
    }


    /**
     * This method allows to give the mobile to meet on the maps
     * @param mobile is the mobile to meet
     */
    public void setMeetMobile(Mobile mobile)
    {
        bufferMobile = mobile;
    }


    /**
     *This method allows the change the actually MasterQuest
     *@param quest who is the new masterQuest
     */
    public void newQuest(MasterQuest quest)
    {
        newQuest(quest, true);
    }


    /**
     *This method allows the change the actually MasterQuest
     *@param quest who is the new masterQuest
     */
    public void newQuest(MasterQuest quest, boolean showDialog)
    {
        myQuest = quest;
        initLesson(quest.getLesson());
        if (showDialog)
            Supervisor.getEvent().notify(new ChangeQuest(quest));
        year = quest.getBloc();
        createPlanning();
    }


    /**
     * This methods allows to init the list of the lesson
     * @param list is a list of the lesson for this bloc
     */
    private void initLesson(List<Lesson> list)
    {
        key.addAll(list);
        percentSucceedCourse = new HashMap<>();
        for (Lesson lss : list)
            percentSucceedCourse.put(lss,0);
    }


    /**
     * This method allows to create a new planning of the people
     */
    private void createPlanning()
    {
        myPlanning = HyperPlanning.createSchedule(key,Supervisor.getSupervisor().getTime().getDate());
        Supervisor.getEvent().notify(new PlanningChanged(myPlanning));
    }


    /**
     * This method return the actual item to use in the game
     * @return item to use
     */
    public Items getItems()
    {
        return use;
    }


    /**
     * This method allows to add the friend Mobile at this people
     * @param mobile is the friend
     */
    public void addFriend(Mobile mobile)
    {
        if (soulMate == null && mobile.getClass().equals(SaoulMatePNJ.class))
            soulMate = (SaoulMatePNJ) mobile;

        if (mobile.setFriend())
        {
            friend++;
            Supervisor.getEvent().notify(new DisplayMessage(String.format(GraphicalSettings.getStringFromId("friend"),friend),"NewFriend"));
            Supervisor.getEvent().notify(new AddFriend(mobile));
        }
    }


    /**
     * This method return the number of the friend
     * @return number of friend
     */
    public int getFriend()
    {
        return friend;
    }


    /**
     * This method allows to give the soul mate
     * @return soul mate of this player
     */
    public SaoulMatePNJ getSoulMate()
    {
        return soulMate;
    }


    /**
     * This method return the MasterQuest actual for this people
     * @return MasterQuest actual
     */
    public MasterQuest getQuest()
    {
        return myQuest;
    }


    /**
     * This method return a dictionary with date and Lesson for this people
     * @return planning is a dictionary
     */
    public HashMap<Integer,ArrayList<Course>> getPlanning()
    {
        return myPlanning;
    }


    /**
     * @param planning The planning for this character
     */
    public void setPlanning(HashMap<Integer, ArrayList<Course>> planning) {
        this.myPlanning = planning;
    }


    /**
     *This method allows to push a object in the bag of people.
     *@param object who is the object taken.
     *@return true if the object is in the bag and false otherwise.
     */
    public boolean pushObject(Items object)
    {
        if (object instanceof Guns && gun == null)
        {
            gun = (Guns)object;
            return true;
        }

        if(myObject.size() >= maxItem)
            return false;

        myObject.add(object);
        Supervisor.getEvent().notify(new InventoryChanged(this, object, InventoryChanged.Type.Added));
        return true;
    }


    /**
     * This method allows to use a gun
     * @param gun is a gun
     */
    public void useGun(Items gun)
    {
        if (this.gun != null)
            pushObject((Items)this.gun);
        myObject.remove(gun);
        this.gun=(Guns)gun;
    }


    /**
     * This method allows to set the number of the item in the bag
     * @param cmb is the number maximum of item
     */
    public void setMaxItem(int cmb)
    {
        maxItem = cmb;
    }


    /**
     * Notify when the people remove the object in the bag
     * @param object is the object to remove
     */
    @Override
    public boolean removeObject(Items object)
    {
        Supervisor.getEvent().notify(new InventoryChanged(this, object, InventoryChanged.Type.Removed));
        return super.removeObject(object);
    }


    /**
     * This method check if the people can attack the other
     * @return boolean of can attack
     */
    @Override
    public boolean canAttack()
    {
        return energy>=10;
    }


    /**
     * This method allows to use the item who is in the bag
     * @param object is the object to use
     */
    public void useObject(Items object)
    {
        if (myObject.contains(object))
        {
            object.used(this);
            use = object;
        }
    }


    /**
     * This method allows to add the success in the lesson to follow
     * @param cmb is the percent to add in success
     */
    private void addSuccess(int cmb,Lesson lesson)
    {
        if (percentSucceedCourse.containsKey(lesson))
            percentSucceedCourse.replace(lesson,percentSucceedCourse.get(lesson)+cmb);

        testingSucceed();
    }


    /**
     * This method allows to check the percent of every course
     */
    private void testingSucceed()
    {
        for (Lesson ls : key)
        {
            if (percentSucceedCourse.get(ls) >= 70)
            {
                percentSucceedCourse.remove(ls);key.remove(ls);
            }
        }
    }


    /***/
    public ArrayList<Lesson> getLesson()
    {
        return key;
    }


    /**
     * This methods check if the player goes to lesson course
     */
    private void checkCourseNow(Course course)
    {
        if (course != null)
        {
            addSuccess(10,course.getLesson());
            if (course.isLate())
                addSuccess((int)course.getLate(),course.getLesson());//penalize
        }
    }


    /**
     *This method allows to redefine the energy of this people.
     *@param many who is the energy loss or win
     */
    public void addEnergy(double many)
    {
        if(this.energy + many <= 100)
            this.energy += many;
        else
            this.energy = 100;

        if (energy <= 2)
            this.energy = 2;

        if (energy <= 10)
            Supervisor.getEvent().notify(new LowSomething(LowSomething.TypeLow.Energy));
        Supervisor.getEvent().notify(new EnergyChanged(this, energy));
    }


    /**
     * This method allows to changed the actual energy of player
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        energy(dt);
    }


    /**
     *This method allows to do exist energy of people.
     * @param time is the time between two frame.
     */
    private void energy(double time)
    {
        addEnergy(this.state.getEnergy()*time);
    }


    /**
     * This method return the actual energy of people
     * @return energy of people
     */
    public double getEnergy()
    {
        return energy;
    }


    /**
     * This method allows to give the minimum of experience for have a level
     * @param nb is the level
     * @return the minimum of experience for the level in param
     */
    private double minExperience(int nb)
    {
        if(nb <= 1)
            return 750;
        else
            return minExperience(nb-1) + 1000 * Math.pow(1.1,nb-1);
    }


    /**
     * This method return the minimum number of experience to go to the next level.
     * @return the minimum number of experience to go to the next level.
     */
    public double minExperience()
    {
        return threshold;
    }


    /**
     * After the win attack, the people win the experience.
     * This method calculate the experience with the level of victim
     * @param victim who is dead
     */
    public void winExperience(Attack victim)
    {
        winExperience(calculateWin(victim));
    }


    /**
     * This method allows to the experience at this people and check the threshold
     * @param win who is the experience win
     */
    public void winExperience(double win)
    {
        experience += win;
        calculusWinXp();
        Supervisor.getEvent().notify(new ExperienceChanged(this, experience));
    }


    /**
     * This method check the experience with the threshold of the level
     */
    private void calculusWinXp()
    {
        if(experience >= threshold)
        {
            upLevel();
            threshold = minExperience(level+1);
            calculusWinXp();
        }
    }


    /**
     * This method allows to reinitialisation the characteristic of player
     */
    public void reinitialization()
    {
        strength = 0;
        agility  = 0;
        defence  = 0;
    }


    /**
     * This method calculates the experience to win when the Mobile is dead
     * @param vtm is the other Attack who is dead
     * @return experience (double) who is win
     */
    private double calculateWin(Attack vtm)
    {
        return (minExperience(level+1)-minExperience(level))/(3*(Math.pow(((float)level/vtm.getLevel()),2)));
    }


    /**
     * A people is a Human player so the type is HumanPlayer
     * @return playerType of this instance
     */
    @Override
    public TypePlayer getType()
    {
        return TypePlayer.Human;
    }


    /**
     * This method return the year of study of the people to balance the game
     * @return year is the bloc of study
     */
    public Bloc getBloc()
    {
        return year;
    }


    /**
     * This method allows to up level of this people (#debug#)
     */
    public void upLevel()
    {
        if (level < 40)
        {
            level++;
            Supervisor.getEvent().notify(new UpLevel(this));
        }
    }


    /**
     * This method allows to change the maps of this people
     * @param maps is the new maps
     */
    @Override
    public void setMaps(Maps maps)
    {
        setPlaceMaps(maps.getStart());
        super.setMaps(maps);
    }


    /**
     * This method allows to give the actual place of the people
     * @param place is the place on the maps
     */
    public void setPlaceMaps(Places place)
    {
        this.place = place;
        state = place.getState();
    }


    /**
     * This method returns the place of the people
     * @return the place
     */
    public Places getPlace()
    {
        return place;
    }


    /**
     * This method reduce the energizing once.
     * @param state is the state of the people
     */
    public void reduceEnergizing(State state)
    {
        addEnergy(state.getEnergy());
    }


    /**
     * This method allows to invincible people,the life doesn't decrease (#debug#)
     * @param var is true == invincible | false == doesn't invincible.
     */
    public void invincible(boolean var)
    {
        invincible = var;
    }


    /**
     * This method allows to say if the people is invincible
     * @return If the character is invincible.
     */
    public boolean isInvincible()
    {
        return invincible;
    }


    /**
     * @param energy The energy of this player
     */
    public void setEnergy(double energy)
    {
        this.energy = energy;
        Supervisor.getEvent().notify(new EnergyChanged(this, energy));
    }


    /**
     * @param experience The experience of this player
     */
    public void setExperience(double experience)
    {
        this.experience = experience;
        Supervisor.getEvent().notify(new ExperienceChanged(this, experience));
    }


    /**
     * This method return the type of the first action when he meet other character
     * @return action
     */
    public Actions getAction()
    {
        return Actions.Dialog;
    }


    /**
     * This method allows to see the notification in the game
     * @param notify is the notification
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty())
            setMaps(((PlaceInMons)notify).getBuffer());
        if (notify.getEvents().equals(Events.EntryPlace) && notify.bufferNotEmpty())
            setPlaceMaps(((EntryPlaces)notify).getBuffer());
        if (notify.getEvents().equals(Events.ChangeMonth))
            createPlanning();
        if (notify.getEvents().equals(Events.MeetOther) && notify.bufferNotEmpty() && notify.getBuffer() instanceof Mobile)
            setMeetMobile((Mobile) notify.getBuffer());
        if (notify.getEvents().equals(Events.GoLesson) && notify.bufferNotEmpty())
            checkCourseNow(((GoToLesson)notify).getBuffer());
    }


    /**
     * Method is called when the player leave the mobile
     */
    public void leaveMobile()
    {
        bufferMobile =null;
    }


    /***/
    public Mobile meet()
    {
        return bufferMobile;
    }


    /***/
    public void meetMobile(Mobile mobile)
    {
        bufferMobile = mobile;
    }


    /**
     * This method allows to say the picture of the people
     * @return the start name of the picture
     */
    @Override
    public String toString()
    {
        if(howGun())
            return "bh_"+gender.getName()+"_aa_";
        return "bh_"+gender.getName()+"_sa_";
    }
}
