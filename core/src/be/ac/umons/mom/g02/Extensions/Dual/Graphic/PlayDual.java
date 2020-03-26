package be.ac.umons.mom.g02.Extensions.Dual.Graphic;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Mobile.ZombiePNJ;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.Button;
import be.ac.umons.mom.g02.GraphicalObjects.LifeBar;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.graphics.Color;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;


/***/
public class PlayDual extends PlayingState

{
    protected LifeBar lifeBarTwo;
    protected Button endDual;
    protected HashMap<Player,Player> adv = new HashMap<>();
    protected HashMap<Player,Integer> cases = new HashMap<>();
    protected ArrayList<Cases> drawCase = new ArrayList<>();
    protected HashMap<Player,Point> old = new HashMap<>();

    /**
     * @param gs The game's graphical settings
     */
    public PlayDual(GraphicalSettings gs)
    {
        super(gs);
        supervisor = Supervisor.getSupervisor();
    }

    @Override
    public void init()
    {
        super.init();
        setSecondPlayerCharacteristics(SupervisorDual.getPeopleTwo());
        //TODO setter the inventory for the second player
        lifeBarTwo = new LifeBar(gs);
        lifeBarTwo.setForegroundColor(gcm.getColorFor("lifeBar"));
        if (SupervisorDual.getSupervisorDual().getDual().getStartMaps().equals(Maps.DualKiosk)) //TODO found a solution for this -> delete the condition
            initMap(SupervisorDual.getSupervisorDual().getDual().getStartMaps().getMaps(),15,15);//SupervisorDual.getSupervisorDual().getDual().getStartMaps().getMaps()
        else
            initMap(SupervisorDual.getSupervisorDual().getDual().getStartMaps().getMaps(),8,24);
        playerTwo.setMapPos(new Point(player.getPosX(),player.getPosY()));
        cam.position.x = player.getPosX();
        cam.position.y = player.getPosY();
        cam.update();
        initSizeOfMaps();
        SupervisorDual.setGraphic(gs);
        adv.put(player,playerTwo);adv.put(playerTwo,player);
        if (SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.Survivor))
        {
            for (Character ch : pnjs)
            {
                if (ch.getCharacteristics().getClass().equals(ZombiePNJ.class))
                    ((ZombiePNJ)ch.getCharacteristics()).initialisation(ch,player);
            }

        }

        initPNJsPositions(getPNJsOnMap(SupervisorDual.getSupervisorDual().getDual().getStartMaps().getMaps()));//TODO
        questShower.setQuest(Supervisor.getSupervisor().actualQuest());

        endDual = new Button(gs);
        endDual.setText("X");
        endDual.setOnClick(() -> gsm.removeAllStateAndAdd(DualChooseMenu.class));
        endDual.setFont(gs.getSmallFont());
    }

    @Override
    public void update(float dt)
    {
        super.update(dt);
        lifeBarTwo.setValue((int)playerTwo.getCharacteristics().getActualLife());
        lifeBarTwo.setMaxValue((int)playerTwo.getCharacteristics().lifeMax());
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
        }TODO this
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

        Point ButtonSize = new Point((int)(2 * gs.getSmallFont().getXHeight() + 2 * leftMargin), (int)(2 * topMargin + gs.getSmallFont().getLineHeight()));
        pauseButton.draw(sb, new Point(MasterOfMonsGame.WIDTH/2 -ButtonSize.x-10, (int)(MasterOfMonsGame.HEIGHT - ButtonSize.y - topBarHeight - 2 * topMargin+25)),ButtonSize);
        endDual.draw(sb,new Point(MasterOfMonsGame.WIDTH/2 +ButtonSize.x+10, (int)(MasterOfMonsGame.HEIGHT - ButtonSize.y - topBarHeight - 2 * topMargin+25)),ButtonSize);
    }


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
        }
    }


    @Override
    public void handleInput()
    {
        super.handleInput();
        if (gim.isKey("pickUpAnObjectTwo", KeyStatus.Pressed))
        {
            if (selectedOne instanceof Character)
                supervisor.meetCharacter(playerTwo.getCharacteristics(), ((Character) selectedOne).getCharacteristics());
            else
            {
                if (SupervisorMultiPlayer.getPeopleTwo().pushObject(((MapObject) selectedOne).getItem()))
                    pickUpAnObject();
            }
        }
        if (gim.isKey("attackTwo", KeyStatus.Pressed))
            attack(playerTwo);

        endDual.handleInput();
        //inventoryShowerTwo.handleInput();TODO
    }

    @Override
    protected void checkForNearSelectable(Player player)//TODO upgrade
    {
        if (SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.DualPlayer))
            pnjs.add(adv.get(player));

        super.checkForNearSelectable(player);

        if (SupervisorDual.getSupervisorDual().getDual().equals(TypeDual.DualPlayer))
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

    public void initSizeOfMaps()
    {
        playerTwo.setMapWidth(mapWidth * tileWidth);
        playerTwo.setMapHeight(mapHeight * tileHeight);
        playerTwo.setTileWidth(tileWidth);
        playerTwo.setTileHeight(tileHeight);
    }

    @Override
    public void dispose()
    {
        super.dispose();
        endDual.dispose();
        lifeBarTwo.dispose();
    }
}