package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingStateDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Input;

import java.awt.*;
import java.net.SocketException;
import java.util.HashMap;

public class PlayingState extends PlayingStateDual {

    protected NetworkManager nm;
    protected HashMap<String, Character> idCharacterMap;
    protected boolean pauseSent;

    /**
     * @param gs The game's graphical settings
     */
    public PlayingState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        setNetworkManagerRunnables();
        super.init();

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendMessageOnTCP("Pause");
            pauseSent = true;
        });
        endDual.setOnClick(() -> {
            goToPreviousMenu();
            nm.sendOnTCP("EndDual");
        });

    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     */
    public void setNetworkManagerRunnables() {
        nm.whenMessageReceivedDo("PNJ", (objects) -> onCharacterDetected(
                (String)objects[0],
                (be.ac.umons.mom.g02.Objects.Characters.Character)objects[3],
                (int)objects[1], (int)objects[2]
        ));
        nm.whenMessageReceivedDo("MPNJ", (objects) ->
        {
            MovingPNJ mpnj = new MovingPNJ((Bloc)objects[1], (MobileType) objects[2], (Maps) objects[3], (Actions) objects[4]);
            mpnj.initialisation(onCharacterDetected((String) objects[0], mpnj, (int) objects[5], (int) objects[6]),
                    this, player);
        });
        nm.whenMessageReceivedDo("Item", (objects) -> addItemToMap((MapObject.OnMapItem) objects[0]));
        nm.whenMessageReceivedDo("getPNJsPos", (objects) ->
                PlayingLANHelper.sendPNJsPositions((String) objects[0], idCharacterMap));
        nm.whenMessageReceivedDo("PP", (objects -> setSecondPlayerPosition((Point) objects[0])));
        nm.whenMessageReceivedDo("SPP", (objects) -> player.setMapPos((Point) objects[0]));
        nm.whenMessageReceivedDo("hitPNJ", (objects) -> {
            Character c = idCharacterMap.get(objects[0]);
            if (c != null) {
                c.getCharacteristics().setActualLife((double) objects[1]);
                playerTwo.expandAttackCircle();
            }
        });
        nm.whenMessageReceivedDo("PNJDeath", (objects) -> {
            String name = (String) objects[0];
            if (idCharacterMap.containsKey(name)) {
                SupervisorLAN.getSupervisor().addADeathToIgnore((Mobile)idCharacterMap.get(name).getCharacteristics());
                Supervisor.getEvent().notify(new Dead(idCharacterMap.get(name).getCharacteristics()));
            }
        });
        nm.whenMessageReceivedDo("Pause", (objects) -> gsm.setState(PauseMenuState.class));
        nm.whenMessageReceivedDo("EndPause", (objects) -> gsm.removeFirstState());
        nm.whenMessageReceivedDo("EMQ", (objects) -> {
            timeShower.extendOnFullWidth(GraphicalSettings.getStringFromId("secondPlayerFinishedQuest"));
            SupervisorLAN.getPeople().getQuest().passQuest();
        });
        nm.setOnDisconnected(() -> {
            DisconnectedMenuState dms = (DisconnectedMenuState) gsm.setState(DisconnectedMenuState.class);
            dms.setSecondPlayerPosition(playerTwo.getMapPos());
        });
        nm.whenMessageReceivedDo("LVLUP", (objects) -> {
            int newLevel = (int)objects[0];
            while (newLevel > playerTwo.getCharacteristics().getLevel())
                ((People)playerTwo.getCharacteristics()).upLevel();
            timeShower.extendOnFullWidth(String.format(GraphicalSettings.getStringFromId("secondPlayerLVLUP"), playerTwo.getCharacteristics().getLevel()));
        });
        nm.whenMessageReceivedDo("getItemsPos", (objects ->
                PlayingLANHelper.sendItemsPositions(mapObjects)));
        nm.whenMessageReceivedDo("Death", (objects) -> goToPreviousMenu());
        nm.whenMessageReceivedDo("IPU", (objects) -> {
            for (int i = 0; i < mapObjects.size(); i++)
                if (mapObjects.get(i).getCharacteristics().equals(objects[0]))
                    mapObjects.remove(i);
        });
        nm.whenMessageReceivedDo("PL", (objects -> {
            lifeBarTwo.setValue((int)(objects[0]));
        }));
        nm.whenMessageReceivedDo("EndDual", objects -> goToPreviousMenu());
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        nm.update(dt);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        nm.sendMessageOnUDP("PP", player.getMapPos());

        if (gim.isKey(Input.Keys.ESCAPE, KeyStatus.Pressed)) {
            nm.sendOnTCP("Pause");
            pauseSent = true;
        }
    }

    @Override
    protected void translateCamera(int x, int y) {
        translateCameraFollowingPlayer(x, y);
    }

    /**
     * Executed when the second player sends a character characteristics. It adds the character to the map.
     * @param name The character's name
     * @param mob The character's characteristics
     * @param x The horizontal position on the map (pixel)
     * @param y The vertical position on the map (pixel)
     * @return The graphical object associated with the character
     */
    private Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
        Character c = new Character(gs, mob);
        pnjs.add(c);
        idCharacterMap.put(name, c);
        c.setMapPos(new Point(x, y));
        c.setMapWidth(mapWidth * tileWidth);
        c.setMapHeight(mapHeight * tileHeight);
        c.setTileWidth(tileWidth);
        c.setTileHeight(tileHeight);
        return c;
    }

    @Override
    public void update(Notification notify) {
        super.update(notify);
        if (notify.getEvents().equals(Events.Dead) && notify.bufferNotEmpty() && notify.getBuffer().getClass().equals(People.class)) {
            nm.sendOnTCP("Death");
            goToPreviousMenu();
        } else if (notify.getEvents().equals(Events.Attack)) {
            nm.sendMessageOnUDP("PL", player.getCharacteristics().getActualLife());
        }
    }

    protected void goToPreviousMenu() {
        if (nm.isTheServer())
            gsm.removeAllStateAndAdd(DualChooseMenu.class);
        else
            gsm.removeAllStateAndAdd(WaitMenuState.class);
    }
}
