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
     * The socket used for broadcasting / sending message if a server is selected.
     */
    protected DatagramSocket ds;
    /**
     * The thread used for listening the server's informations.
     */
    protected Thread servInfoListeningThread;
    /**
     * The thread used for broadcasting the server's informations.
     */
    protected Thread servInfoBroadcastingThread;
    /**
     * What to do when a server is detected.
     */
    protected Runnable onServerDetected;
    protected Runnable onServerSelected;

    protected ServerInfo selectedServer;

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
        if (servInfoBroadcastingThread != null)
            servInfoBroadcastingThread.interrupt();
        servInfoBroadcastingThread = new Thread(() -> {
            try {
                while (! connected) {
                    for (InetAddress address : addressToBroadcast.keySet())
                        broadcastMessage("MOMServer" + address.toString() + addressToBroadcast.get(address).toString() + "/" + servName, addressToBroadcast.get(address));
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
        servInfoBroadcastingThread.start();
    }

    /**
     * Broadcast the given message on the given address.
     * @param message The message
     * @param address The broadcasting address
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
        if (servInfoListeningThread != null)
            servInfoListeningThread.interrupt();
        servInfoListeningThread = new Thread(this::listenToBroadcast);
        servInfoListeningThread.start();
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
                return;
            }

            String received = new String(dp.getData());
            String[] tab = received.split("/");
            InetAddress serverAddress = null;
            try {
                serverAddress = InetAddress.getByName(tab[1]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            switch (tab[0]) {
                case "MOMServer":
                    Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
                    if (! detected.contains(tab[1])) {
                        detected.add(tab[1]);
                        try {
                            detectedServers.add(new ServerInfo(tab[3], serverAddress,
                                    getMyAddressFromBroadcast(InetAddress.getByName(tab[2]))));
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                            return;
                        }
                        if (onServerDetected != null)
                            Gdx.app.postRunnable(() ->
                                onServerDetected.run());
                    }
                    break;
                case "MOMConnect":
                    try {
                        setSelectedServer(new ServerInfo("", InetAddress.getByName(tab[1]),
                                null));
                        if (onServerSelected != null)
                            onServerSelected.run();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    protected InetAddress getMyAddressFromBroadcast(InetAddress broadcast) {
        for (InetAddress key : addressToBroadcast.keySet()) {
            if (addressToBroadcast.get(key).equals(broadcast)) {
                return key;
            }
        }
        return null;
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

    public void sendMessage(String message) throws IOException {
        if (selectedServer == null)
            throw new NullPointerException("No server was selected");
        byte[] buf = message.getBytes();
        DatagramPacket p = new DatagramPacket(buf, buf.length, selectedServer.getIp(), PORT);
        ds.send(p);
    }

    public void setSelectedServer(ServerInfo selectedServer) {
        if (servInfoListeningThread != null && servInfoListeningThread.isAlive())
            servInfoListeningThread.interrupt();
        if (servInfoBroadcastingThread != null && servInfoBroadcastingThread.isAlive())
            servInfoBroadcastingThread.interrupt();
        this.selectedServer = selectedServer;
        try {
            ds.close();
            ds = new DatagramSocket();
        } catch (SocketException e) {
            Gdx.app.error("NetworkManager", "An error has occured while trying to create the socket", e);
            return;
        }
    }

    public void setOnServerSelected(Runnable onServerSelected) {
        this.onServerSelected = onServerSelected;
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
