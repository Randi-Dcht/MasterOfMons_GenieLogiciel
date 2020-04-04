package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingFlag;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WinMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.awt.*;
import java.net.SocketException;
import java.util.HashMap;

public class FlagPlayingState extends PlayingFlag implements NetworkReady {

    NetworkManager nm;

    /**
     * @param gs
     */
    public FlagPlayingState(GraphicalSettings gs) {
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
        nm.whenMessageReceivedDo("FPU", objects -> {
            MapObject.OnMapItem rec = (MapObject.OnMapItem) objects[0];
            for (int i = 0; i < mapObjects.size(); i++) {
                MapObject.OnMapItem fomi = mapObjects.get(i).getCharacteristics();
                if (fomi.getItem().idOfPlace().equals(rec.getItem().idOfPlace()) &&
                        fomi.getMapPos().equals(rec.getMapPos())) {
                    mapObjects.remove(i);
                    break;
                }
            }
        });
        nm.sendMessageOnTCP("getItemsPos");
    }

    @Override
    protected void checkForNearSelectable(Player player) {
        if (player == this.player)
            super.checkForNearSelectable(player);
    }

    @Override
    public void PlaceFlag(Point... pt) {
        if (nm.isTheServer())
            super.PlaceFlag(pt);
    }

    @Override
    public void finishDual() {
        gsm.setState(WinMenu.class, true);
    }

    @Override
    protected void translateCamera(int x, int y) {
        translateCameraFollowingPlayer(x, y);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        PlayingDualLANHelper.handleInput(this);
    }

    @Override
    public MapObject dropSelectedObject() {
        MapObject mo = super.dropSelectedObject();
        nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        return mo;
    }
    @Override
    protected void pickUpAnObject() {
        nm.sendMessageOnTCP("FPU", ((MapObject) selectedOne).getCharacteristics()); // Only flag in this mode
        super.pickUpAnObject();
    }

    @Override
    public void update(Notification notify) {
        super.update(notify);
        PlayingDualLANHelper.update(this, notify);
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
        return null; // No PNJ on this map
    }

    @Override
    public HashMap<String, Character> getIdCharacterMap() {
        return null; // No PNJ on this map
    }

    @Override
    public void getFocus() {
        super.getFocus();
        PlayingDualLANHelper.getFocus();
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y, int posInList) {
        return null; // No PNJ on this map
    }
}
