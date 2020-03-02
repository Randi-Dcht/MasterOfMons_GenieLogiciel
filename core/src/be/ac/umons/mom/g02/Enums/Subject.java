package be.ac.umons.mom.g02.Enums;

/**
 * This class allows to choice the subject of the memory.
 * @author Maxime Renversez
 */
public enum Subject {
    crepro("Création d'un projet individuel",10, Maps.Nimy),
    crelang("Création d'un language de programmation",10, Maps.Nimy),
    infauto("Informatique appliqué à l'automobile",10, Maps.Nimy);

    final String name;
    final Maps maps;
    final int number;

    /**
     * This constructor define the subject with :
     * @param name who is the name of the subject.
     * @param maps who is the maps where the memory is.
     */
    private Subject(String name, int number, Maps maps)
    {
        this.name=name;
        this.number=number;
        this.maps = maps;
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
     *This method return the maps where the teacher give the course
     *@return maps (Tmx map)
     */
    public Maps location()
    {
        return maps;
    }
}
