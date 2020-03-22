package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

/***/
public class PaperHelp extends Items//TODO delete
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
    public boolean getObsolete()
    {
        return true;
    }
}
