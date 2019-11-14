package be.ac.umons.sgl.mom;
/**
*Cette classe permet d'implémente les objet que le personnage pourra prendre lors de parcours
*@author Randy Dauchot (étudiant en Sciences informatique)
*/
public abstract class Objet
{
  protected double positionX;
  protected double positionY;
  protected boolean visible = true;
  final String name;

  public Objet(double x,double y,String name)
  {
    positionX = x;
    positionY = y;
    this.name = name;
  }

  public void visibly()
  {
    visible = false;
  }

  public abstract void used(People pp);
  public abstract void make();
  public abstract double getObsolete();

}
