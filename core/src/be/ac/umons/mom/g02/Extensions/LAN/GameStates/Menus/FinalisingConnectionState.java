package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.Save;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;

/**
 * The state where we wait for a connection / for the acceptation of the connection by the second player.
 */
public class FinalisingConnectionState extends MenuState {

    /**
     * The network manager.
     */
    protected NetworkManager nm;

    protected boolean sendPlayer;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public FinalisingConnectionState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        nm.setOnSecondPlayerDetected(secondPlayer -> {
            if (MasterOfMonsGame.getGameToLoad() == null) {
                LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
                ls.setAfterLoadingState(PlayingState.class);
            }
            SupervisorLAN.setPlayerTwo(secondPlayer);
        });
        nm.setOnPlayerDetected(player -> {
            LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
            ls.setAfterLoadingState(PlayingState.class);
            SupervisorLAN.setPlayerOne(player);
        });
        nm.setOnSaveDetected((save -> {
            SupervisorLAN.getSupervisor().oldGameLAN(save);
            LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
            ls.setAfterLoadingState(PlayingState.class);
        }));

    }

    @Override
    public void init() {
        super.init();

        transparentBackground = false;

        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("finalisingConnection"))
        });

        Gdx.app.postRunnable(() -> {
            if (MasterOfMonsGame.getGameToLoad() != null) {
                Save save = SupervisorLAN.getSupervisor().oldGameLAN(MasterOfMonsGame.getGameToLoad());
                if (nm.isTheServer()) {
                    try {
                        nm.sendSave(save);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Gdx.app.error("FinalisingConnectionState", "The save object hasn't been sent !", e);
                    }
                    LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
                    ls.setAfterLoadingState(PlayingState.class);
                }
            }
        });
        if (sendPlayer)
            nm.sendPlayerInformation(SupervisorLAN.getPeople());
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    public void setSendPlayer(boolean sendPlayer) {
        this.sendPlayer = sendPlayer;
        if (sendPlayer)
            nm.sendPlayerInformation(SupervisorLAN.getPeople());
    }
}
