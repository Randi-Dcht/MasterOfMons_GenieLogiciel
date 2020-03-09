package be.ac.umons.mom.g02.Extensions.IA;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

public class ia extends Mobile implements Observer {
    /**
     * Distance between the player and the pnj on x
     */
    private int distanceX;
    /**
     * Distance between the player and the pnj on y
     */
    private int distanceY;
    /**
     * The main player of the game
     */
    private Character player;
    /**
     * This is the instance of the pnj (graphic)
     */
    private Character pnjGraphic;
    /**
     * This is the variable of the playingState
     */
    private PlayingState ps;
    /**
     * Size of the tile
     */
    private int tileSize = 64;
    /**
     * If the moving PNJ already meet the player on the maps
     */
    private boolean meet = false;

    /**
     * This constructor define the moving PNJ in the maps
     * @param bloc  is the level of the player
     * @param maps is the maps of this pnj
     * @param type  is the type of the Mobile
     */
    public ia(Bloc bloc, MobileType type, Maps maps,Actions action)
    {
        super("MovingPNJ",bloc,type,action, NameDialog.Move);
        setMaps(maps);
        SuperviserNormally.getSupervisor().getEvent().add(Events.PlaceInMons,this);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param gs is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public Character initialisation(PlayingState ps, GraphicalSettings gs, Player victim)
    {
        pnjGraphic = new Character(gs,this);
        setVictim(victim);
        this.ps = ps;
        return pnjGraphic;
    }


    /**
     * This method allows to give the size of the tiles
     * @param size is the size of the tile
     */
    public void setSize(int size)
    {
        tileSize = size;
    }


    /**
     * This method allows to add a victim player to the mobile
     * @param victim  is the graphic of the player
     */
    public void setVictim(Player victim)
    {
        this.player = victim;
    }


    /**
     * This method calculus the distance between this and victim player
     */
    private void calculusDistance()
    {
        distanceX= pnjGraphic.getPosX() - player.getPosX();
        distanceY = pnjGraphic.getPosY() - player.getPosY();
    }


    /**
     * This method give the time between two frames
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        if (!meet)
            moving(dt);
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving(double dt)//TODO optimiser cela
    {
        int x=0,y=0;
//        int toMove = (int)Math.round(ps.velocity * dt * tileSize);
        int toMove=0;
        calculusDistance();

        if(distanceX > toMove || distanceX < -toMove || distanceY > toMove || distanceY < -toMove)
        {
            if (distanceX < 0)
            {
                x = toMove;
                pnjGraphic.setOrientation(Orientation.Right);
            }
            else
            {
                x = -toMove;
                pnjGraphic.setOrientation(Orientation.Left);
            }
            if (distanceY < 0)
            {
                y = toMove;
                pnjGraphic.setOrientation(Orientation.Top);
            }
            else
            {
                y = -toMove;
                pnjGraphic.setOrientation(Orientation.Bottom);
            }
        }
        else
        {
            SuperviserNormally.getSupervisor().meetCharacter(this,player.getCharacteristics());
            meet=true;
        }
        pnjGraphic.move(x,y);
    }



    /**
     * This method allows to give the instance of graphic Character
     * @return instance of character
     */
    public Character getCharacter()
    {
        return pnjGraphic;
    }


    /**
     * The method to receive the notification
     * @param notify is the notification of the game
     */
    @Override
    public void update(Notification notify)
    {
        //TODO delete if never use
    }
}

