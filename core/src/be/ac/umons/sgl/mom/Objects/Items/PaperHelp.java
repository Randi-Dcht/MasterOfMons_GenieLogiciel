package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Objects.Characters.People;

public class PaperHelp extends Items
{
    public PaperHelp(double x, double y)
    {
        super(x, y,"OldExam");
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
