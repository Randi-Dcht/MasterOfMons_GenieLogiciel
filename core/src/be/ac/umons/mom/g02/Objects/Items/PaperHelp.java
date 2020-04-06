package be.ac.umons.mom.g02.Objects.Items;


/**
 * This class define the paper of the paper with the exam question
 */
public class PaperHelp extends Items//TODO delete
{

    /**
     * This constructor define the item of the exam question
     */
    public PaperHelp()
    {
        super("ExamQuestion");
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


    /**
     * This method returns the id to place the Item on the map
     * @return id of place
     */
    public String idOfPlace()
    {
        return "OTHER";
    }
}
