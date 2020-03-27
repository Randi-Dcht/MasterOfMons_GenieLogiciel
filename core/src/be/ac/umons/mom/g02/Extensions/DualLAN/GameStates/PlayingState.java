package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.Menu.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingStateDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

public class PlayingState extends PlayingStateDual {

    NetworkManager nm;
    protected HashMap<String, Character> idCharacterMap;

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
        super.init();

    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     */
    protected void setNetworkManagerRunnables() { // TODO : Find a way to remove copy-paste
        nm.setOnPNJDetected(this::onCharacterDetected);
        nm.setOnMovingPNJDetected((name, mob, x, y) ->
                ((MovingPNJ)mob).initialisation(
                        onCharacterDetected(name, mob, x, y), this, player
                ));
        nm.setOnItemDetected(this::addItemToMap);
        nm.setOnGetPNJ(this::sendPNJsPositions);
        nm.setOnPositionDetected(this::setSecondPlayerPosition);
        nm.setOnSecondPlayerPositionDetected((pos) -> player.setMapPos(pos));
        nm.setOnHitPNJ((name, life) -> {
            Character c = idCharacterMap.get(name);
            if (c != null) {
                c.getCharacteristics().setActualLife(life);
                playerTwo.expandAttackCircle();
            }
        });
        nm.setOnPNJDeath((name) -> {
            if (idCharacterMap.containsKey(name)) {
                SupervisorDual.getSupervisorDual().addADeathToIgnore((Mobile)idCharacterMap.get(name).getCharacteristics());
                Supervisor.getEvent().notify(new Dead(idCharacterMap.get(name).getCharacteristics()));
            }
        });
        nm.setOnPause(() -> gsm.setState(PauseMenuState.class));
        nm.setOnEndPause(() -> gsm.removeFirstState());
        nm.setOnMasterQuestFinished(() -> {
            timeShower.extendOnFullWidth(gs.getStringFromId("secondPlayerFinishedQuest"));
            Supervisor.getPeople().getQuest().passQuest();
        });
        nm.setOnDisconnected(() -> gsm.setState(DisconnectedMenuState.class));
        nm.setOnLevelUp((newLevel) -> {
            while (newLevel > playerTwo.getCharacteristics().getLevel())
                ((People)playerTwo.getCharacteristics()).upLevel();
            timeShower.extendOnFullWidth(String.format(gs.getStringFromId("secondPlayerLVLUP"), playerTwo.getCharacteristics().getLevel()));
        });
        nm.setOnGetItem(this::sendItemsPositions);
        nm.setOnDeath(() -> gsm.removeAllStateAndAdd(DualChooseMenu.class));
        nm.setOnItemPickUp((omi) -> {
            for (int i = 0; i < mapObjects.size(); i++)
                if (mapObjects.get(i).getCharacteristics().equals(omi))
                    mapObjects.remove(i);
        });
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

    /**
     * Send all the PNJs positions to the second player.
     * @param map The map asked.
     */
    protected void sendPNJsPositions(String map) {
        for (Mobile mob : supervisor.getMobile(supervisor.getMaps(map))) {
            try {
                nm.sendPNJInformation(idCharacterMap.get(mob.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (MovingPNJ mob : supervisor.getMovingPnj(supervisor.getMaps(map))) {
            Character c = idCharacterMap.get(mob.getName());
            nm.sendAMovingPNJ(mob, c.getPosX(), c.getPosY());
        }
    }
    /**
     * Send all the items positions to the second player.
     */
    protected void sendItemsPositions() {
        for (MapObject mo : mapObjects) {
            try {
                nm.sendItemInformation(mo.getCharacteristics());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
