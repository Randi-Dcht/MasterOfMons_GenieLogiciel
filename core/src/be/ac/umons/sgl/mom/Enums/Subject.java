package be.ac.umons.sgl.mom.Enums;

/**
 * This class allows to choice the subject of the memory.
 * @author Maxime Renversez
 */
public enum Subject {
    crepro("Création d'un projet individuel",10,Place.Nimy),
    crelang("Création d'un language de programmation",10,Place.Nimy),
    infauto("Informatique appliqué à l'automobile",10,Place.Nimy);

    final String name;
    final Place place;
    final int number;

    /**
     * This constructor define the subject with :
     * @param name who is the name of the subject.
     * @param place who is the place where the memory is.
     */
    private Subject(String name,int number,Place place)
    {
        this.name=name;
        this.number=number;
        this.place=place;
    }

    public String getSubjectName()
    {
        return name;
    }

    public int numberOfSubject()
    {
        return 3;
    }

    /**
     *This method return the place where the teacher give the course
     *@return place (Tmx map)
     */
    public Place location()
    {
        return place;
    }
}
