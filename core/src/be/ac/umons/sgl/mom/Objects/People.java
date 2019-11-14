package be.ac.umons.sgl.mom;
import java.util.*;

/**
*Cette classe permet de définir le perso du joeur humain
*@param name : le nom du personnage
*@param agility : son agilité
*@param defence : sa capacité à se défendre
*@param strength : la strength pour battre un autre
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public class People
{
/*savoir où est le personnage sur la carte*/
	private double x;
	private double y;
/*caracteristique physique du personnage*/
	private int strength;
	private int defence;
	private int agility;
	private int energy = 100;
/*caracteristique autre du personnage*/
	private double life;
	private double experience = 0;
	private int level = 1;
	//private Niveau annee;
	private quest maquest;
	final String name;
	final int maxObject = 5; //nombre d'objet que peut avoir le personnage
	private ArrayList<Objet> myObject = new ArrayList<Objet>();
	private ArrayList<Lesson> myCourse = new ArrayList<Lesson>();


	public People(String nom, int strength, int defence, int agility)
	{
		this.nom     = nom;
		this.strength   = strength;
		this.defence = defence;
		this.agility = agility;
		this.life     = lifemax();
	}

	public void regeneration()
	{
	}

	public void newQuest(Quest quest)
	{
		myquest = quest;
		quest.retake(myquest);
		for(int i = 0 ; i < quest.lesson().length ; i++)
		{
			myCourse.add(quest.lesson()[i]);
		}
	}

	public void caracteristique(int strength, int defence, int agility)
	{
		this.strength = strength;
		this.defence = defence;
		this.agility = agility;
	}

	public boolean removeObject()
	{
		return false;
	}

	public boolean pushObject(Objet object)
	{
		if(myobject.size() == maxObject)
			return false;
		myobject.add(object);
		return true;
	}

	public double lifemax()
	{
		return (strength*20);
	}

	public double displacement()
	{
		return (2 +(agility/20));
	}

	public int numberCourse()
	{
		return mycourse.size();
	}

	public ArrayList<Cours> myCourse()
	{
		return mycourse;
	}

	public void energy(int many)
	{
		this.energy = this.energy + many;
	}

	public void move(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
