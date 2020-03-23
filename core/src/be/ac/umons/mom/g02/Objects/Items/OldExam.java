package be.ac.umons.mom.g02.Objects.Items;

/**
 * This class define the old exam for the player
 */
public class OldExam extends Items//TODO delete
{

    /**
     * This constructor define the item of the old exam
     */
    public OldExam()
    {
        super("ExamAnswer");
    }


    /**
     * This method return if the object can be use in the game
     * @return a boolean if can to use
     */
    @Override
    public boolean getObsolete()
    {
        return true;
    }
}
