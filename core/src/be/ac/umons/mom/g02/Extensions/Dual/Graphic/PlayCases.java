package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.MoreCasesMons;
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
    protected HashMap<Player,ArrayList<Point>> cases = new HashMap<>();
    /***/
    protected ArrayList<Cases> drawCase = new ArrayList<>();
    /***/
    protected HashMap<Player, Point> old = new HashMap<>();
    /***/
    protected TextBox player1Number;
    /***/
    protected TextBox player2Number;
    /***/
    protected TextBox timerShow;
    /***/
    protected double time = 90;


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


        player1Number = new TextBox(gs);
        player1Number.setText("0");
        player2Number = new TextBox(gs);
        player2Number.setText("0");

        timerShow = new TextBox(gs);
        timerShow.setText(String.valueOf(time));

        old.put(player,new Point(player.getPosX()/64,player.getPosY()/32));
        cases.put(player,new ArrayList<>());
        old.put(playerTwo,new Point(playerTwo.getPosX()/64,playerTwo.getPosY()/32));
        cases.put(playerTwo,new ArrayList<>());

    }


    /***/
    @Override
    public void update(float dt)
    {
        super.update(dt);

        checkCase(player);
        checkCase(playerTwo);

        player1Number.setText(String.valueOf(cases.get(player).size()));
        player2Number.setText(String.valueOf(cases.get(playerTwo).size()));

        time -= dt;
        if (time <= 0)
            gsm.removeAllStateAndAdd(DualChooseMenu.class);//TODO

        timerShow.setText(String.format("%2.0f",time));
    }


    /***/
    @Override
    public void draw()
    {
        int topBarHeight = 10;

        super.draw();

        Point textSize = new Point(0,0);
        timerShow.draw(sb,new Point(MasterOfMonsGame.WIDTH/2 - timerShow.getWidth(),timerShow.getHeight() + 10),textSize);
        player1Number.draw(sb,new Point(MasterOfMonsGame.WIDTH/4-100, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin-40)),textSize);
        player2Number.draw(sb,new Point(MasterOfMonsGame.WIDTH/2 + MasterOfMonsGame.WIDTH/4, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin-40)),textSize);
    }


    /***/
    @Override
    protected void drawAfterMaps()
    {
        for (Cases cc : drawCase)
            cc.draw((int)cam.position.x,(int)cam.position.y);
    }

    /***/
    protected void checkCase(Player player)
    {
        if (!old.get(player).equals(new Point(player.getPosX()/64,player.getPosY()/32)))
        {
            old.replace(player,new Point(player.getPosX()/64,player.getPosY()/32));

            if (player.equals(this.player))
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), com.badlogic.gdx.graphics.Color.BLUE));//TODO
            else
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), Color.RED));//TODO

            cases.get(adv.get(player)).remove(new Point(player.getPosX()/64,player.getPosY()/32));
            cases.get(player).add(new Point(player.getPosX()/64,player.getPosY()/32));

            if(supervisorDual.getDual().equals(TypeDual.OccupationFloor))//TODO
                ((MoreCasesMons)((DualMasterQuest) supervisorDual.actualQuest()).getUnderQuest((People)player.getCharacteristics())).callMe(1);
        }
    }


    @Override
    public void dispose()
    {
        super.dispose();
        player1Number.dispose();
        player2Number.dispose();
        timerShow.dispose();
    }
}
