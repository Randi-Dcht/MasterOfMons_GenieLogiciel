package be.ac.umons.sgl.mom;
/**
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public enum Lesson
{
	mathInfo1('O',"math pour informatique 1",Lieu.Nimy,1),
	mathInfo2('O',"math pour informatique 2",Lieu.Nimy,1),
	algo1Python('O',"algorithme python",Lieu.Nimy,1),
	algo2Java('F',"algorithme java",Lieu.Nimy,1),
	fonctionOrdinateur('F',"fonctionnement des ordinateurs",Lieu.Nimy,1),
	nombreComplexe('F',"nombre complexe",Lieu.Nimy,2),
	optimisation('O',"optimisation lineaire",Lieu.Nimy,2),
	anglais('O',"english for sciences",Lieu.Nimy,2),
	systemeExploitation('O',"systeme exploitation",Lieu.Nimy,2),
	projetBa1('F',"projet bachelier 1",Lieu.Nimy,1),
	projetBa2('F',"projet bachelier 2",Lieu.Nimy,2),
	calculus2('O',"algèbre partie B",Lieu.Nimy,2),
	economiePolitique('F',"economie politique",Lieu.Nimy,2),
	realTime('F',"real time computer",Lieu.Nimy,3),
	reseau('O',"reseau",Lieu.Nimy,2),
	calculProbabilite('O',"calcul de probabilite",Lieu.Nimy,3),
	intelligenceArtificielle('O',"intelligence artificielle",Lieu.Nimy,3),
	compilation('F',"compilation",Lieu.Nimy,3),
	grapheOptimisation('O',"graphe et optimisation",Lieu.Nimy,3),
	baseDonnes('F',"base de données",Lieu.Nimy,3),
	statistique('F',"statistique",Lieu.Nimy,3);

	final char type;
	final String name;
	final Lieu place;
	final int annee;

	private Lesson(char type,String name, Place place,int year)
	{
		this.type  = type;
		this.name   = name;
		this.place  = place;
		this.year = year;
	}

	public boolean obligatoryCourse()
	{
		if(type == 'O')
		  return true;
		return false;
	}

	public Place location()
	{
		return place;
	}

  public boolean take(int year)
	{
		if(this.year <= year)
		  return true;
		return false;
	}
}
