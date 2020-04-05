package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayCases;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WinMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.net.SocketException;
import java.util.HashMap;

/**
 * The playing state when the user choose the play the dual "Cases"
 */
public class CasesPlayingState extends PlayCases implements NetworkReady {

    /**
     * The NetworkManager to use
     */
    protected NetworkManager nm;
    /**
     * The number of cases of each player (to show)
     */
    protected int cp1, cp2;

    /**
     * If we already called the changing to <code>WinMenu</code>
     * @see WinMenu
     */
    protected boolean changingAlreadyCalled = false;

    /**
     * @param gs The game's graphical settings
     */
    public CasesPlayingState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();

        try {
            nm = NetworkManager.getInstance();
            setNetworkManagerRunnables();
        } catch (SocketException e) {
            e.printStackTrace();
        }


        pauseButton.setOnClick(() -> {
            gsm.setState(InGameMenuState.class);
            nm.sendMessageOnTCP("Pause");
            PlayingLANHelper.pauseSent = true;
        });

        endDual.setOnClick(() -> {
            PlayingDualLANHelper.goToPreviousMenu();
            nm.sendOnTCP("EndDual");
        });
    }

    /**
     * Set all the needed network manager's runnables except the one for setting the map changing (must be done earlier)
     */
    public void setNetworkManagerRunnables() {
        PlayingDualLANHelper.setNetworkManagerRunnable(this);
        nm.whenMessageReceivedDo("Time", objects -> time = (double)objects[0]);
        nm.whenMessageReceivedDo("CP1", objects -> cp2 = (int)objects[0]);
        nm.whenMessageReceivedDo("CP2", objects -> cp1 = (int)objects[0]);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (nm.isTheServer()) {
            nm.sendMessageOnUDP("Time", time);
            cp1 = cases.get(player).size();
            cp2 = cases.get(playerTwo).size();
            nm.sendMessageOnUDP("CP1", cp1);
            nm.sendMessageOnUDP("CP2", cp2);
        }

        player1Number.setText("" + cp1);
        player2Number.setText("" + cp2);
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
    public void finishDual() {
        if (! changingAlreadyCalled)
            gsm.setState(WinMenu.class, true);
        changingAlreadyCalled = true;
    }

    @Override
    public void getFocus() {
        super.getFocus();
        PlayingDualLANHelper.getFocus();
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
        return null; // NO PNJ IN THIS MODE
    }

    @Override
    public HashMap<String, Character> getIdCharacterMap() {
        return null; // NO PNJ IN THIS MODE
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y, int posInList) {
        return null; // NO PNJ IN THIS MODE
    }
}
