package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;

public class TeacherPNJ extends Mobile
{
    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     * @param type
     */
    public TeacherPNJ(Bloc playerBloc, MobileType type)
    {
        super("TeacherOfCourse", playerBloc, type);
    }

    public Actions meet(People people)
    {
        return null;
    }

    @Override
    public Actions getAction()
    {
        return Actions.Attack;
    }
}
