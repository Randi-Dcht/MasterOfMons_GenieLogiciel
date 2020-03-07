package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;


/**
 * This class define the moving PNJ with displacement in the maps
 * @author Umons_Group_2_ComputerScience
 */
public final class MovingPNJ extends Mobile
{

    /**
     * Distance between people and this PNJ on X
     */
    private int tileXbetween;
    /**
     * Distance between people and this PNJ on Y
     */
    private int tileYbetween;
    /**
     * The human player
     */
    private Character victim;
    /**
     * The instance of this PNJ (graphic)
     */
    private Character myGraphic;
    /**
     * Size of the tile
     */
    private int tileSize = 64;
    /**
     * If the moving PNJ already meet the player on the maps
     */
    private boolean meet = false;
    /**
     * The variable of the PlayingState
     */
    private PlayingState ps;


    /**
     * This constructor define the moving PNJ in the maps
     * @param bloc  is the level of the player
     * @param maps is the maps of this pnj
     * @param type  is the type of the Mobile
     */
    public MovingPNJ(Bloc bloc, MobileType type, Maps maps,Actions action)
    {
        super("MovingPNJ",bloc,type,action, NameDialog.Move);
        setMaps(maps);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param gs is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public Character initialisation(GraphicalSettings gs,PlayingState ps, Player victim)
    {
        myGraphic = new Character(gs,this);
        setVictim(victim);
        this.ps = ps;
        return myGraphic;
    }


    /**
     * This method allows to add a victim player to the mobile
     * @param victim  is the graphic of the player
     */
    public void setVictim(Player victim)
    {
        this.victim = victim;
    }

}
