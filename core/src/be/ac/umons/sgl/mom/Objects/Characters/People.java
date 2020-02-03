package be.ac.umons.sgl.mom.Objects.Characters;

import java.util.*;
import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.io.Serializable;

 /**
 *This class allows to define a people with all characteristic.
 *This is a logic party of people.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public class People implements Serializable, Attack, Observer, Social
{
/*caracteristique physique du personnage*/
	private int strength;
	private int defence;
	private int agility;
	private double energy = 100;
	private State state = State.normal;
/*caracteristique autre du personnage*/
	private double life;
	private double experience = 0;
	private int level = 1;
	//private Niveau annee;
	private MasterQuest myQuest;
	final String name;
	final int maxObject = 5; //nombre d'objet que peut avoir le personnage
	private ArrayList<Items> myObject = new ArrayList<Items>(); //objet dans son sac à dos
	private ArrayList<Lesson> myCourse = new ArrayList<Lesson>(); //Ces cours qui l'a encore

/**
 * This constructor allows to create a new people who pilot by a player
 * @param name who is the name of player
 * @param type who is the characteristic of this people (Enums)
 */
	public People(String name, Type type)
	{
		this.name     = name;
		this.strength = type.getStrength();
		this.defence  = type.getDefence();
		this.agility  = type.getAgility();
		this.life     = lifemax();
	}

/**
*This method allows to increase the life
*/
	public void regeneration()
	{
	}

	public Actions meet(PNJ other)
	{
		return null;
	}

	public double getLife()
	{
		return life;
	}

	public int getStrength()
	{
		return strength;
	}
	public int getDefence()
	{
		return defence;
	}
	public int getAgility()
	{
		return agility;
	}

	public double getExperience()
	{
		return experience;
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
*This method allows to calculated the maximum of life people.
*@return max life.
*/
	public double lifemax()
	{
		return (strength*20);
	}

/**
*This method allows to calculated ...
*/
	public double displacement()
	{
		return (2 +(agility/20));
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
	public void addEnergy(double many)
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
		this.energy = energy + (this.state.getEnergy()*time);
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
	*This method allows to say the time between two attack of this people
	 * @return the time between two attack
	 */
	@Override
	public double recovery()
	{
		return (0.5 + (agility/40));
	}

	@Override
	public void attack() {

	}

	/**
	 * This method allows to say the probability of dodge the other attack
	 * @return the probability of dodge
	 */
	@Override
	public double dodge() /*esquive*/
	{
		return Math.min((agility/100),0.75);
	}

	@Override
	public void update(Events event) {

	}

	@Override
	public Actions getAction() {
		return null; //TODO etre demander via l'interface graphique
	}
}
