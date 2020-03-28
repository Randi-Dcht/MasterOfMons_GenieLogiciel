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
    /***/
    protected People player;
    /***/
    protected Dealer seller;


    /**
     * This constructor define the menu of the seller when people buys items
     * @param gs is the graphicSetting
     */
    public SellerMenuState(GraphicalSettings gs)
    {
        super(gs);
    }

    @Override
    public void init()//TODO String format and bundle
    {
        super.init();
        player = Supervisor.getPeople();
        seller = new Dealer(Bloc.BA1);
        transparentBackground = true;
        ArrayList<MenuItem> list = new ArrayList<>();
        list.add(new TitleMenuItem(gs,"Welcome on shop"));
        list.add(new TextMenuItem(gs,String.format("You have %3d € in your wallet",player.getMyMoney())));//gs.getStringFromId("MoneyPlayerShop")

        System.out.println(seller.getInventory() + " -> " + seller.getInventory().size());
        for (Items itm : seller.getInventory())
        {
            list.add(new TextMenuItem(gs,itm.getIdItems()));//gs.getStringFromId(itm.getIdItems())
            list.add(new ButtonMenuItem(gim,gs,"Buy for " + itm.buy() + " €",() -> seller.buyItem(itm,itm.buy())));//gs.getStringFromId("BuyItm")
        }

        list.add(new ButtonMenuItem(gim,gs,"Quit shop",() -> gsm.removeFirstState()));//gs.getStringFromId("QuitShop")

        setMenuItems(list.toArray(new MenuItem[0]),false);
    }
}
