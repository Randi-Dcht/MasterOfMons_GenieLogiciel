package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayingFlag;
import be.ac.umons.mom.g02.Extensions.DualLAN.Helpers.PlayingDualLANHelper;
import be.ac.umons.mom.g02.Extensions.DualLAN.Interfaces.NetworkReady;
import be.ac.umons.mom.g02.Extensions.LAN.Helpers.PlayingLANHelper;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

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
        super.init();

        try {
            nm = NetworkManager.getInstance();
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

        PlayingDualLANHelper.setNetworkManagerRunnable(this);
    }

    @Override
    public Character onCharacterDetected(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y) {
        return null; // No PNJ on this map
    }

    @Override
    public HashMap<String, Character> getIdCharacterMap() {
        return null; // No PNJ on this map
    }
}
