package be.ac.umons.sgl.mom;
/**
*Cette classe permet de définir les cours pour tout son cursus scolaire
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public enum Lesson
{
	MI1('O',"math pour informatique 1",Place.Nimy,1),
	MI2('O',"math pour informatique 2",Place.Nimy,1),
	algo1('O',"algorithme python",Place.Nimy,1),
	algo2('F',"algorithme java",Place.Nimy,1),
	ftOrdi('F',"fonctionnement des ordinateurs",Place.Nimy,1),
	nbComplexe('F',"nombre complexe",Place.Nimy,2),
	opti('O',"optimisation lineaire",Place.Nimy,2),
	anglais('O',"english for sciences",Place.Nimy,2),
	OS('O',"systeme exploitation",Place.Nimy,2),
	projet1('F',"projet bachelier 1",Place.Nimy,1),
	projet2('F',"projet bachelier 2",Place.Nimy,2),
	calculus2('O',"algèbre partie B",Place.Nimy,2),
	ecopol('F',"economie politique",Place.Nimy,2),
	realTime('F',"real time computer",Place.Nimy,3),
	reseau('O',"reseau",Place.Nimy,2),
	calculProba('O',"calcul de probabilite",Place.Nimy,3),
	intelligen('O',"intelligence artificielle",Place.Nimy,3),
	compilation('F',"compilation",Place.Nimy,3),
	grapheOpti('O',"graphe et optimisation",Place.Nimy,3),
	baseDonnes('F',"base de données",Place.Nimy,3),
	statistique('F',"statistique",Place.Nimy,3);

	final char type;
	final String name;
	final Place place;
	final int year;

/**
*Constrcuteur interne
*/
	private Lesson(char type,String name, Place place,int year)
	{
		this.type  = type;
		this.name   = name;
		this.place  = place;
		this.year = year;
	}

/**
*Cette méthode permey de savoir si le personnage doit aller au cours pour réussir
*@return true si c'est obliagtoire sinon false
*/
	public boolean obligatoryCourse()
	{
		if(type == 'O')
		  return true;
		return false;
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
