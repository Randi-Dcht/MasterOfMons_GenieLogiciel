package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingStateDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.awt.*;
import java.net.SocketException;
import java.util.HashMap;

public class PlayingState extends PlayingStateDual implements NetworkReady {

    protected NetworkManager nm;
    /**
     * The hashmap making the link between a character's name and its graphical object
     */
    protected HashMap<String, Character> idCharacterMap;

    /**
     * @param gs The game's graphical settings
     */
    public PlayingState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {

        Supervisor.getEvent().add(this, Events.LifeChanged);

        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        super.init();

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendMessageOnTCP("Pause");
            PlayingLANHelper.pauseSent = true;
        });
        endDual.setOnClick(() -> {
            PlayingDualLANHelper.goToPreviousMenu();
            nm.sendOnTCP("EndDual");
        });

        PlayingDualLANHelper.setNetworkManagerRunnable(this);

    }

    @Override
    public MapObject dropSelectedObject() {
        MapObject mo = super.dropSelectedObject();
        nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        return mo;
    }
    @Override
    protected void pickUpAnObject() {
        nm.sendMessageOnTCP("IPU", ((MapObject) selectedOne).getCharacteristics());
        super.pickUpAnObject();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        nm.update(dt);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        PlayingDualLANHelper.handleInput(this);
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
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
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
        PlayingDualLANHelper.update(this, notify);
    }

    @Override
    public void getFocus() {
        super.getFocus();
        PlayingDualLANHelper.getFocus();
    }

    public HashMap<String, Character> getIdCharacterMap() {
        return idCharacterMap;
    }
}
