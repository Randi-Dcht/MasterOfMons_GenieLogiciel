package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.MoreCasesMons;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class allows to add in the PlayingState the number of cases on the maps for the extension Dual (MoreCaseMons)
 */
public class PlayCases extends PlayingStateDual
{
    /***/
    protected HashMap<Player,Integer> cases = new HashMap<>();
    /***/
    protected ArrayList<Cases> drawCase = new ArrayList<>();
    /***/
    protected HashMap<Player, Point> old = new HashMap<>();
    /***/
    protected TextBox player1Number;
    /***/
    protected TextBox player2Number;


    /**
     * @param gs The game's graphical settings
     */
    public PlayCases(GraphicalSettings gs)
    {
        super(gs);
    }

    /***/
    @Override
    public void init()
    {
        super.init();


        player1Number = new TextBox(gs); player1Number.setText("0");
        player2Number = new TextBox(gs); player2Number.setText("0");


        old.put(player,new Point(player.getPosX(),player.getPosY()));cases.put(player,0);
        old.put(playerTwo,new Point(playerTwo.getPosX(),playerTwo.getPosY()));cases.put(playerTwo,0);

    }


    /***/
    @Override
    public void update(float dt)
    {
        super.update(dt);

        checkCase(player);
        checkCase(playerTwo);

        player1Number.setText(cases.get(player).toString());
        player2Number.setText(cases.get(playerTwo).toString());
    }


    /***/
    @Override
    public void draw()
    {
        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        /*
        if (SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.OccupationFloor))
        {
            for (Cases cc : drawCase)
                cc.draw();
        }//TODO this
        */
        super.draw();

        Point textSize = new Point((int)( gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(topMargin + gs.getSmallFont().getLineHeight()));
        player1Number.draw(sb,new Point(objectSize.x, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin)),textSize);
        player2Number.draw(sb,new Point(MasterOfMonsGame.WIDTH - objectSize.x, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin)),textSize);
    }


    /***/
    protected void checkCase(Player player)
    {
        if (!old.get(player).equals(new Point(player.getPosX(),player.getPosY())))
        {
            old.replace(player,new Point(player.getPosX(),player.getPosY()));
            if (player.equals(this.player))
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), com.badlogic.gdx.graphics.Color.BLUE));//TODO
            else
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), Color.RED));//TODO
            cases.replace(player,cases.get(player)+1);
            old.replace(player,new Point(player.getPosX(),player.getPosY()));
            if(supervisorDual.getDual().equals(TypeDual.OccupationFloor))//TODO
                ((MoreCasesMons)((DualMasterQuest) SupervisorDual.getSupervisorDual().actualQuest()).getUnderQuest((People)player.getCharacteristics())).callMe(1);
        }
    }


    @Override
    public void dispose()
    {
        super.dispose();
        player1Number.dispose();
        player2Number.dispose();
    }
}
