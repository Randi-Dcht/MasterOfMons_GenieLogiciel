package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Objects.Characters.People;

import java.util.List;
import java.util.Random;

/**
 * This class define the old exam for the player
 */
public class OldExam extends Items
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


    @Override
    public void used(People pp)
    {
        List<Lesson> list = pp.getLesson();
        if (list.size() > 0)
            pp.helpByItem(25,list.get(new Random().nextInt(list.size())));
        super.used(pp);
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
