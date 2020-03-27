package be.ac.umons.mom.g02.Extensions.DualLAN.Managers;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.SocketException;

public class NetworkManager extends be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager {

    /**
     * @return The instance of NetworkManager
     * @throws SocketException If the port used (32516) is already used
     */
    public static NetworkManager getInstance() throws SocketException {
        if (instance == null)
            instance = new NetworkManager();
        return (NetworkManager) instance;
    }

    protected OnTypeChosenRunnable onTypeChosen;

    /**
     * @throws SocketException If the port used (32516) is already used
     */
    protected NetworkManager() throws SocketException {
        super();
    }

    public void sendTypeChosen(TypeDual type) {
        try {
            sendOnTCP("TC#" + objectToString(type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processMessage(String received) {
        super.processMessage(received);
        String[] tab = received.split("#");
        switch (tab[0]) {
            case "TC":
                try {
                    TypeDual td = (TypeDual) objectFromString(tab[1]);
                    if (onTypeChosen != null)
                        Gdx.app.postRunnable(() -> onTypeChosen.run(td));
                } catch (IOException | ClassNotFoundException e) {
                    Gdx.app.error("NetworkManager(DualLAN)", "Error detected while parsing type of dual message (ignoring message)", e);
                }
                break;
        }
    }

    public void setOnTypeChosen(OnTypeChosenRunnable onTypeChosen) {
        this.onTypeChosen = onTypeChosen;
    }

    public interface OnTypeChosenRunnable {
        void run(TypeDual type);
    }

}
