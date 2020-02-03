package be.ac.umons.sgl.mom.Enums;
/**
*Cette classe permet de définir les cours pour tout son cursus scolaire
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public enum Lesson
{
	MI1('O',"math pour informatique 1",Place.Nimy,1,10),
	MI2('O',"math pour informatique 2",Place.Nimy,1,10),
	algo1('O',"algorithme python",Place.Nimy,1,10),
	algo2('F',"algorithme java",Place.Nimy,1,10),
	ftOrdi('F',"fonctionnement des ordinateurs",Place.Nimy,1,10),
	nbComplexe('F',"nombre complexe",Place.Nimy,2,10),
	opti('O',"optimisation lineaire",Place.Nimy,2,10),
	anglais('O',"english for sciences",Place.Nimy,2,10),
	OS('O',"systeme exploitation",Place.Nimy,2,10),
	projet1('F',"projet bachelier 1",Place.Nimy,1,10),
	projet2('F',"projet bachelier 2",Place.Nimy,2,10),
	calculus2('O',"algèbre partie B",Place.Nimy,2,10),
	ecopol('F',"economie politique",Place.Nimy,2,10),
	realTime('F',"real time computer",Place.Nimy,3,10),
	reseau('O',"reseau",Place.Nimy,2,10),
	calculProba('O',"calcul de probabilite",Place.Nimy,3,10),
	intelligen('O',"intelligence artificielle",Place.Nimy,3,10),
	compilation('F',"compilation",Place.Nimy,3,10),
	grapheOpti('O',"graphe et optimisation",Place.Nimy,3,10),
	baseDonnes('F',"base de données",Place.Nimy,3,10),
	statistique('F',"statistique",Place.Nimy,3,10);

	final char type;
	final String name;
	final Place place;
	final int year;
	final int number;

/**
*Constrcuteur interne
*/
	private Lesson(char type,String name, Place place,int year,int number)
	{
		this.type  = type;
		this.name   = name;
		this.place  = place;
		this.year = year;
		this.number = number;
	}

/**
*Cette méthode permey de savoir si le personnage doit aller au cours pour réussir
*@return true si c'est obliagtoire sinon false
*/
	public boolean obligatoryCourse()
	{
		return type == 'O';
	}

	public int numberOfCourse()
	{
		return number;
	}

/**
*Cette méthode permet de dire la localisation du cours sur le campus
*@return place où se déroule le cours
*/
	public Place location()
	{
		return place;
	}

/**
*Cette méthode permet de savoir si on peut prendre le cours
*@param year qui est l'année du personnage
*@return true s'il peut prendre le cours et false sinon
*/
	public boolean take(int year)
	{
		if(this.year <= year)
		  return true;
		return false;
	}
}
