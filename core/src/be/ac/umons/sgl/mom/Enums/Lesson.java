package be.ac.umons.sgl.mom.Enums;
/**
*Cette classe permet de définir les cours pour tout son cursus scolaire
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public enum Lesson
{
	/*
	* Note of type:
	* -----
	* O --> course is obligatory to pass a test
	* F --> course isn't obligatory to pass a test
	* ____________________________________________
	* Note of number:
	* ---------------
	* This is the number of course for a period 1 year of University.
	* This allows to calculated the number of hour for this course.
    */

	                          /*Lesson of Bachelor 1*/
	MI1('O',"math pour informatique 1",Place.Nimy,1,10),
	MI2('O',"math pour informatique 2",Place.Nimy,1,10),
	algo1('O',"algorithme python",Place.Nimy,1,10),
	algo2('F',"algorithme java",Place.DeVinci,1,10),
	ftOrdi('F',"fonctionnement des ordinateurs",Place.Nimy,1,10),

	                         /*Lesson of Bachelor 2*/
	projet1('F',"projet bachelier 1",Place.DeVinci,1,10),
	nbComplexe('F',"nombre complexe",Place.Nimy,2,10),
	opti('O',"optimisation lineaire",Place.Poly,2,10),
	anglais('O',"english for sciences",Place.Nimy,2,10),
	OS('O',"systeme exploitation",Place.Nimy,2,10),
	projet2('F',"projet bachelier 2",Place.Nimy,2,10),
	calculus2('O',"algèbre partie B",Place.Poly,2,10),
	ecopol('F',"economie politique",Place.Warocque,2,10),
	reseau('O',"reseau",Place.DeVinci,2,10),

                              /*Lesson of Bachelor 3*/
	calculProba('O',"calcul de probabilite",Place.Poly,3,10),
	intelligen('O',"intelligence artificielle",Place.DeVinci,3,10),
	compilation('F',"compilation",Place.Nimy,3,10),
	grapheOpti('O',"graphe et optimisation",Place.Poly,3,10),
	baseDonnes('F',"base de données",Place.Nimy,3,10),
	statistique('F',"statistique",Place.Nimy,3,10);

	                         /*Lesson of Master 1 & 2*/
	/*code ici(TODO: #Maxime)*/

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
		return this.year <= year;
	}
}
