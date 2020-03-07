package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Manage all the networking for the extension LAN.
 * A part of this code is from https://www.baeldung.com/java-broadcast-multicast
 */
public class NetworkManager {

    /**
     * The instance of NetworkManager
     */
    protected static NetworkManager instance;

    /**
     * @return The instance of NetworkManager
     * @throws SocketException If the port used (32516) is already used
     */
    public static NetworkManager getInstance() throws SocketException {
        if (instance == null)
            instance = new NetworkManager();
        return instance;
    }


    /**
     * The port used for communication
     */
    protected final int PORT = 32516;
    /**
     * If a connection has been established or not.
     */
    protected boolean connected = false;
    /**
     * The list of all the server that has been detected.
     */
    protected List<ServerInfo> detectedServers;
    /**
     * The different broadcast addresses on which we should broadcast.
     */
    HashMap<InetAddress, InetAddress> addressToBroadcast;
    /**
     * The socket used for broadcasting
     */
    protected DatagramSocket ds;
    /**
     * The thread used for listening/broadcasting the server's informations.
     */
    protected Thread servInfoThread;
    /**
     * What to do when a server is detected.
     */
    protected Runnable onServerDetected;

    /**
     * @throws SocketException If the port used (32516) is already used
     */
    protected NetworkManager() throws SocketException {
        ds = new DatagramSocket(PORT);
        detectedServers = new LinkedList<>();
        try {
            addressToBroadcast = listAllBroadcastAddresses();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Start broadcasting the server's informations with the given server name.
     * @param servName The server name
     */
    public void startBroadcastingMessage(String servName) {
        if (servInfoThread != null)
            servInfoThread.interrupt();
        servInfoThread = new Thread(() -> {
            try {
                while (! connected) {
                    for (InetAddress address : addressToBroadcast.keySet())
                        broadcastMessage("MOMServer" + address.toString() + "/" + servName, addressToBroadcast.get(address));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        servInfoThread.start();
    }

    /**
     * Broadcast the given message on the given address.
     * @param message The message
     * @param address The address
     * @throws IOException If an error occur during the broadcasting
     */
    protected void broadcastMessage(String message, InetAddress address) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
        ds.send(packet);
    }

    /**
     * Start listening for the server informations.
     */
    public void startListeningForServer() {
        if (servInfoThread != null)
            servInfoThread.interrupt();
        servInfoThread = new Thread(this::listenToBroadcast);
        servInfoThread.start();
    }

    /**
     * Listen to every broadcast and check if one is a server or not.
     */
    protected void listenToBroadcast() {
        List<String> detected = new ArrayList<>();
        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            try {
                ds.receive(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String received = new String(dp.getData());
            if (received.startsWith("MOMServer")) {
                String[] tab = received.split("/");
                Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
                try {
                    if (! detected.contains(tab[1])) {
                        detected.add(tab[1]);
                        InetAddress serverAddress = InetAddress.getByName(tab[1]);
                        detectedServers.add(new ServerInfo(tab[2], serverAddress, PORT));
                        Gdx.app.postRunnable(onServerDetected);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * https://www.baeldung.com/java-broadcast-multicast
     * @return A list of all the broadcast's addresses on which we should broadcast the server's informations.
     * @throws SocketException If an error occured
     */
    protected HashMap<InetAddress, InetAddress> listAllBroadcastAddresses() throws SocketException {
        HashMap<InetAddress, InetAddress> broadcastList = new HashMap<>();
        Enumeration<NetworkInterface> interfaces
                = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            Iterator<InterfaceAddress> it = networkInterface.getInterfaceAddresses().stream().iterator();
            while (it.hasNext()) {
                InterfaceAddress ia = it.next();
                InetAddress broadcast = ia.getBroadcast();
                if (broadcast != null)
                    broadcastList.put(ia.getAddress(), ia.getBroadcast());
            }
        }
        return broadcastList;
    }

    /**
     * @return The list of detected servers.
     */
    public List<ServerInfo> getDetectedServers() {
        return detectedServers;
    }

    /**
     * @param onServerDetected What to do when a server is detected
     */
    public void setOnServerDetected(Runnable onServerDetected) {
        this.onServerDetected = onServerDetected;
    }

    /**
     * @return The map linking the ip address of the machine to the broadcast address.
     */
    public HashMap<InetAddress, InetAddress> getAddressToBroadcast() {
        return addressToBroadcast;
    }
}
