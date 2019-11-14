package be.ac.umons.sgl.mom;
public class Energizing extends Objet
{
  private int obsolete = 31536000;

  public Energizing(double x,double y)
  {
    super(x,y,"Boisson energisante");
@@ -10,10 +12,13 @@ public class Energizing extends Objet
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
}
