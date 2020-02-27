package be.ac.umons.sgl.mom.Enums;


/**
 *This is a list of the Tmx place in this game (MasterOfMonsBAc2/core/Tmx)
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum Place
{
    Nimy(      "Tmx/Umons_Nimy.tmx",      State.normal, "InfoNimy"),
    Warocque(  "Tmx/Umons_Warocque.tmx",  State.listen, "InfoWaroc"),
    GrandAmphi("Tmx/Umons_GrandAmphi.tmx",State.listen, "InfoGA"),
    DeVinci(   "Tmx/Umons_DeVinci.tmx",   State.study,  "InfoDeVinci"),
    Poly(      "Tmx/Umons_Polytech.tmx",  State.listen, "InfoPoly"),
    Kot(       "Tmx/kot.tmx",             State.nap,    "InfoKot"),
    Mons(      "Tmx/CityMons.tmx",        State.normal, "InfoCity");
    /*MicrosoftCompany("Tmx/MicrosoftCompany.tmx",State.normal);*/ //TODO up to mx


    /**The characteristic of the maps*/
    final String maps;
    final State state;
    final String about;


    /**
     * This constructor allows to create the place in the game with the name of the place
     * @param maps is the name of the maps .tmx
     */
    private Place(String maps,State state,String about)
    {
        this.maps  = maps;
        this.state = state;
        this.about = about;
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


    /**
     * This method return the id of the information
     * @return the id of information about the place
     */
    public String getInformation()
    {
        return about;
    }
}
