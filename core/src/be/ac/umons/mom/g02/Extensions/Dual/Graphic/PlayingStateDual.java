package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualPauseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.WinMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.InventoryShower;
import be.ac.umons.mom.g02.GraphicalObjects.LifeBar;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import java.awt.Point;
import java.util.HashMap;



/**
 * This class define the playingState for the dual extension
 */
public class PlayingStateDual extends PlayingState
{
    /**
     * The constance of the camera on the axe X
     */
    protected static int cam_X_pos = MasterOfMonsGame.WIDTH/2 + MasterOfMonsGame.WIDTH/4;
    /**
     * The constance of the camera on the axe Y
     */
    protected static int cam_Y_pos = MasterOfMonsGame.HEIGHT - MasterOfMonsGame.HEIGHT/2;
    /**
     * The second life bar for the second player
     */
    protected LifeBar lifeBarTwo;
    /**
     * The button to quit the dual
     */
    protected Button endDual;
    /**
     * The association between player and adversary
     */
    protected HashMap<Player,Player> adv = new HashMap<>();
    /**
     * The supervisor of the dual
     */
    protected SupervisorDual supervisorDual;
    /**
     * The size of the object to draw
     */
    protected Point objectSize;
    /**
     * The second inventory shower
     */
    protected InventoryShower inventoryShowerTwo;
    /**
     * If the player is living
     */
    protected HashMap<Player,Boolean> playerLife = new HashMap<>();
    /**
     * If the cam must be fixed
     */
    protected boolean pos = true;
    /**
     * The size of the list (mobile draw on maps)
     */
    protected int sizeList = 25;
    /**
     * The time to add the new mobile on the maps
     */
    protected double time = 7;
    /**
     * If the players can relive
     */
    protected boolean relive = false;


    /**
     * @param gs The game's graphical settings
     */
    public PlayingStateDual(GraphicalSettings gs)
    {
        super(gs);
        supervisor = Supervisor.getSupervisor();
        supervisorDual = SupervisorDual.getSupervisorDual();
    }


    /**
     * This method allows to initialization of the playingState of the dual
     */
    @Override
    public void init()
    {
        Supervisor.getSupervisor().setMustPlaceItem(false);
        super.init();

        setSecondPlayerCharacteristics(SupervisorDual.getPeopleTwo());
        lifeBarTwo = new LifeBar(gs);
        lifeBarTwo.setForegroundColor(gcm.getColorFor("lifeBar"));
        initMap(supervisorDual.getDual().getStartMaps().getMaps(),supervisorDual.getDual().getPointPlayerOne().x,supervisorDual.getDual().getPointPlayerOne().y);
        playerTwo.setMapPos(supervisorDual.getDual().getPointPlayerTwo());
        translateCamera(cam_X_pos,cam_Y_pos);
        initSizeOfMaps();
        SupervisorDual.setGraphic(gs);

        inventoryShowerTwo = new InventoryShower(gs, playerTwo);

        adv.put(player,playerTwo);
        adv.put(playerTwo,player);

        playerLife.put(player,true);
        playerLife.put(playerTwo,true);

        if (supervisorDual.getDual().equals(TypeDual.Survivor))
        {
            for (Character ch : pnjs)
            {
                if (ch.getCharacteristics().getClass().equals(ZombiePNJ.class))
                   ((ZombiePNJ)ch.getCharacteristics()).initialisation(ch,player);
            }

        }

        initPNJsPositions(getPNJsOnMap(supervisorDual.getDual().getStartMaps().getMaps()));
        questShower.setQuest(Supervisor.getSupervisor().actualQuest());


        endDual = new Button(gs);
        endDual.setText("X");
        endDual.setOnClick(() -> gsm.removeAllStateAndAdd(DualChooseMenu.class));
        pauseButton.setOnClick(()-> gsm.setState(DualPauseMenu.class));
        endDual.setFont(gs.getSmallFont());

        if (supervisorDual.getDual().equals(TypeDual.CatchFlag) || SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.OccupationFloor))
            changedCam();

        objectSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
        relive = supervisorDual.getDual().canReLife();
    }


    /**
     * This methods allows to move the camera
     * @param x is translate on X
     * @param y is translate on Y
     */
    @Override
    protected void translateCamera(int x, int y)
    {
        cam.position.x = x;
        cam.position.y = y;
        cam.update();
    }


    /**
     * This method allows to update the objects every changed frames
     * @param dt is the time between two frames
     */
    @Override
    public void update(float dt)
    {
        super.update(dt);
        lifeBarTwo.setValue((int)playerTwo.getCharacteristics().getActualLife());
        lifeBarTwo.setMaxValue((int)playerTwo.getCharacteristics().lifeMax());

        if (SupervisorDual.getSupervisorDual().finish())
            finishDual();

        if ((cam.position.x != cam_X_pos|| cam.position.y != cam_Y_pos) && pos)
            translateCamera(cam_X_pos,cam_Y_pos);

        if (!playerLife.get(player) && relive)
            lifePlayer(player,dt);

        if (!playerLife.get(playerTwo) && relive)
            lifePlayer(playerTwo,dt);

        if (supervisorDual.getDual().equals(TypeDual.Survivor))
        {
            time -= dt;
            if (time <= 0)
            {
                sizeList++;
                time = 7;
            }
        }
    }


    /**
     * This method allows to draw the graphic object on the screen
     */
    @Override
    public void draw()
    {

        int topBarWidth = (int)((MasterOfMonsGame.WIDTH - 4 * leftMargin) / 3);
        int topBarHeight = 10;

        gmm.render();

        drawAfterMaps();

        if (playerLife.get(player))
            player.draw(sb, player.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, player.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);
        if (playerLife.get(playerTwo))
            playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, playerTwo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

        for (int i=0;i<Math.min(sizeList,pnjs.size());i++)
            pnjs.get(i).draw(sb, pnjs.get(i).getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, pnjs.get(i).getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

        for (MapObject mo : mapObjects)
                mo.draw(sb, mo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, mo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, tileHeight);

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
        inventoryShowerTwo.draw(sb, MasterOfMonsGame.WIDTH/2 + MasterOfMonsGame.WIDTH/4, inventoryShowerHeight,pt);

        lifeBar.draw(sb, (int)leftMargin, MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);
        lifeBarTwo.draw(sb, (int)MasterOfMonsGame.WIDTH-(topBarWidth+20), MasterOfMonsGame.HEIGHT - (int)topMargin - topBarHeight, topBarWidth, topBarHeight);

        questShower.draw(sb, MasterOfMonsGame.WIDTH / 2 - questShower.getWidth()/2, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight-40));

        pauseButton.draw(sb, new Point(MasterOfMonsGame.WIDTH/2 -objectSize.x-10, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin+25)),objectSize);
        endDual.draw(sb,new Point(MasterOfMonsGame.WIDTH/2 +objectSize.x+10, (int)(MasterOfMonsGame.HEIGHT - objectSize.y - topBarHeight - 2 * topMargin+25)),objectSize);

    }


    /**
     * This method allows to draw the object between map et player
     */
    protected void drawAfterMaps(){}


    /**
     * This methods allows to changed the orientation of the camera
     */
    private void changedCam()
    {
        cam.setToOrtho(false,SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight);
        gmm.setView(cam);
        cam.update();
    }


    /**
     * This methods allows to analyse the input on the keyboard
     */
    @Override
    public void handleInput()
    {
        if (gim.isKey("pickUpAnObjectTwo", KeyStatus.Pressed) && playerLife.get(playerTwo) && selectedOne != null)
        {
            if (selectedOne instanceof Character)
                return;
            else
            {
                if (SupervisorMultiPlayer.getPeopleTwo().pushObject(((MapObject) selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        if (gim.isKey("attackTwo", KeyStatus.Pressed) && playerLife.get(playerTwo))
        {
            pnjs.add(player);
            attack(playerTwo);
            pnjs.remove(player);
        }

        if (playerLife.get(player))
            super.handleInput();

        endDual.handleInput();
        inventoryShowerTwo.handleInput();
    }


    /**
     * Add the second player to the list of can attack
     * @param player is the player to add
     */
    @Override
    protected void attack(Player player)
    {
        if (!supervisorDual.getDual().equals(TypeDual.Survivor))
            pnjs.add(adv.get(player));

        super.attack(player);

        if (!supervisorDual.getDual().equals(TypeDual.Survivor))
            pnjs.remove(adv.get(player));
    }


    /**
     * Add the second player to select this
     * @param player is the player to add
     */
    @Override
    protected void checkForNearSelectable(Player player)
    {
        if (!supervisorDual.getDual().equals(TypeDual.Survivor) && playerLife.get(adv.get(player)))
            pnjs.add(adv.get(player));

        super.checkForNearSelectable(player);

        if (!supervisorDual.getDual().equals(TypeDual.Survivor) && playerLife.get(adv.get(player)))
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
        else if (gim.isKey("movingUpwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        else if (gim.isKey("movingLeftwardSecond", KeyStatus.Down))
        {
            playerTwo.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        else if (gim.isKey("movingRightwardSecond", KeyStatus.Down))
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


    /**
     * This method receive the notification of the game
     * @param notify is the changed on the game
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class))
        {
            if (supervisorDual.getDual().equals(TypeDual.DualPlayer) || supervisorDual.getDual().equals(TypeDual.Survivor))
                finishDual();
            else
                queuingToPlay((People)notify.getBuffer());
        }
        else if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer() instanceof Mobile )
            deadMobile((Mobile)notify.getBuffer());
    }


    /**
     * When the people (human) is dead on the game
     * @param people is the player dead
     */
    protected void queuingToPlay(People people)
    {
        if (people.equals(player.getCharacteristics()))
            playerLife.replace(player,false);
        else
            playerLife.replace(playerTwo,false);
    }


    /**
     * This method allows to increase the life of the player when this is dead
     * @param player is the player dead
     * @param dt is the time frame
     */
    protected void lifePlayer(Player player, double dt)
    {
        People pp = (People)player.getCharacteristics();
        pp.setActualLife(pp.getActualLife()+dt*3);

        if (pp.getActualLife() >= pp.lifeMax()*0.9)
            whatPeople(player);
    }


    /**
     * Checks the player and pass to true the living
     */
    protected void whatPeople(Player player)
    {
        if (player.equals(this.player))
        {
            playerLife.replace(this.player,true);
            int x = (mapHeight - supervisorDual.getDual().getPointPlayerOne().y) * tileWidth / 2 + supervisorDual.getDual().getPointPlayerOne().x * tileHeight;
            int y = (mapHeight - supervisorDual.getDual().getPointPlayerOne().x - supervisorDual.getDual().getPointPlayerOne().y) * tileHeight / 2;
            player.setMapPos(new Point(x,y));
        }
        else
        {
            playerLife.replace(playerTwo,true);
            playerTwo.setMapPos(supervisorDual.getDual().getPointPlayerTwo());
        }
    }


    /**
     * This method is called when the dual finishes
     */
    protected void finishDual()
    {
        gsm.removeAllStateAndAdd(WinMenu.class);
    }


    /**
     * This method allows to remove the mobile when he dies
     * @param mb is the mobile dies
     */
    protected void deadMobile(Mobile mb)
    {
        pnjs.removeIf(chr -> chr.getCharacteristics().equals(mb));
    }


    /**
     * This method allows to init the second player with the size of the graphic object
     */
    protected void initSizeOfMaps()
    {
        playerTwo.setMapWidth(mapWidth * tileWidth);
        playerTwo.setMapHeight(mapHeight * tileHeight);
        playerTwo.setTileWidth(tileWidth);
        playerTwo.setTileHeight(tileHeight);
    }


    /**
     * Allows to dispose the graphical object (draw)
     */
    @Override
    public void dispose()
    {
        endDual.dispose();
        lifeBarTwo.dispose();
        inventoryShowerTwo.dispose();
    }
}