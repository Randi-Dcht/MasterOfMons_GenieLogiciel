package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.PassLevel;
import be.ac.umons.mom.g02.Objects.Items.SuperChargerGun;
import be.ac.umons.mom.g02.Objects.Items.Sword;
import be.ac.umons.mom.g02.Objects.Items.TNT;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;


/***/
public class Dealer extends Mobile
{

    private int myMoney;

    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     */
    public Dealer(Bloc playerBloc)
    {
        super("Dealer Game", playerBloc,MobileType.Lambda, Actions.Dialog, NameDialog.Seller);
        try {//TODO displace
            createList(getMyList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Class<Items>[] getMyList()
    {
        return new Class[]{TNT.class, TheKillBoot.class, Sword.class, SuperChargerGun.class,PassLevel.class};
    }


    public boolean buyItem(Items buy,int money)
    {
        if (buy.buy() <= money && myObject.contains(buy))
        {
            myObject.remove(buy);
            return true;
        }
        return false;
    }


    private void createList(Class<Items>[] list) throws Exception
    {
        for (Class<Items> clss : list)
            myObject.add(clss.getConstructor().newInstance());
    }
}
