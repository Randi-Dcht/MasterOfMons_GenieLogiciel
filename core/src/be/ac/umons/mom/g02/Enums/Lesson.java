package be.ac.umons.mom.g02.Enums;
/**
 *This class allows to define the lesson on the bachelor and master in the Sciences Computer (Umons course).
 *@author Umons_Group_2_ComputerScience
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
     * MyFirstYear       ==  bloc_BA1
     * SuccessfulYear    ==  bloc_BA2
     * MeetAndLearn      ==  bloc_BA3
     * PreparedCompany   ==  bloc_Ma1
     * FinishUniversity  ==  bloc_MA2
     */

    /*Lesson of Bachelor 1*/
    MI1(    'O',"math for computer 1", Maps.GrandAmphi,Bloc.BA1,30),
    MI2(    'O',"math for computer 2", Maps.GrandAmphi,Bloc.BA1,15),
    algo1(  'O',"algorithm python"   , Maps.DeVinci   ,Bloc.BA1,20),
    algo2(  'F',"algorithm java"     , Maps.DeVinci   ,Bloc.BA1,15),
    ftOrdi( 'F',"computer operation" , Maps.GrandAmphi,Bloc.BA1,10),
    projet1('F',"bachelor project 1" , Maps.DeVinci   ,Bloc.BA1,10),

    /*Lesson of Bachelor 2*/
    nbComplexe('F',"complex number"       , Maps.GrandAmphi,Bloc.BA2,5),
    opti(      'O',"linear optimization"  , Maps.Poly      ,Bloc.BA2,5),
    anglais(   'O',"english for sciences" , Maps.Nimy      ,Bloc.BA2,20),
    OS(        'O',"operating system"     , Maps.Nimy      ,Bloc.BA2,30),
    projet2(   'F',"Bachelor project 2"   , Maps.Nimy      ,Bloc.BA2,20),
    calculus2( 'O',"calculus2"            , Maps.Poly      ,Bloc.BA2,15),
    ecopol(    'F',"political economics"  , Maps.Warocque  ,Bloc.BA2,5),
    reseau(    'O',"network"              , Maps.DeVinci   ,Bloc.BA2,30),

    /*Lesson of Bachelor 3*/
    calculProba('O',"probability calculation" , Maps.Poly    ,Bloc.BA3,10),
    intelligen( 'O',"artificial intelligence" , Maps.DeVinci ,Bloc.BA3,40),
    compilation('F',"compilation"             , Maps.Nimy    ,Bloc.BA3,30),
    grapheOpti( 'O',"graph and optimization"  , Maps.Poly    ,Bloc.BA3,20),
    baseDonnes( 'F',"data base"               , Maps.Nimy    ,Bloc.BA3,20),
    statistique('F',"Statistics"              , Maps.Nimy    ,Bloc.BA3,10),

    /*Lesson of Master 1 & 2*/

    res2('F',"reseaux 2", Maps.Nimy,Bloc.MA1,10),
    lecred('0',"lecture et redaction scientifiques", Maps.Nimy,Bloc.MA1,10),
    proglog('F',"programmation logique", Maps.Nimy,Bloc.MA1,10),
    bda('F',"Big data analytics", Maps.Nimy,Bloc.MA1,10),
    comp('F',"compilation", Maps.Nimy,Bloc.MA1,10),
    algbio('F',"algorithme et bioinformatique", Maps.Nimy,Bloc.MA1,10),
    sofev('F',"software evolution", Maps.Nimy,Bloc.MA1,10),
    angl('F',"English for scientific communication", Maps.Nimy,Bloc.MA1,10),

    mem('O',"Memoire", Maps.Nimy,Bloc.MA2,10),
    stage('O',"Stage en entreprise", Maps.Nimy,Bloc.MA2,10),
    ss('F',"Sciences et societe", Maps.Nimy,Bloc.MA2,10),
    ps('F',"Philosophie des sciences", Maps.Nimy,Bloc.MA2,10),
    mang('F',"Managment", Maps.Nimy,Bloc.MA2,10);


    final char type;
    final String name;
    final Maps maps;
    final Bloc year;
    final int number;


    /**
     *This constructor define the lesson with:
     * @param type who is obligatory (O) lesson or not (F).
     * @param name who is the full name of the lesson.
     * @param maps of the lesson (see Tmx map).
     * @param year who is the year of the Lesson
     * @param number who is the number of this course for a month.
     */
    private Lesson(char type, String name, Maps maps, Bloc year, int number)
    {
        this.type  = type;
        this.name   = name;
        this.maps = maps;
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


    /**
     * This method allows to give the number of the course during the
     * @return number of course
     */
    public int numberOfCourse()
    {
        return number;
    }


    /**
     *This method return the maps where the teacher give the course
     *@return maps (Tmx map)
     */
    public Maps location()
    {
        return maps;
    }


    /**
     *This method return the bloc of the lesson
     * @return bloc of the lesson
     */
    public Bloc getBloc()
    {
        return year;
    }
}
