package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Gender;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.ChangeQuest;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Notifications.PlaceInMons;
import be.ac.umons.sgl.mom.Events.Notifications.UpLevel;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Course;
import be.ac.umons.sgl.mom.Other.HyperPlanning;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *This class allows to define a people with all characteristic.
 *This is a logic party of people.
 *@author Umons_Group_2_ComputerScience
 */
public class People extends Character implements Serializable, Observer
{
    /*characteristic of people*/
    private double energy = 100;
    private State state = State.normal;
    private double threshold; /*seuil experience niveau à devoir atteindre*/
    private double experience = 0;
    private MasterQuest myQuest;
    private Bloc year;
    private boolean invincible = false;
    private Difficulty difficulty;
    private Gender gender;
    /*other thing*/
    private HashMap<Integer,ArrayList<Course>> myPlanning;
    private ArrayList<Lesson> myCourse = new ArrayList<Lesson>();
    private int friend = 0;
    private Mobile saoulMate; //TODO check the type


    /**
     * This constructor allows to create a new people who pilot by a player
     * @param name who is the name of player
     * @param type who is the characteristic of this people (Enums)
     * @param difficulty is the difficulty of the game
     * @param gender is the gender of the people
     */
    public People(String name, Type type, Gender gender, Difficulty difficulty)
    {
        super(name);
        SuperviserNormally.getSupervisor().getEvent().add(Events.PlaceInMons,this);
        SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeMonth,this);
        updateType(type.getStrength(),type.getDefence(),type.getAgility());
        this.threshold = minExperience(level+1);
        this.difficulty = difficulty;
        this.gender = gender;
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
     * This method return the gender of the people
     * @return gender of people
     */
    public Gender getGender()
    {
        return gender;
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
     * This method allows to give the arrayList of the Id dialog of this people
     * @param answer is the answer of the other character
     */
    public ArrayList<String> getDialog(String answer)
    {
        return conversation.getDialogPeople().get(answer);
    }


    /**
     *This method allows the change the actually MasterQuest
     *@param quest who is the new masterQuest
     */
    public void newQuest(MasterQuest quest)
    {
        myQuest = quest;
        myCourse.addAll(quest.getLesson());
        SuperviserNormally.getSupervisor().getEvent().notify(new ChangeQuest(quest));
        year = quest.getBloc() ;
        createPlanning();
    }


    /**
     * This method allows to create a new planning of the people
     */
    private void createPlanning()
    {
        myPlanning = HyperPlanning.createSchedule(myCourse,SuperviserNormally.getSupervisor().getTime().getDate()); //TODO voir pour éviter les trois get
    }


    /**
     * This method allows to add the friend Mobile at this people
     * @param mobile is the friend
     */
    public void addFriend(Mobile mobile)
    {
        if (saoulMate == null && mobile.getClass().equals(SaoulMatePNJ.class))
            saoulMate = mobile;

        if (mobile.addFriend)
            friend++;
    }


    /***/
    public void loseFriend()
    {
        friend--;
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
     *This method allows the remove a object in the bag of people.
     *@return true of the object is remove and false otherwise
     */
    public boolean removeObject(Items objet)
    {
        if(myObject.size()==0 || !myObject.contains(objet))
            return false;
        myObject.remove(objet);
        return true;
    }


    /**
     *This method allows to push a object in the bag of people.
     *@param object who is the object taken.
     *@return true if the object is in the bag and false otherwise.
     */
    public boolean pushObject(Items object)
    {
        if(myObject.size() == difficulty.getManyItem())
            return false;
        myObject.add(object);
        return true;
    }

    /**
     * @return The inventory of the player
     */
    public List<Items> getInventory() {
        return myObject;
    }


    public void useObject(Items object)
    {
        if (myObject.contains(object))
            object.used(this);
    }


    /**
     *This method allows to know the number of course to pass.
     *@return size of the list course
     */
    public int numberCourse()
    {
        return myCourse.size();
    }
//TODO remove quand celui reu

    /**
     *This method allows to return the list of course where exams don't pass.
     *@return myCourse who is a list of course.
     */
    public ArrayList<Lesson> myCourse()
    {
        return myCourse;
    }


    /**
     *This method allows to redefine the energy of this people.
     *@param many who is the energy loss or win
     */
    private void addEnergy(double many)
    {
        if(this.energy + many >= 0 || this.energy + many <= 100)
            this.energy = this.energy + many;
    }


    /**
     *This method allows to do exist energy of people.
     * @param time is the time between two frame.
     */
    public void energy(double time)
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
     * @return the minium of experience for the level in param
     */
    private double minExperience(int nb)
    {
        if(nb <= 1) //TODO tester
            return 0;
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
        experience = experience + win;
        if(experience >= threshold)
        {
            upLevel();
            threshold = minExperience(level+1);
        }
    }


    /**
     * This method calculates the experience to win when the Mobile is dead
     * @param vtm is the other Attack who is dead
     * @return experience (double) who is win
     */
    private double calculateWin(Attack vtm)
    {
        return (minExperience(level+1)-minExperience(level))/(3*Math.pow((level/vtm.getLevel()),2));
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
        level++;
        SuperviserNormally.getSupervisor().getEvent().notify(new UpLevel());
    }


    /**
     * This method allows to change the place of this people
     * @param place is the new place
     */
    @Override
    public void setPlace(Place place)
    {
        state = place.getState();
        super.setPlace(place);
    }


    /**
     * This method reduce the energizing once.
     * @param state is the state of the people²
     */
    public void reduceEnergizing(State state)
    {
        energy = energy - state.getEnergy();
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
     * This method return the type of the first action when he meet other character
     * @return action
     */
    @Override
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
        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferEmpty())
            setPlace(((PlaceInMons)notify).getBuffer());
        if (notify.getEvents().equals(Events.ChangeMonth))
            createPlanning();
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
