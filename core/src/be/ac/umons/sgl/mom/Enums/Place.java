package be.ac.umons.sgl.mom.Enums;
/**
*This is a list of the Tmx place in this game (MasterOfMonsBAc2/core/Tmx)
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public enum Place
{
  Nimy("Umons_Nimy.tmx"),
  Warocque("Umons_Warocque.tmx"),
  GrandAmphi("Umons_GrandAmphi.tmx"),
  DeVinci("Umons_DeVinci.tmx"),
  Poly("Umons_Polytech.tmx"),
  Kot("kot.tmx");

  private  String maps;

  private Place(String maps)
  {
    this.maps = maps;
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
