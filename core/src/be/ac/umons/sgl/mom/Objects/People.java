package be.ac.umons.sgl.mom.Objects;
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
	private double energy = 100;
	private int state = 0;
/*caracteristique autre du personnage*/
	private double life;
	private double experience = 0;
	private int level = 1;
	//private Niveau annee;
	private Quest myQuest;
	final String name;
	final int maxObject = 5; //nombre d'objet que peut avoir le personnage
	private ArrayList<Objet> myObject = new ArrayList<Objet>(); //objet dans son sac à dos
	private ArrayList<Lesson> myCourse = new ArrayList<Lesson>(); //Ces cours qui l'a encore


	public People(String name, int strength, int defence, int agility)
	{
		this.name     = name;
		this.strength = strength;
		this.defence  = defence;
		this.agility  = agility;
		this.life     = lifemax();
	}

/**
*Cette méthode permet de regénérer la vie du personnage quand il en a perdu
*/
	public void regeneration()
	{
	}

/**
*Permet de changer de quête pour monter dans tes quêtes supérieur
*@param quest qui est la nouvelle quête
*/
	public void newQuest(Quest quest)
	{
		myQuest = quest;
		quest.retake(myCourse);
		for(int i = 0 ; i < quest.getLesson().length ; i++)
		{
			myCourse.add(quest.getLesson()[i]);
		}
	}

/**
*Permet de définir les caracteristique du personnage quand il gagne des points ou au début.
*@param strength qui est sa force
*@param defence qui est sa capacité à se defendre
*@param agility qui est sa capacité à éviter des coups de l'adversaire
*/
	public void caracteristique(int strength, int defence, int agility)
	{
		this.strength = strength;
		this.defence = defence;
		this.agility = agility;
	}

/**
*Permet de retirer un objet du sac à dos
*@return boolean pour savoir s'il a été bien retirer
*/
	public boolean removeObject(Objet objet)
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
	public boolean pushObject(Objet object)
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
		if(state == 0) //rien mais éveiller
		  this.energy = this.energy - 0.000772;
		else if (state == 1) //dormir ou reposer
		  this.energy = this.energy + 0.0028;
		else if (state == 2) //étudier cours
		  this.energy = this.energy - 0.002313;
		else if (state == 3) //aller au cours
		  this.energy = this.energy - 0.001544;
		else if (state == 4) //micro sieste
		  this.energy = this.energy + 0.0014;
		else if (state == 5) //Sport - courir - xxx
		  this.energy = this.energy - 0.003088;
	}

	public double getEnergy()
	{
		return energy;
	}

/**
*Cette méthode permet de changer l'état du joueur pour son énergie
*@param state qui est l'état  entre 0 et 5
*/
	public void changedState(int state)
	{
		this.state = (state%6);
	}

/**
*Cette méthode doit être appeler lorque le personnage bouge sur la maps
*@param x qui est son déplacement sur l'axe x
*@param y qui est son déplacement sur l'axe y
*/
	public void move(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
