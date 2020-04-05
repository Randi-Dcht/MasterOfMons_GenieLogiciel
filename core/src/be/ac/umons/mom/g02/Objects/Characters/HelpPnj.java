package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Notifications.PlayerHelpMe;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Regulator.Supervisor;

public class HelpPnj extends Mobile
{
    /***/
    private boolean help = true;


    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     */
    public HelpPnj(Bloc playerBloc)
    {
        super(playerBloc,MobileType.Loser, Actions.Dialog, NameDialog.Lambda);
    }


    /***/
    public void giveHelp(Items itm)
    {
        if (itm.getClass().equals(PaperHelp.class) && help)
        {
            help = false;
            Supervisor.getEvent().notify(new PlayerHelpMe(this));
        }
    }


    /***/
    @Override
    public String getName()
    {
        return "Help:"+super.getName();
    }
}
