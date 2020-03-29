package be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.DualChooseMenu;
import be.ac.umons.mom.g02.Extensions.DualLAN.GameStates.Menus.WaitMenuState;
import be.ac.umons.mom.g02.Extensions.LAN.GameStates.PlayingState;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Extensions.LAN.Regulator.SupervisorLAN;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Objects.Save;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.Menus.MenuState;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.MenuItem;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.TitleMenuItem;
import be.ac.umons.mom.g02.Managers.ExtensionsManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

/**
 * The state where we wait for a connection / for the acceptation of the connection by the second player.
 */
public class FinalisingConnectionState extends MenuState {

    /**
     * The network manager.
     */
    protected NetworkManager nm;
    /**
     * If we must send the player's characteristics to the second player
     */
    protected boolean sendPlayer;

    /**
     * @param gs The game's graphical settings.
     */
    public FinalisingConnectionState(GraphicalSettings gs) {
        super(gs);
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

    }

    @Override
    public void init() {
        super.init();

        transparentBackground = false;

        setMenuItems(new MenuItem[]{
                new TitleMenuItem(gs, gs.getStringFromId("finalisingConnection"))
        });

        Gdx.app.postRunnable(() -> {
            if (MasterOfMonsGame.getGameToLoad() != null && ExtensionsManager.getInstance().getExtensionsMap().get("LAN").activated) {
                Save save = SupervisorLAN.getSupervisor().oldGameLAN(MasterOfMonsGame.getGameToLoad());
                if (nm.isTheServer()) {
                    nm.sendMessageOnTCP("SAVE", save);
                    LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
                    ls.setAfterLoadingState(be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.WaitMenuState.class);
                }
            }
        });
        if (sendPlayer)
            nm.sendMessageOnTCP("PI", Supervisor.getPeople());
        if (nm.isTheServer()) {
            goToLoading();
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    /**
     * @param sendPlayer If we must send the player's characteristics to the second player
     */
    public void setSendPlayer(boolean sendPlayer) {
        this.sendPlayer = sendPlayer;
        if (sendPlayer)
            nm.sendMessageOnTCP("PI", Supervisor.getPeople());
    }

    /**
     * Go to the loading state
     */
    public static void goToLoading() {
        GameStateManager gsm = GameStateManager.getInstance();
        try {
            NetworkManager nm = NetworkManager.getInstance();
            LoadingState ls = (LoadingState) gsm.removeAllStateAndAdd(LoadingState.class);
            ls.setOnLoaded(() -> nm.sendOnTCP("Loaded"));
            if (ExtensionsManager.getInstance().getExtensionsMap().get("Dual").activated) {
                SupervisorDual.initDual();
                if (nm.isTheServer())
                    ls.setAfterLoadingState(DualChooseMenu.class);
                else
                    ls.setAfterLoadingState(WaitMenuState.class);
            }
            else
                ls.setAfterLoadingState(be.ac.umons.mom.g02.Extensions.LAN.GameStates.Menus.WaitMenuState.class);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set all the necessary actions in the NetworkManager
     */
    public static void setNetworkManagerRunnable() {
        NetworkManager nm = null;
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        nm.whenMessageReceivedDo("PI", objects -> {
            if (MasterOfMonsGame.getGameToLoad() == null)
                FinalisingConnectionState.goToLoading();
            SupervisorMultiPlayer.setPlayerTwo((People) objects[0]);
        });
        nm.whenMessageReceivedDo("SPI", objects -> {
            FinalisingConnectionState.goToLoading();
            SupervisorMultiPlayer.setPlayerOne((People) objects[0]);
        });
        nm.whenMessageReceivedDo("SAVE", (objects -> {
            Save save = (Save) objects[0];
            save.invertPlayerOneAndTwo();
            MasterOfMonsGame.setSaveToLoad(save);
            if (ExtensionsManager.getInstance().getExtensionsMap().get("LAN").activated)
                SupervisorLAN.getSupervisor().oldGameLAN(save);
            FinalisingConnectionState.goToLoading();
        }));
    }
}
