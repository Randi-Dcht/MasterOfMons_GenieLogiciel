package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Objects.Characters.People;

public class OldExam extends Items//TODO delete
{

    /***/
    public OldExam()
    {
        super("ExamAnswer");
    }


    /***/
    @Override
    public boolean getObsolete()
    {
        return true;
    }
}
