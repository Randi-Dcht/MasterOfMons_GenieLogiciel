package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notification;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *This class allows to define a people with all characteristic.
 *This is a logic party of people.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class People extends Character implements Serializable
{
/*characteristic physique of people*/
	private double energy = 100;
	private State state = State.normal;
	private double threshold; /*seuil experience niveau à devoir atteindre*/
/*characteristic other of people*/
	private double experience = 0;
	private MasterQuest myQuest;
	final String name;
	final int maxObject = 5;
	private ArrayList<Items> myObject = new ArrayList<Items>(); //objet dans son sac à dos
	private ArrayList<Lesson> myCourse = new ArrayList<Lesson>(); //Ces cours qui l'a encore

/**
 * This constructor allows to create a new people who pilot by a player
 * @param name who is the name of player
 * @param type who is the characteristic of this people (Enums)
 */
	public People(String name, Type type)
	{
		super(type);
		this.name = name;
		this.threshold = minExperience(level+1);
	}

/**
*This method allows to increase the life
*/
	public void regeneration()
	{
	}

	public double getExperience()
	{
		return experience;
	}

	public Actions meet(PNJ other)
	{
		return null;
	}

/**
*This method allows the change the actually MasterQuest
*@param quest who is the new masterQuest
*/
	public void newQuest(MasterQuest quest)
	{
		myQuest = quest;
		quest.retake(myCourse);
		myCourse.addAll(Arrays.asList(quest.getLesson()));
	}

	public MasterQuest getQuest()
	{
		return myQuest;
	}

/**
*This method allows define the characteristic of a people.
*@param strength who is strength of people.
*@param defence who is defence of people.
*@param agility who is the capacity of avoid the attack.
*/
	public void updateType(int strength, int defence, int agility)
	{
		this.strength = strength;
		this.defence = defence;
		this.agility = agility;
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
		if(myObject.size() == maxObject)
			return false;
		myObject.add(object);
		return true;
	}

/**
*This method allows to know the number of course to pass.
*@return size of the list course
*/
	public int numberCourse()
	{
		return myCourse.size();
	}

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
	public void addEnergy(double many)//TODO mort
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
		this.energy = energy + (this.state.getEnergy()*time); //TODO addenergie
	}

	public double getEnergy()
	{
		return energy;
	}

/**
*Cette méthode permet de changer l'état du joueur pour son énergie
*@param state qui est l'état  entre 0 et 5
*/
	public void changedState(State state)
	{
		this.state = state;
	} //TODO supprimer à verifier

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
	}/*note à moi même : pour avoir le niveau maximun prendre le nb+1 du niveau actuelle*/

	/**
	 * This method return the minimum number of experience to go to the next level.
	 * @return the minimum number of experience to go to the next level.
	 */
	public double minExperience() {
		return threshold;
	}


	@Override
	public void update(Notification notify) {
	}

	public void winExperience(Attack victim)//TODO add level
	{
		experience = experience + calculateWin(victim);
	}

	/**
	 * This method calculates the experience to win when the PNJ is dead
	 * @param vtm is the other Attack who is dead
	 * @return experience (double) who is win
	 */
	private double calculateWin(Attack vtm)
	{
		return (minExperience(level+1)-minExperience(level))/(3*Math.pow((level/vtm.getLevel()),2));
	}

	@Override
	public Actions getAction() {
		return null; //TODO etre demander via l'interface graphique
	}
}
