package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.People;
import com.badlogic.gdx.Gdx;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.List;

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

    protected Socket socket;
    protected ServerSocket serverSocket;

    protected PrintStream ps;
    protected BufferedReader br;
    /**
     * The thread used for broadcasting the server's informations.
     */
    protected Thread servInfoBroadcastingThread;

    /**
     * The thread used for listening the server's informations.
     */
    protected Thread listenOnUDPThread;
    protected Thread receiveOnTCPThread;
    protected Thread sendOnTCPThread;
    protected Thread sendOnUDPThread;
    protected Stack<String> toSendOnTCP;
    protected Stack<String> toSendOnUDP;

    /**
     * What to do when a server is detected.
     */
    protected Runnable onServerDetected;
    protected Runnable onServerSelected;
    protected Runnable onConnected;
    protected OnPlayerDetectedRunnable onPlayerDetected;
    protected OnPositionDetectedRunnable onPositionDetected;
    protected Runnable onPause;

    protected ServerInfo selectedServer;
    List<String> detected = new ArrayList<>();

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
                        Thread.sleep(100);
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
        if (listenOnUDPThread != null)
            listenOnUDPThread.interrupt();
        listenOnUDPThread = new Thread(this::listenToUDP);
        listenOnUDPThread.start();
    }

    public void tryToConnect() {
        new Thread(() -> {
            try {
                socket = new Socket(selectedServer.getIp(), PORT);
                initConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void acceptConnection() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                initConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void initConnection() {
        try {
            ps = new PrintStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        toSendOnTCP = new Stack<>();
        toSendOnUDP = new Stack<>();
        sendOnTCPThread = new Thread(() -> {
            try {
                sendOnTCPThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sendOnUDPThread = new Thread(this::sendOnUDPThread);
        receiveOnTCPThread = new Thread(this::listenOnTCP);
        sendOnTCPThread.start();
        receiveOnTCPThread.start();
        Gdx.app.postRunnable(onConnected);
    }

    protected void listenOnTCP() {
        while (true) {
            try {
                processMessage(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void sendOnTCP(String message) {
        toSendOnTCP.add(message);
    }

    protected void sendOnTCPThread() throws InterruptedException {
        while (true) {
            if (! toSendOnTCP.isEmpty()) {
                ps.println(toSendOnTCP.pop());
            } else {
                ps.flush();
                Thread.sleep(100);
            }
        }
    }

    /**
     * Listen to every broadcast and check if one is a server or not.
     */
    protected void listenToUDP() {
        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            try {
                ds.receive(dp);
                processMessage(new String(dp.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendOnUDP(String message) {
        if (toSendOnUDP.size() > 10)
            toSendOnUDP.pop();
        toSendOnUDP.push(message);
    }

    protected void sendOnUDPThread() {
        while (true) {
            if (toSendOnUDP.size() > 0) {
                sendUDPMessage(toSendOnTCP.pop());
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void sendUDPMessage(String message) {
        if (selectedServer == null)
            throw new NullPointerException("No server was selected");
        byte[] buf = message.getBytes();
        DatagramPacket p = new DatagramPacket(buf, buf.length, selectedServer.getIp(), PORT);
        try {
            ds.send(p);
        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "A message wasn't sent : " + message, e);
        }
    }

    public void sendPlayerInformation(People player) {
        sendOnTCP(String.format("PI/%s/%d/%d/%d", player.toString(), player.getType().ordinal(), player.getGender().ordinal(), player.getDifficulty().ordinal()));
    }
    public void sendPlayerPosition(Player player) {
        sendOnUDP(String.format("PP/%d/%d", player.getPosX(), player.getPosY()));
    }

    protected void processMessage(String received) {
            String[] tab = received.split("/");
            switch (tab[0]) {
                case "MOMServer":
                    InetAddress serverAddress = null;
                    try {
                        serverAddress = InetAddress.getByName(tab[1]);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    addADetectedServer(tab, serverAddress);
                    break;
                case "PI": // Player info
                    String name = tab[1];
                    Type type = Type.values()[Integer.parseInt(tab[2])];
                    Gender gender = Gender.values()[Integer.parseInt(tab[3])];
                    Difficulty difficulty = Difficulty.values()[Integer.parseInt(tab[4])];
                    if (onPlayerDetected != null)
                        Gdx.app.postRunnable(() ->
                            onPlayerDetected.run(new People(name, type, gender, difficulty)));
                    break;
                case "PP": // Player Position
                    String x = tab[1];
                    String y = tab[2];
                    try {
                        if (onPositionDetected != null)
                            Gdx.app.postRunnable(() ->
                                    onPositionDetected.run(new Point(
                                            Integer.parseInt(x),
                                            Integer.parseInt(y))));
                    } catch (NumberFormatException e) {
                        Gdx.app.error("NetworkManager", "Error detected while parsing position (ignoring message)", e);
                    }
                case "PAUSE":
                    if (onPause != null)
                        Gdx.app.postRunnable(onPause);
            }
    }

    public void selectAServer(String s) {
        try {
            setSelectedServer(new ServerInfo("", InetAddress.getByName(s),
                    null));
            if (onServerSelected != null)
                Gdx.app.postRunnable(onServerSelected);
            detected = null; // Dispose now useless informations
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    public void selectAServer(ServerInfo si) {
        setSelectedServer(si);
        if (onServerSelected != null)
            Gdx.app.postRunnable(onServerSelected);
        detected = null; // Dispose now useless informations
    }

    protected void addADetectedServer(String[] tab, InetAddress serverAddress) {
//        Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
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
                Gdx.app.postRunnable(onServerDetected);
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

    public void setSelectedServer(ServerInfo selectedServer) {
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

    public void setOnConnected(Runnable onConnected) {
        this.onConnected = onConnected;
    }

    public void setOnPlayerDetected(OnPlayerDetectedRunnable onPlayerDetected) {
        this.onPlayerDetected = onPlayerDetected;
    }

    public void setOnPositionDetected(OnPositionDetectedRunnable onPositionDetected) {
        this.onPositionDetected = onPositionDetected;
    }

    public void setOnPause(Runnable onPause) {
        this.onPause = onPause;
    }

    public interface OnPlayerDetectedRunnable {
        void run(People secondPlayer);
    }

    public interface OnPositionDetectedRunnable {
        void run(Point pos);
    }
}
