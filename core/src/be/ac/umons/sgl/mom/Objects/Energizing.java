package be.ac.umons.sgl.mom.Objects;

public class Energizing extends Items
{
  private double obsolete = 31536000;

  public Energizing(double x,double y)
  {
    super(x,y,"Boisson energisante");
  }

  public void used(People pp)
  {
    pp.energy(0.23);
    visibly();
  }

  public void make(double time)
  {
    obsolete = obsolete - time;
    if(obsolete <= 0)
      visibly();
  }

  public double getObsolete()
  {
    return obsolete;
  }
}
