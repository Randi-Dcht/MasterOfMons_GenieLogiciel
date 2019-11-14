package be.ac.umons.sgl.mom;

public class Energizing extends Objet
{
  private int obsolete = 31536000;

  public Energizing(double x,double y)
  {
    super(x,y,"Boisson energisante");
  }

  public void used(People pp)
  {
    pp.energy(0.23);
    visibly();
  }

  public void make()
  {
    obsolete--;
    if(obsolete == 0)
      visibly();
  }

  public double getObsolete()
  {
    return obsolete;
  }
}
