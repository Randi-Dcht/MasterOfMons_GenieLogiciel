package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.ButtonMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TextMenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Objects.Characters.Dealer;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.util.ArrayList;


/**
 * This class define the menu to buy the items with the PNJ seller
 */
public class SellerMenuState extends MenuState
{
    /**
     * The player who speak with seller
     */
    protected People player;
    /**
     * The seller of the gun
     */
    protected Dealer seller;


    /**
     * This constructor define the menu of the seller when people buys items
     * @param gs is the graphicSetting
     */
    public SellerMenuState(GraphicalSettings gs)
    {
        super(gs);
    }


    /**
     * Initialization of the shop
     */
    @Override
    public void init()
    {
        super.init();
        player = Supervisor.getPeople();
        seller = Supervisor.getSupervisor().getDealerOnMap();
        transparentBackground = true;
        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(new TitleMenuItem(gs,GraphicalSettings.getStringFromId("WlcShop")));
        list.add(new TextMenuItem(gs,String.format(GraphicalSettings.getStringFromId("MoneyWllt"),player.getMyMoney())));

        for (Items itm : seller.getInventory())
        {
            list.add(new TextMenuItem(gs,GraphicalSettings.getStringFromId(itm.getIdItems())));
            list.add(new ButtonMenuItem(gim,gs,String.format(GraphicalSettings.getStringFromId("buttonPay"),itm.buy()),() -> buy(itm)));
        }

        list.add(new ButtonMenuItem(gim,gs,GraphicalSettings.getStringFromId("QShop"),() -> gsm.removeFirstState()));

        setMenuItems(list.toArray(new MenuItem[0]),false);
    }


    /**
     * Check the buy of the people and quit the shop
     * @param itm to buy
     */
    private void buy(Items itm)
    {
        if (player.pullMoney(itm.buy()) && seller.buyItem(itm))
        {
            player.pushObject(itm);
            player.addMoney(-itm.buy());
            gsm.removeFirstState();
        }
    }
}
