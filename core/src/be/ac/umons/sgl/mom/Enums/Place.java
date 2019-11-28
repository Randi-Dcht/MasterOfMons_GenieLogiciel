package be.ac.umons.sgl.mom.Enums;
/**
*Ensemble des lieux stratégique sur la maps
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public enum Place
{
  Nimy("nimy.tmx"),
  Waroque("waroque.tmx"),
  GrandAmphi("garmphi.tmx"),
  DeVinci("devinci.tmx"),
  PolyHoudaing("polytech.tmx"),
  Bibliotheque("biblio.tmx"),
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
