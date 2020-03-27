package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;


/**
 * This class define the moving PNJ with displacement in the maps
 * @author Umons_Group_2_ComputerScience
 */
public class MovingPNJ extends Mobile
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
        Supervisor.getSupervisor().addRefresh(this);
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


    /**
     * This method allows to give the size of the tiles
     * @param size is the size of the tile
     */
    public void setSize(int size)
    {
        tileSize = size;
    }


    /**
     * This method calculus the distance between this and victim player
     */
    private void calculusDistance()
    {
        tileXbetween = myGraphic.getPosX() - victim.getPosX();
        tileYbetween = myGraphic.getPosY() - victim.getPosY();
    }


    /**
     * This method give the time between two frames
     * @param dt is the time between two frames
     */
    @Override
    public void update(double dt)
    {
        if (!meet)
        {
            calculusDistance();
            if (tileXbetween <= 1000 && tileYbetween <= 1000)
                moving(dt,false,false);
        }
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving(double dt, boolean onX, boolean onY)//TODO optimise this
    {
        int x=0,y=0;
        int toMove = 0;
        if (Supervisor.getSupervisor().getClass().equals(SuperviserNormally.class))
            toMove = (int)Math.round(Supervisor.getPeople().getSpeed() * dt * tileSize);


        if(tileXbetween > toMove || tileXbetween < -toMove || tileYbetween > toMove || tileYbetween < -toMove)
        {
            if (tileXbetween < 0 || onX)
            {
                x = toMove;
                myGraphic.setOrientation(Orientation.Right);
            }
            else
            {
                x = -toMove;
                myGraphic.setOrientation(Orientation.Left);
            }
            if (tileYbetween < 0 || onY)
            {
                y = toMove;
                myGraphic.setOrientation(Orientation.Top);
            }
            else
            {
                y = -toMove;
                myGraphic.setOrientation(Orientation.Bottom);
            }
        }
        else
        {
            SuperviserNormally.getSupervisor().meetCharacter(this,victim.getCharacteristics());
            meet=true;
        }
        checkMove(x,y,dt);
    }


    public void checkMove(int x, int y,double dtMemory)
    {
        myGraphic.move(x,y);
       if (!ps.checkForCollision(myGraphic))
        {
            myGraphic.move(-x,-y);//back to old position
            /*if (x == 0)TODO this (stack overflow !!)
                moving(dtMemory,true,false);
            else
                moving(dtMemory,false,true);*/
        }
    }



    /**
     * This method allows to give the instance of graphic Character
     * @return instance of character
     */
    public Character getCharacter()
    {
        return myGraphic;
    }


}
