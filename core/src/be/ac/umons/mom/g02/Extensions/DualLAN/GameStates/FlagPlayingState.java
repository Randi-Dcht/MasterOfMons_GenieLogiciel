package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Events.Notifications.InventoryChanged;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingFlag;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Cases;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Items.Flag;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WinMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.InventoryItem;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Items;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

/**
 * The playing state when the player choose to play the dual "Flag"
 */
public class FlagPlayingState extends PlayingFlag implements NetworkReady {

    /**
     * The network manager of the game
     */
    protected NetworkManager nm;

    /**
     * If the color of the bases are inverted or not.
     */
    protected boolean colorInverted;
    /**
     * If we already called the changing to <code>WinMenu</code>
     * @see WinMenu
     */
    protected boolean changingAlreadyCalled = false;
    /**
     * If a flag has already been picked up.
     */
    protected boolean flagPickedUp = false; // Useful because the inventory is cleared at each game -> Can induce bug

    /**
     * @param gs The graphical settings to use
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
        PlayingDualLANHelper.init(this);

        pos = false;

        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendMessageOnTCP("Pause");
            PlayingLANHelper.pauseSent = true;
        });

        endDual.setOnClick(() -> {
            PlayingDualLANHelper.goToChoosingMenu();
            nm.sendOnTCP("EndDual");
        });

        PlayingDualLANHelper.setNetworkManagerRunnable(this);
        nm.whenMessageReceivedDo("FPU", objects -> {
            MapObject.OnMapItem rec = (MapObject.OnMapItem) objects[0];
            for (int i = 0; i < mapObjects.size(); i++) {
                MapObject.OnMapItem fomi = mapObjects.get(i).getCharacteristics();
                if (! fomi.getItem().idOfPlace().equals(rec.getItem().idOfPlace()) && // ! because inversed camp (and only two camps)
                        fomi.getMapPos().equals(rec.getMapPos())) {
                    mapObjects.remove(i);
                    flagPickedUp = true;
                    break;
                }
            }
        });
        nm.whenMessageReceivedDo("Item", (objects) -> {
            Flag f = (Flag) ((MapObject.OnMapItem)objects[0]).getItem();
            invertFlagColor(f);
            addItemToMap((MapObject.OnMapItem) objects[0]);
        });
        nm.sendMessageOnTCP("getItemsPos");

        nm.whenMessageReceivedDo("CI", (objects) -> { // Clear Inventory
            super.cleanInventory((People) player.getCharacteristics());
            super.cleanInventory((People) playerTwo.getCharacteristics());
        });

        for (Items it : player.getCharacteristics().getInventory()) // Can be done because the inventory was cleared if we wasn't disconnected
            invertFlagColor((Flag)it); // Only flags in inventory (useful when disconnected)
        for (Items it : playerTwo.getCharacteristics().getInventory())
            invertFlagColor((Flag)it); // Only flags in inventory (useful when disconnected)

        nm.whenMessageReceivedDo("IC", objects -> {
            Items it = (Items)objects[0];
            InventoryChanged.Type type = (InventoryChanged.Type)objects[1];
            if (type == InventoryChanged.Type.Added)
                ((People)getSecondPlayer().getCharacteristics()).pushObject( // Only flags in inventory
                        invertFlagColor((Flag)it));
            else if (type == InventoryChanged.Type.Removed) {
                List<Items> inventory = playerTwo.getCharacteristics().getInventory();
                Items f = inventory.remove(inventory.size() - 1); // Only flags in inventory
                if (flagPickedUp) {
                    f.used(SupervisorDual.getPeopleTwo());
                    flagPickedUp = false;
                }
            }
        });

        if (! nm.isTheServer()) {
            playerTwo.setMapPos(supervisorDual.getDual().getPointPlayerOne());
            player.setMapPos(supervisorDual.getDual().getPointPlayerTwo());
        }

        setColor(! nm.isTheServer());
        nm.whenMessageReceivedDo("MICC", (objects) -> {
            setColor((boolean)objects[0]);
        });

        nm.processMessagesNotRan();
    }

    @Override
    protected void onPause() {
        PlayingDualLANHelper.onPause();
    }

    /**
     * Invert the two bases if necessary.
     * @param b If the two bases should be inverted
     */
    protected void setColor(boolean b) {
        if (b == colorInverted)
            return;
        baseOne.setColorExt(Color.BLUE);
        baseTwo.setColorExt(Color.RED);
        Cases tmp = baseOne;
        baseOne = baseTwo;
        baseTwo = tmp;
        colorInverted = b;
    }

    /**
     * Invert the color of the given flag <code>f</code>. (<code>f</code> is modified too)
     * @param f The flag
     * @return The flag with the color inverted.
     */
    protected Flag invertFlagColor(Flag f) {
        if (f.idOfPlace().equals("R")) { // R for second player => B for me
            f.setPeople((People)playerTwo.getCharacteristics(), "B");
        } else
            f.setPeople((People)player.getCharacteristics(), "R");
        return f;
    }

    @Override
    protected void checkForNearSelectable(Player player) {
        if (player == this.player)
            super.checkForNearSelectable(player);
    }

    @Override
    public void PlaceFlag() {
        if (nm.isTheServer())
            super.PlaceFlag();
    }

    @Override
    protected void pickUpAnObject() {
        nm.sendMessageOnTCP("FPU", ((MapObject) selectedOne).getCharacteristics()); // Only flag in this mode
        super.pickUpAnObject();
    }

    @Override
    protected void cleanInventory(People people) {
        if (nm.isTheServer()) {
            super.cleanInventory(people);
            nm.sendMessageOnTCP("CI"); // Clear inventory
        }
    }

    @Override
    public void finishDual() {
        if (! changingAlreadyCalled)
            gsm.setState(WinMenu.class, true);
        changingAlreadyCalled = true;
    }

    @Override
    protected void translateCamera(int x, int y) {
        translateCameraFollowingPlayer(x, y);
    }

    @Override
    public void handleInput() {
        super.handleInput();
        PlayingDualLANHelper.handleInput(this);
        if (gim.isKey("useAnObject", KeyStatus.Pressed) && baseOne.inCase(player))
        {
            InventoryItem ii = inventoryShower.getSelectedItem();
            if (ii != null)
                nm.sendMessageOnTCP("Item", new MapObject.OnMapItem(ii.getItem(), player.getMapPos(), supervisorDual.getDual().getStartMaps().getMaps()));
        }
    }

    @Override
    public MapObject dropSelectedObject() {
        MapObject mo = super.dropSelectedObject();
        nm.sendMessageOnTCP("Item", mo.getCharacteristics());
        return mo;
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
        if (nm.isTheServer())
            nm.sendMessageOnTCP("MICC", ! colorInverted); // Must Inverse Case Color
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y, int posInList) {
        return null; // No PNJ on this map
    }
}
