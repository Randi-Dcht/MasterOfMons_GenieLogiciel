package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

/***/
public class PaperHelp extends Items
{

    /***/
    public PaperHelp()
    {
        super("ExamQuestion");
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
    public boolean getObsolete()
    {
        return true;
    }
}
