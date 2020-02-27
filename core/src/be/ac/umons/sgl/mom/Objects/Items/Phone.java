package be.ac.umons.sgl.mom.Objects.Items;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Events.Notifications.UseItem;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;

/**
 * This class define the phone item who help the people in the Quest
 * @author Umons_Group_2_ComputerScience
 */
public class Phone extends Items
{

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
    pp.addFriend(new Mobile("FriendRandom",pp.getBloc(), MobileType.Lambda, Actions.Never));
    SuperviserNormally.getSupervisor().getEvent().notify(new UseItem(this));
  }


  /***/
  @Override
  public void update(double time)//TODO check if use
  {

  }


  /***/
  public double getObsolete()//TODO idem
  {
    return 0;
  }

  @Override
  public String question() {
    return null;
  }
}
