package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class OldExam extends Items
{

    /***/
    public OldExam()
    {
        super("ExamAnswer");
    }


    /***/
    @Override
    public void used(People pp)
    {
    }


    /***/
    @Override
    public void update(double time)
    {
    }


    /***/
    @Override
    public double getObsolete()
    {
        return 0;
    }
}
