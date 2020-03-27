package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.MoreCasesMons;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.GraphicalObjects.LifeBar;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;


/***/
public class PlayDual extends PlayingState

{
    /***/
    protected LifeBar lifeBarTwo;
    /***/
    protected Button endDual;
    /***/
    protected HashMap<Player,Player> adv = new HashMap<>();
    /***/
    protected HashMap<Player,Integer> cases = new HashMap<>();
    /***/
    protected ArrayList<Cases> drawCase = new ArrayList<>();
    /***/
    protected HashMap<Player,Point> old = new HashMap<>();
    /***/
    protected TextBox player1Number;
    /***/
    protected TextBox player2Number;
    /***/
    protected SupervisorDual supervisorDual;


    /**
     * @param gs The game's graphical settings
     */
    public PlayDual(GraphicalSettings gs)
    {
        super(gs);
        supervisor = Supervisor.getSupervisor();
        supervisorDual = SupervisorDual.getSupervisorDual();
    }


    /***/
    @Override
    public void init()
    {
        super.init();
        setSecondPlayerCharacteristics(SupervisorDual.getPeopleTwo());
        //TODO setter the inventory for the second player
        lifeBarTwo = new LifeBar(gs);
        lifeBarTwo.setForegroundColor(gcm.getColorFor("lifeBar"));
        initMap(supervisorDual.getDual().getStartMaps().getMaps(),supervisorDual.getDual().getPointPlayerOne().x,supervisorDual.getDual().getPointPlayerOne().y);
        playerTwo.setMapPos(new Point(player.getPosX(),player.getPosY()));
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        cam.update();
        initSizeOfMaps();
        SupervisorDual.setGraphic(gs);
        adv.put(player,playerTwo);adv.put(playerTwo,player);
        if (supervisorDual.getDual().equals(TypeDual.Survivor))
        {
            for (Character ch : pnjs)
            {
                if (ch.getCharacteristics().getClass().equals(ZombiePNJ.class))
                    ((ZombiePNJ)ch.getCharacteristics()).initialisation(ch,player);
            }

        }

        initPNJsPositions(getPNJsOnMap(supervisorDual.getDual().getStartMaps().getMaps()));//TODO
        questShower.setQuest(Supervisor.getSupervisor().actualQuest());

        if (supervisorDual.getDual().equals(TypeDual.OccupationFloor))
        {
            player1Number = new TextBox(gs); player1Number.setText("0");
            player2Number = new TextBox(gs); player2Number.setText("0");
        }

        old.put(player,new Point(player.getPosX(),player.getPosY()));cases.put(player,0);
        old.put(playerTwo,new Point(playerTwo.getPosX(),playerTwo.getPosY()));cases.put(playerTwo,0);


        endDual = new Button(gs);
        endDual.setText("X");
        endDual.setOnClick(() -> gsm.removeAllStateAndAdd(DualChooseMenu.class));
        endDual.setFont(gs.getSmallFont());

        if (supervisorDual.getDual().equals(TypeDual.CatchFlag) || SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.OccupationFloor))
            changedCam();

    }


    /***/
    @Override
    public void update(float dt)
    {
        super.update(dt);
        lifeBarTwo.setValue((int)playerTwo.getCharacteristics().getActualLife());
        lifeBarTwo.setMaxValue((int)playerTwo.getCharacteristics().lifeMax());

        if (supervisorDual.getDual().equals(TypeDual.OccupationFloor))
        {
            checkCase(player);
            checkCase(playerTwo);
        }

        if (supervisorDual.getDual().equals(TypeDual.OccupationFloor))//TODO upgrade
        {
            player1Number.setText(cases.get(player).toString());
            player2Number.setText(cases.get(playerTwo).toString());
        }
    }


    @Override
    public void draw()
    {

        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        gmm.render();
        player.draw(sb, player.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, player.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);
        playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, playerTwo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

        for (Character pnj : pnjs)
            pnj.draw(sb, pnj.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, pnj.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);
/*
        if (SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.OccupationFloor))
        {
            for (Cases cc : drawCase)
                cc.draw();
        }//TODO this
 */
        sb.begin();
        if (gs.mustShowMapCoordinates())
        {
            gs.getSmallFont().draw(sb, String.format("(%f, %f)", player.getMapRectangle().x, player.getMapRectangle().y), (int)leftMargin, (int)(10 * topMargin - topBarHeight));
            gs.getSmallFont().draw(sb, String.format("(%d, %d)", player.getPosX(), player.getPosY()), (int)leftMargin, (int)(10 * topMargin - topBarHeight - gs.getSmallFont().getLineHeight()));

            gs.getSmallFont().draw(sb, String.format("(%f, %f)", playerTwo.getMapRectangle().x, playerTwo.getMapRectangle().y), (int)leftMargin, (int)(10 * topMargin - topBarHeight));
            gs.getSmallFont().draw(sb, String.format("(%d, %d)", playerTwo.getPosX(), playerTwo.getPosY()), (int)leftMargin, (int)(10 * topMargin - topBarHeight - gs.getSmallFont().getLineHeight()));
        }
        sb.end();


        int inventoryShowerHeight = tileHeight * 2;Point pt = new Point(tileWidth, tileWidth);

        inventoryShower.draw(sb,    MasterOfMonsGame.WIDTH/2 - MasterOfMonsGame.WIDTH/4, inventoryShowerHeight,pt);
        inventoryShower.draw(sb, MasterOfMonsGame.WIDTH/2 + MasterOfMonsGame.WIDTH/4, inventoryShowerHeight,pt);

        lifeBar.draw(sb, (int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        lifeBar.draw(sb, (int)MasterOfMonsGame.WIDTH-(topBarWidth+20), MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);

        questShower.draw(sb, MasterOfMonsGame.WIDTH / 2 - questShower.getWidth()/2, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight-40));//TODO

        Point objectSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
        pauseButton.draw(sb, new Point(MasterOfMonsGame.WIDTH/2 -objectSize.x-10, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin+25)),objectSize);
        endDual.draw(sb,new Point(MasterOfMonsGame.WIDTH/2 +objectSize.x+10, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin+25)),objectSize);

        if (supervisorDual.getDual().equals(TypeDual.OccupationFloor))
        {
            Point textSize = new Point((int)( gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(topMargin + gs.getSmallFont().getLineHeight()));
            player1Number.draw(sb,new Point(objectSize.x, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin)),textSize);
            player2Number.draw(sb,new Point(MasterOfMonsGame.WIDTH - objectSize.x, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin)),textSize);
        }
    }


    /***/
    private void changedCam()
    {
        cam.setToOrtho(false,SHOWED_MAP_WIDTH * tileWidth/2, SHOWED_MAP_HEIGHT * tileHeight/2);//TODO changed
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        gmm.setView(cam);
        cam.update();
    }


    /***/
    private void checkCase(Player player)
    {
        if (!old.get(player).equals(new Point(player.getPosX(),player.getPosY())))
        {
            old.replace(player,new Point(player.getPosX(),player.getPosY()));
            if (player.equals(this.player))
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), Color.BLUE));//TODO
            else
                drawCase.add(new Cases(gs,player.getPosX(),player.getPosY(), Color.RED));//TODO
            cases.replace(player,cases.get(player)+1);
            old.replace(player,new Point(player.getPosX(),player.getPosY()));
            if(supervisorDual.getDual().equals(TypeDual.OccupationFloor))//TODO
                ((MoreCasesMons)((DualMasterQuest)SupervisorDual.getSupervisorDual().actualQuest()).getUnderQuest((People)player.getCharacteristics())).callMe(1);
        }
    }


    /***/
    @Override
    public void handleInput()
    {
        if (gim.isKey("pickUpAnObjectTwo", KeyStatus.Pressed))
        {
            if (selectedOne instanceof Character)
                return;
            else
            {
                if (SupervisorMultiPlayer.getPeopleTwo().pushObject(((MapObject) selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        if (gim.isKey("attackTwo", KeyStatus.Pressed))
        {
            pnjs.add(player);
            attack(playerTwo);
            pnjs.remove(player);
        }
        if (gim.isKey("pickUpAnObject", KeyStatus.Pressed))
        {
            if (selectedOne instanceof Character)
                return;
            else
            {
                if (SupervisorMultiPlayer.getPeopleTwo().pushObject(((MapObject) selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        else
            super.handleInput();

        endDual.handleInput();
        //inventoryShowerTwo.handleInput();TODO
    }


    /***/
    @Override
    protected void attack(Player player)
    {
        if (supervisorDual.getDual().equals(TypeDual.DualPlayer) || SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.CatchFlag))
            pnjs.add(adv.get(player));

        super.attack(player);

        if (supervisorDual.getDual().equals(TypeDual.DualPlayer) || SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.CatchFlag))
            pnjs.remove(adv.get(player));
    }


    /***/
    @Override
    protected void checkForNearSelectable(Player player)//TODO upgrade
    {
        if (supervisorDual.getDual().equals(TypeDual.DualPlayer))
            pnjs.add(adv.get(player));

        super.checkForNearSelectable(player);

        if (supervisorDual.getDual().equals(TypeDual.DualPlayer))
            pnjs.remove(adv.get(player));
    }


    /**
     * Make the player move and check that the position of the player is not out of the map.
     * @param dt The delta time
     */
    @Override
    protected void makePlayerMove(float dt)
    {
        super.makePlayerMove(dt);
        int toMove = (int)Math.round(SupervisorMultiPlayer.getPeopleTwo().getSpeed() * dt * tileWidth);
        int toMoveX=0, toMoveY=0;

        if (gim.isKey("movingDownwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Bottom);
            toMoveY += -toMove;
        }
        if (gim.isKey("movingUpwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        if (gim.isKey("movingLeftwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        if (gim.isKey("movingRightwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Right);
            toMoveX += toMove;
        }

        playerTwo.move(toMoveX, toMoveY);
        if (checkForCollision(playerTwo))
        {
            playerTwo.move(-toMoveX, -toMoveY);
        }

        checkForNearSelectable(playerTwo);
        checkForAboutCollision(playerTwo);
    }


    /***/
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class))
            gsm.removeAllStateAndAdd(DualChooseMenu.class);

    }


    /***/
    public void initSizeOfMaps()
    {
        playerTwo.setMapWidth(mapWidth * tileWidth);
        playerTwo.setMapHeight(mapHeight * tileHeight);
        playerTwo.setTileWidth(tileWidth);
        playerTwo.setTileHeight(tileHeight);
    }


    /***/
    @Override
    public void dispose()
    {
        super.dispose();
        endDual.dispose();
        lifeBarTwo.dispose();
        if (supervisorDual.getDual().equals(TypeDual.OccupationFloor))
        {
            player1Number.dispose();
            player2Number.dispose();
        }
    }
}