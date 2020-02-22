package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.MobileType;

public class SaoulMatePNJ extends Mobile //TODO update this class
{

    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     * @param type is the type of the mobile in the game
     */
    public SaoulMatePNJ(Bloc playerBloc, MobileType type)
    {
        super("SaoulMate", playerBloc, type,Actions.Dialog);
    }

}
