package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.Supervisor;

/**
 * This class define the phone item who help the people in the Quest
 * @author Umons_Group_2_ComputerScience
 */
public class Phone extends Items
{

    private double batteryEnergizing = 100;

    /**
     * This constructor define the phone item
     */
    public Phone()
    {
        super("Phone");
    }


    /**
     * This method allows to define the help of this items to the people
     * @param pp is the people
     */
    public void used(People pp)
    {
        for (int i=0; i < 10;i++)
            pp.addFriend(new Mobile("FriendRandom",pp.getBloc(), MobileType.Lambda, Actions.Never, NameDialog.Lambda));
        batteryEnergizing -= 25;
        super.used(pp);
    }


    @Override
    public boolean removeInBag()
    {
        return false;
    }

    /***/
    public boolean getObsolete()
    {
        return batteryEnergizing <= 0;
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
