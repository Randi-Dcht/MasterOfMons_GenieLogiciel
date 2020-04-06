package be.ac.umons.mom.g02.Objects.Items;


import be.ac.umons.mom.g02.Objects.Characters.HelpPnj;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;

/**
 * This class define the paper of the paper with the exam question
 */
public class PaperHelp extends Items
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
     * This methods allows to use the item
     * @param pp is the player who uses the item
     */
    @Override
    public void used(People pp)
    {
        Mobile mob = pp.meet();
        if (mob != null && mob.getClass().equals(HelpPnj.class))
            ((HelpPnj)mob).giveHelp(this);
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
