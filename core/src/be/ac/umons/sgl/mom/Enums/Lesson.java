package be.ac.umons.sgl.mom.Enums;
/**
 *This class allows to define the lesson on the bachelor and master in the Sciences Computer (Umons course).
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
     * ____________________________________________
     * Note of year:
     * -------------
     * MyFirstYear == 1
     * SuccessfulYear == 2
     * MeetAndLearn == 3
     * PreparedCompany   == 4
     * FinishUniversity   == 5
     */

    /*Lesson of Bachelor 1*/
    MI1('O',"math pour informatique 1",Place.Nimy,Bloc.BA1,10),
    MI2('O',"math pour informatique 2",Place.Nimy,Bloc.BA1,10),
    algo1('O',"algorithme python",Place.Nimy,Bloc.BA1,10),
    algo2('F',"algorithme java",Place.DeVinci,Bloc.BA1,10),
    ftOrdi('F',"fonctionnement des ordinateurs",Place.Nimy,Bloc.BA1,10),
    projet1('F',"projet bachelier 1",Place.DeVinci,Bloc.BA1,10),

    /*Lesson of Bachelor 2*/
    nbComplexe('F',"nombre complexe",Place.Nimy,Bloc.BA2,10),
    opti('O',"optimisation lineaire",Place.Poly,Bloc.BA2,10),
    anglais('O',"english for sciences",Place.Nimy,Bloc.BA2,10),
    OS('O',"systeme exploitation",Place.Nimy,Bloc.BA2,10),
    projet2('F',"projet bachelier 2",Place.Nimy,Bloc.BA2,10),
    calculus2('O',"algèbre partie B",Place.Poly,Bloc.BA2,10),
    ecopol('F',"economie politique",Place.Warocque,Bloc.BA2,10),
    reseau('O',"reseau",Place.DeVinci,Bloc.BA2,10),

    /*Lesson of Bachelor 3*/
    calculProba('O',"calcul de probabilite",Place.Poly,Bloc.BA3,10),
    intelligen('O',"intelligence artificielle",Place.DeVinci,Bloc.BA3,10),
    compilation('F',"compilation",Place.Nimy,Bloc.BA3,10),
    grapheOpti('O',"graphe et optimisation",Place.Poly,Bloc.BA3,10),
    baseDonnes('F',"base de données",Place.Nimy,Bloc.BA3,10),
    statistique('F',"statistique",Place.Nimy,Bloc.BA3,10),

    /*Lesson of Master 1 & 2*/

    res2('F',"reseaux 2",Place.Nimy,Bloc.MA1,10),
    lecred('0',"lecture et redaction scientifiques",Place.Nimy,Bloc.MA1,10),
    proglog('F',"programmation logique",Place.Nimy,Bloc.MA1,10),
    bda('F',"Big data analytics",Place.Nimy,Bloc.MA1,10),
    comp('F',"compilation",Place.Nimy,Bloc.MA1,10),
    algbio('F',"algorithme et bioinformatique",Place.Nimy,Bloc.MA1,10),
    sofev('F',"software evolution",Place.Nimy,Bloc.MA1,10),
    angl('F',"English for scientific communication",Place.Nimy,Bloc.MA1,10),

    mem('O',"Memoire",Place.Nimy,Bloc.MA2,10),
    stage('O',"Stage en entreprise",Place.Nimy,Bloc.MA2,10),
    ss('F',"Sciences et societe",Place.Nimy,Bloc.MA2,10),
    ps('F',"Philosophie des sciences",Place.Nimy,Bloc.MA2,10),
    mang('F',"Managment",Place.Nimy,Bloc.MA2,10);


    final char type;
    final String name;
    final Place place;
    final Bloc year;
    final int number;


    /**
     *This constructor define the lesson with:
     * @param type who is obligatory (O) lesson or not (F).
     * @param name who is the full name of the lesson.
     * @param place of the lesson (see Tmx map).
     * @param year who is the year of the Lesson
     * @param number who is the number of this course for a year.
     */
    private Lesson(char type,String name, Place place,Bloc year,int number)
    {
        this.type  = type;
        this.name   = name;
        this.place  = place;
        this.year = year;
        this.number = number;
    }


    /**
     *This method allows to say if the course is obligatory to succeed
     *@return true if the course is obligatory else false
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
     *This method return the place where the teacher give the course
     *@return place (Tmx map)
     */
    public Place location()
    {
        return place;
    }


    /**
     *This method allows to say if the people can take the lesson.
     *@param year is the year of the people
     *@return true if the people can take this course else false
     */
    public boolean take(int year)
    {
        return false;//this.year <= year;
    }
}
