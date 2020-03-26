package be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;

public class ZombiePNJ extends Mobile
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
     * This constructor define the moving PNJ in the maps
     * @param maps is the maps of this pnj
     * @param type  is the type of the Mobile
     */
    public ZombiePNJ(MobileType type, Maps maps)
    {
        super("MovingPNJ",Bloc.Extend,type,Actions.Attack, NameDialog.Move);
        setMaps(maps);
    }


    /**
     * This class allows to create the instance graphic of this PNJ
     * @param character is the graphic setting
     * @param victim is the instance of graphic Player
     */
    public void initialisation(Character character,Player victim)
    {
        myGraphic = character;
        setVictim(victim);
        Supervisor.getSupervisor().addRefresh(this);
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
        if (!meet && myGraphic != null && victim != null)
        {
            calculusDistance();
            moving(dt);
        }
    }


    /**
     * This method allows to move the people in the maps with refresh
     */
    private void moving(double dt)
    {
        int x=0,y=0;
        int toMove = (int)Math.round(0.23 * dt * tileSize);


        if(tileXbetween > toMove || tileXbetween < -toMove || tileYbetween > toMove || tileYbetween < -toMove)
        {
            if (tileXbetween < 0 )
            {
                x = toMove;
                myGraphic.setOrientation(Orientation.Right);
            }
            else
            {
                x = -toMove;
                myGraphic.setOrientation(Orientation.Left);
            }
            if (tileYbetween < 0)
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
            Supervisor.getSupervisor().meetCharacter(this,victim.getCharacteristics());
            meet=true;
        }
        myGraphic.move(x,y);
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
