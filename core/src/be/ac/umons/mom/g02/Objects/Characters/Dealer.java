package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;

/***/
public class Dealer extends Mobile
{

    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     */
    public Dealer(Bloc playerBloc)
    {
        super("Dealer Rocco", playerBloc,MobileType.Lambda, Actions.Dialog, NameDialog.Seller);
    }
}
