package be.ac.umons.sgl.mom.Enums;


/**
 *This is a list of the Tmx maps in this game (MasterOfMonsBAc2/core/Tmx)
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum Maps
{
    Nimy(      "Tmx/Umons_Nimy.tmx",      "InfoNimy"),
    Warocque(  "Tmx/Umons_Warocque.tmx",  "InfoWaroc"),
    GrandAmphi("Tmx/Umons_GrandAmphi.tmx","InfoGA"),
    DeVinci(   "Tmx/Umons_DeVinci.tmx",   "InfoDeVinci"),
    Poly(      "Tmx/Umons_Polytech.tmx",  "InfoPoly"),
    Kot(       "Tmx/kot.tmx",             "InfoKot"),
    Mons(      "Tmx/CityMons.tmx",        "InfoCity");


    /**The characteristic of the maps*/
    final String maps;
    final String about;


    /**
     * This constructor allows to create the maps in the game with the name of the maps
     * @param maps is the name of the maps .tmx
     */
    private Maps(String maps,String about)
    {
        this.maps  = maps;
        this.about = about;
    }


    /**
     * This method to give the name of the Tmx/maps of Mons
     * @return maps who is the name of the Mons maps.
     */
    public String getMaps()
    {
        return maps;
    }


    /**
     * This method return the id of the information
     * @return the id of information about the maps
     */
    public String getInformation()
    {
        return about;
    }
}
