package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Objects.Characters.People;

public class OldExam extends Items
{

    public OldExam(Place place)
    {
        super(place, "Old Exam");
    }

    @Override
    public void used(People pp)
    {
    }

    @Override
    public void make(double time)
    {
    }

    @Override
    public double getObsolete()
    {
        return 0;
    }
}
