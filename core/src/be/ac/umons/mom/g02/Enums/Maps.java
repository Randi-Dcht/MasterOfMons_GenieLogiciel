package be.ac.umons.mom.g02.Enums;


/**
 *This is a list of the Tmx maps in this game (MasterOfMonsBAc2/core/Tmx)
 *@author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum Maps
{
    Nimy(      "Tmx/Umons_Nimy.tmx",      "InfoNimy",    Places.OnTheMap),
    Warocque(  "Tmx/Umons_Warocque.tmx",  "InfoWaroc",   Places.OnTheMap),
    GrandAmphi("Tmx/Umons_GrandAmphi.tmx","InfoGA",      Places.OnTheMap),
    DeVinci(   "Tmx/Umons_DeVinci.tmx",   "InfoDeVinci", Places.OnTheMap),
    Poly(      "Tmx/Umons_Polytech.tmx",  "InfoPoly",    Places.OnTheMap),
    Kot(       "Tmx/Kot.tmx",             "InfoKot",     Places.OnTheMap),
    Mons(      "Tmx/CityMons.tmx",        "InfoCity",    Places.OnTheMap),
    LAN_Puzzle("Tmx/LAN_Puzzle.tmx",      "InfoPuzzle",  Places.OnTheMap),
    LAN_Boss(  "Tmx/LAN_Boss.tmx",        "InfoLANBoss", Places.OnTheMap),
    DualPark(  "Tmx/Dual_parckNimy.tmx",  "InfoDualPark",Places.OnTheMap ),
    DualKiosk( "Tmx/Dual_LittleParkl.tmx","InfoDualPark",Places.OnTheMap);


    /**The characteristic of the maps*/
    final String maps;
    final String about;
    final Places start;


    /**
     * This constructor allows to create the maps in the game with the name of the maps
     * @param maps is the name of the maps .tmx
     * @param about is the information about the maps
     * @param start is the first place to start in this maps
     */
    private Maps(String maps,String about,Places start)
    {
        this.maps  = maps;
        this.about = about;
        this.start = start;
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


    /**
     * This method returns the first places of this map
     * @return the place of the map
     */
    public Places getStart()
    {
        return start;
    }
}
