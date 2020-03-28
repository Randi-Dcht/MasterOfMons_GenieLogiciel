package be.ac.umons.mom.g02.Extensions.DualLAN.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Extensions.Dual.Graphic.PlayCases;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DisconnectedMenuState;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.PauseMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.GameStates.Menus.InGameMenuState;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;

import java.awt.*;
import java.net.SocketException;

public class PlayingCasesState extends PlayCases {

    protected NetworkManager nm;
    protected boolean pauseSent = true;

    /**
     * @param gs The game's graphical settings
     */
    public PlayingCasesState(GraphicalSettings gs) {
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
        nm.whenMessageReceivedDo("PP", (objects -> setSecondPlayerPosition((Point) objects[0])));
        nm.whenMessageReceivedDo("SPP", (objects) -> player.setMapPos((Point) objects[0]));
        nm.whenMessageReceivedDo("Pause", (objects) -> gsm.setState(PauseMenuState.class));
        nm.whenMessageReceivedDo("EndPause", (objects) -> gsm.removeFirstState());
        nm.whenMessageReceivedDo("EMQ", (objects) -> {
            timeShower.extendOnFullWidth(gs.getStringFromId("secondPlayerFinishedQuest"));
            SupervisorLAN.getPeople().getQuest().passQuest();
        });
        nm.setOnDisconnected(() -> {
            DisconnectedMenuState dms = (DisconnectedMenuState) gsm.setState(DisconnectedMenuState.class);
            dms.setSecondPlayerPosition(playerTwo.getMapPos());
        });
        nm.whenMessageReceivedDo("Death", objects -> goToPreviousMenu());
        nm.whenMessageReceivedDo("PL", objects -> lifeBarTwo.setValue((int)(objects[0])));
        nm.whenMessageReceivedDo("EndDual", objects -> goToPreviousMenu());
        nm.whenMessageReceivedDo("Time", objects -> time = (double)objects[0]);
        nm.whenMessageReceivedDo("CP1", objects -> cases.put(playerTwo, (int)objects[0]));
        nm.whenMessageReceivedDo("CP2", objects -> cases.put(player, (int)objects[0]));
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        if (nm.isTheServer()) {
            nm.sendMessageOnUDP("Time", time);
            nm.sendMessageOnUDP("CP1", cases.get(player));
            nm.sendMessageOnUDP("CP2", cases.get(playerTwo));
        }
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

    protected void goToPreviousMenu() {
        if (nm.isTheServer())
            gsm.removeAllStateAndAdd(be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu.class);
        else
            gsm.removeAllStateAndAdd(WaitMenuState.class);
    }

    @Override
    public void getFocus() {
        super.getFocus();
        if (pauseSent) {
            pauseSent = false;
            nm.sendOnTCP("EndPause");
        }
    }
}
