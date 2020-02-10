package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Objects.Characters.People;

public class Battery extends Items
{
  public Battery(Place place)
  {
    super(place,"Battery");
  }

  public void used(People pp)
  {

  }

  public void make(double time)
  {

  }

  public double getObsolete()
  {
    return 0;
  }
}
