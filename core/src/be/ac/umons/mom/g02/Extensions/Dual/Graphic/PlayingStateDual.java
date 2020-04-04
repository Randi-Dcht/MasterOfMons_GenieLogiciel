package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
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



/***/
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
    protected boolean player1Life = true, player2Life = true;
    /**
     * If the cam must be fixed
     */
    protected boolean pos = true;


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
        //TODO setter the inventory for the second player
        lifeBarTwo = new LifeBar(gs);
        lifeBarTwo.setForegroundColor(gcm.getColorFor("lifeBar"));
        initMap(supervisorDual.getDual().getStartMaps().getMaps(),supervisorDual.getDual().getPointPlayerOne().x,supervisorDual.getDual().getPointPlayerOne().y);
        playerTwo.setMapPos(supervisorDual.getDual().getPointPlayerTwo());
        cam.position.x = MasterOfMonsGame.WIDTH/2 + MasterOfMonsGame.WIDTH/4;
        cam.position.y = MasterOfMonsGame.HEIGHT/2 - MasterOfMonsGame.HEIGHT/4;
        cam.update();
        initSizeOfMaps();
        SupervisorDual.setGraphic(gs);

        inventoryShowerTwo = new InventoryShower(gs, playerTwo);

        adv.put(player,playerTwo);
        adv.put(playerTwo,player);

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


        endDual = new Button(gs);
        endDual.setText("X");
        endDual.setOnClick(() -> gsm.removeAllStateAndAdd(DualChooseMenu.class));
        endDual.setFont(gs.getSmallFont());

        if (supervisorDual.getDual().equals(TypeDual.CatchFlag) || SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.OccupationFloor))
            changedCam();

        objectSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
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

        if ((cam.position.x != cam_X_pos|| cam.position.y != cam_Y_pos) && pos)
            translateCamera(cam_X_pos,cam_Y_pos);

        if (supervisorDual.getDual().equals(TypeDual.Survivor))
        {
            if (!player1Life)
                lifePlayer(player,dt);
            if (!player2Life)
                lifePlayer(playerTwo,dt);
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

        if (player1Life)
            player.draw(sb, player.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, player.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);
        if (player2Life)
            playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, playerTwo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

        for (int i=0;i<Math.min(25,pnjs.size());i++)
            pnjs.get(i).draw(sb, pnjs.get(i).getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, pnjs.get(i).getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

        for (MapObject mo : mapObjects)
            if (mo.getMap().equals(gmm.getActualMapName()))
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

        questShower.draw(sb, MasterOfMonsGame.WIDTH / 2 - questShower.getWidth()/2, (int)(MasterOfMonsGame.HEIGHT - 2 * topMargin - topBarHeight-40));//TODO

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
        cam.setToOrtho(false,SHOWED_MAP_WIDTH * tileWidth, SHOWED_MAP_HEIGHT * tileHeight);//TODO changed
        gmm.setView(cam);
        cam.update();
    }


    /**
     * This methods allows to analyse the input on the keyboard
     */
    @Override
    public void handleInput()
    {
        if (gim.isKey("pickUpAnObjectTwo", KeyStatus.Pressed) && player2Life && selectedOne != null)
        {
            if (selectedOne instanceof Character)
                return;
            else
            {
                if (SupervisorMultiPlayer.getPeopleTwo().pushObject(((MapObject) selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        if (gim.isKey("attackTwo", KeyStatus.Pressed) && player2Life)
        {
            pnjs.add(player);
            attack(playerTwo);
            pnjs.remove(player);
        }

        if (player1Life)
            super.handleInput();

        endDual.handleInput();
        inventoryShowerTwo.handleInput();//TODO
    }


    /**
     * Add the second player to the list of can attack
     * @param player is the player to add
     */
    @Override
    protected void attack(Player player)
    {
        if (supervisorDual.getDual().equals(TypeDual.DualPlayer) || supervisorDual.getDual().equals(TypeDual.CatchFlag))
            pnjs.add(adv.get(player));

        super.attack(player);

        if (supervisorDual.getDual().equals(TypeDual.DualPlayer) || supervisorDual.getDual().equals(TypeDual.CatchFlag))
            pnjs.remove(adv.get(player));
    }


    /**
     * Setter the fixed camera
     * @param pos is the boolean if the cam can be fixed
     */
    public void setCamPos(boolean pos)
    {
        this.pos = pos;
    }


    /**
     * Add the second player to select this
     * @param player is the player to add
     */
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
            if (supervisorDual.getDual().equals(TypeDual.DualPlayer))
                finishDual();
            else if (supervisorDual.getDual().equals(TypeDual.Survivor))
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
            player1Life = false;
        else
            player2Life = false;
    }


    /**
     * This method allows to increase the life of the player when this is dead
     * @param player is the player dead
     * @param dt is the time frame
     */
    protected void lifePlayer(Player player, double dt)
    {
        People pp = (People)player.getCharacteristics();
        pp.setActualLife(pp.getActualLife()+dt*2);

        if (pp.getActualLife() >= pp.lifeMax()*0.9)
            whatPeople(player);

    }


    /**
     * Checks the player and pass to true the living
     */
    protected void whatPeople(Player player)
    {
        if (player.equals(this.player))
            player1Life = true;
        else
            player2Life = true;
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
       // super.dispose();TODO problem with multi dispose of lifeBar
        endDual.dispose();
        lifeBarTwo.dispose();
        inventoryShowerTwo.dispose();
    }
}