package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Objects.Items.Flower;
import be.ac.umons.mom.g02.Objects.Items.Items;
import java.util.Random;


/**
 * This class define the soul mate for the player in this quest
 */
public class SaoulMatePNJ extends Mobile
{
    /**
     * This variable is the percent of the love the player
     */
    private int percentLove;


    /**
     * This constructor allows define the mobile/PNJ
     * @param playerBloc is the bloc of the player
     * @param type is the type of the mobile in the game
     */
    public SaoulMatePNJ(Bloc playerBloc, MobileType type)
    {
        super("SoulMate", playerBloc, type, Actions.Dialog, NameDialog.Lambda);
        percentLove = new Random().nextInt(80);
    }


    /**
     * This method allows to give the item
     * @param item is the item to give
     */
    public void giveItem(Items item)
    {
        if (item.getClass().equals(Flower.class))
            percentLove += 25;
    }


    /**
     * This method allows to give if the soul mate is loved the player
     * @return boolean if love
     */
    public boolean isLove()
    {
        return percentLove >=60;
    }

}
