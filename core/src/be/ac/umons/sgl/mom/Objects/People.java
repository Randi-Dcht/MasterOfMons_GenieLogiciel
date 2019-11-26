package be.ac.umons.sgl.mom.Objects;
import java.util.*;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Quest;
import java.io.Serializable;

/**
*Cette classe permet de définir le perso du joeur humain
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public class People implements Serializable
{
/*savoir où est le personnage sur la carte*/
	private double x;
	private double y;
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
 * Permet d'instancier un personnage gérer par le joueur
 * @param name qui est le nom du joueur
 * @param type qui est la caractéristique du joueur
 * */
	public People(String name, Type type)
	{
		this.name     = name;
		this.strength = type.getStrength();
		this.defence  = type.getDefence();
		this.agility  = type.getAgility();
		this.life     = lifemax();
	}

/**
*Cette méthode permet de regénérer la vie du personnage quand il en a perdu
*/
	public void regeneration()
	{
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
*Permet de changer de quête pour monter dans tes quêtes supérieur
*@param quest qui est la nouvelle quête
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
*Permet de définir les caracteristique du personnage quand il gagne des points ou au début.
*@param strength qui est sa force
*@param defence qui est sa capacité à se defendre
*@param agility qui est sa capacité à éviter des coups de l'adversaire
*/
	public void updateType(int strength, int defence, int agility)
	{
		this.strength = strength;
		this.defence = defence;
		this.agility = agility;
	}

/**
*Permet de retirer un objet du sac à dos
*@return boolean pour savoir s'il a été bien retirer
*/
	public boolean removeObject(Items objet)
	{
		if(myObject.size()==0 || !myObject.contains(objet))
			return false;
		myObject.remove(objet);
		return true;
	}

/**
*Permet d'ajouter des objets dans son sac à dos
*@param object qui est l'objet qui prend
*@return true si l'objet est bien ajouté et false sinon
*/
	public boolean pushObject(Items object)
	{
		if(myObject.size() == maxObject)
			return false;
		myObject.add(object);
		return true;
	}

/**
*Cette méthode permet de calculer la vie mawimal du personnage
*@return vie maximun
*/
	public double lifemax()
	{
		return (strength*20);
	}

/**
*Cette méthode permet de calculer ...
*/
	public double displacement()
	{
		return (2 +(agility/20));
	}

/**
*Cette méthode retourne le nombre de cours qui a encore à passer
*@return size de la liste de cours
*/
	public int numberCourse()
	{
		return myCourse.size();
	}

/**
*Cette méthode permet de retourner la liste de cours suivi et dont les examens ne sont pas encore réussi
*@return myCourse qui est la liste de cours
*/
	public ArrayList<Lesson> myCourse()
	{
		return myCourse;
	}

/**
*Permet de modifier l'énergie du personnage
*@param many qui est l'énergie perdu ou gagné
*/
	public void energy(double many)
	{
		if(this.energy + many >= 0 || this.energy + many <= 100)
		  this.energy = this.energy + many;
	}

/**
*Permet de modifier l'énergie du personnage chaque seconde
*/
	public void energy()
	{
		this.energy = energy + this.state.getEnergy();
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
	}

/**
*Cette méthode doit être appeler lorque le personnage bouge sur la maps
*@param x qui est son déplacement sur l'axe x
*@param y qui est son déplacement sur l'axe y
*/
	public void move(Couple couple)
	{
		this.x = couple.x;
		this.y = couple.y;
	}
}
