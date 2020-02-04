package be.ac.umons.sgl.mom.Enums;
/**
*Ensemble des lieux stratégique sur la maps
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

  public String getMaps()
  {
    return maps;
  }
}
