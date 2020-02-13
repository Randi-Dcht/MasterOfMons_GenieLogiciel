package be.ac.umons.sgl.mom.Enums;


/**
 *This is a list of the Tmx place in this game (MasterOfMonsBAc2/core/Tmx)
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */
public enum Place
{
    Nimy("Umons_Nimy.tmx",State.normal),
    Warocque("Umons_Warocque.tmx",State.listen),
    GrandAmphi("Umons_GrandAmphi.tmx",State.listen),
    DeVinci("Umons_DeVinci.tmx",State.study),
    Poly("Umons_Polytech.tmx",State.listen),
    Kot("kot.tmx",State.nap);

    private String maps;
    private State state;


    /**
     * This constructor allows to create the place in the game with the name of the place
     * @param maps is the name of the maps .tmx
     */
    private Place(String maps,State state)
    {
        this.maps = maps;
        this.state = state;
    }


    /**
     * This method return the state of the people in function the place
     * @return state of the people
     */
    public State getState()
    {
        return state;
    }


    /**
     * This method to give the name of the Tmx/place of Mons
     * @return maps who is the name of the Mons place.
     */
    public String getMaps()
    {
        return maps;
    }
}
