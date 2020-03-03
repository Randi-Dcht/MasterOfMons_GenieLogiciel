package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.NameDialog;

public class SaoulMatePNJ extends Mobile //TODO update this class
{

    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     * @param type is the type of the mobile in the game
     */
    public SaoulMatePNJ(Bloc playerBloc, MobileType type)
    {
        super("SaoulMate", playerBloc, type, Actions.Dialog, NameDialog.Lambda);
    }

}
