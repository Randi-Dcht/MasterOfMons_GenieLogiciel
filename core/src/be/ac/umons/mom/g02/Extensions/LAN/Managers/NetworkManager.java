package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.People;
import com.badlogic.gdx.Gdx;

import java.awt.*;
import java.io.*;
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
     * If the current manager is considered as a server (if it was waiting for a connection).
     */
    protected boolean isTheServer = false;
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
     * The socket used to send/receive important messages when the connection is established.
     */
    protected Socket socket;
    /**
     * The socket used to wait for a connection.
     */
    protected ServerSocket serverSocket;
    /**
     * The PrintStream associated with the socket <code>socket</code>
     */
    protected PrintStream ps;
    /**
     * The BufferedReader associated with the socket <code>socket</code>
     */
    protected BufferedReader br;
    /**
     * The thread used for broadcasting the server's informations.
     */
    protected Thread servInfoBroadcastingThread;
    /**
     * The thread used for listening the server's informations.
     */
    protected Thread listenOnUDPThread;
    /**
     * The thread used for listening on the TCP socket.
     */
    protected Thread receiveOnTCPThread;
    /**
     * The thread used for sending messages on the TCP socket.
     */
    protected Thread sendOnTCPThread;
    /**
     * The thread used for sending messages on the UDP socket.
     */
    protected Thread sendOnUDPThread;
    /**
     * The messages to send on the TCP socket.
     */
    protected Stack<String> toSendOnTCP;
    /**
     * The messages to send on the UDP socket.
     */
    protected Stack<String> toSendOnUDP;
    /**
     * The thread trying to connect to the server.
     */
    protected Thread connectThread;
    /**
     * The thread waiting for a connection from the second player.
     */
    protected Thread acceptThread;

    /**
     * What to do when a server is detected.
     */
    protected Runnable onServerDetected;
    /**
     * What to do when the server is selected.
     */
    protected Runnable onServerSelected;
    /**
     * What to do when the second player is connected.
     */
    protected Runnable onConnected;
    /**
     * What to do when the second player informations has been received.
     */
    protected OnPlayerDetectedRunnable onPlayerDetected;
    /**
     * What to do when the second player position has been received.
     */
    protected OnPositionDetectedRunnable onPositionDetected;
    /**
     * What to do when the second player is in pause.
     */
    protected Runnable onPause;
    /**
     * What to do when the second player isn't in pause anymore.
     */
    protected Runnable onEndPause;
    /**
     * What to do when the informations of a PNJ has been received.
     */
    protected OnPNJDetectedRunnable onPNJDetected;
    /**
     * What to do when the second player ask informations about the PNJs on the map.
     */
    protected OnGetPNJRunnable onGetPNJ;
    /**
     * What to do when the second player hit a PNJ.
     */
    protected OnHitPNJRunnable onHitPNJ;
    /**
     * What to do when the second player killed a PNJ.
     */
    protected OnPNJDeathRunnable onPNJDeath;
    /**
     * What to do when the second player finished the current <code>MasterQuest</code>
     */
    protected Runnable onMasterQuestFinished;
    /**
     * What to do when the second player is disconnected
     */
    protected Runnable onDisconnected;
    /**
     * On which map the PNJ's informations has been asked.
     */
    protected String mustSendPNJPos;
    /**
     * The server on which we must connect.
     */
    protected ServerInfo selectedServer;
    /**
     * The list of all servers that was detected.
     */
    List<String> detected;

    protected boolean ignoreEMQ = false;

    protected double msSinceLastMessage;

    /**
     * @throws SocketException If the port used (32516) is already used
     */
    protected NetworkManager() throws SocketException {
        ds = new DatagramSocket(PORT);
        detectedServers = new LinkedList<>();
        detected = new LinkedList<>();
        try {
            addressToBroadcast = listAllBroadcastAddresses();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }
        toSendOnTCP = new Stack<>();
        toSendOnUDP = new Stack<>();
        msSinceLastMessage = 1;
    }

    /**
     * Update the time gone by the last message.
     * @param dt The time gone by since the last call of this function.
     */
    public void update(double dt) {
        msSinceLastMessage += dt;
        if (msSinceLastMessage > 2)
            sendOnTCP("TC"); // Test Connection
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
                        broadcastMessage("MOMServer" + address.toString().replace("/", "#")
                                + addressToBroadcast.get(address).toString().replace("/", "#") + "#" + servName, addressToBroadcast.get(address));
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
     * Stop broadcasting the server's information on the network.
     */
    public void stopBroadcastingServerInfo() {
        servInfoBroadcastingThread.interrupt();
        servInfoBroadcastingThread = null;
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

    /**
     * Try to connect on the selected server.
     */
    public void tryToConnect() {
        if (connectThread != null)
            connectThread.interrupt();
        connectThread = new Thread(() -> {
            try {
                if (socket != null)
                    socket.close();
                socket = new Socket(selectedServer.getIp(), PORT);
                initConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        connectThread.start();
    }

    /**
     * Wait a connection and select the connection as the selected server.
     */
    public void acceptConnection() {
        if (acceptThread != null)
            acceptThread.interrupt();
        acceptThread = new Thread(() -> {
            try {
                if (serverSocket == null)
                    serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                isTheServer = true;
                setSelectedServer(new ServerInfo("", socket.getInetAddress(), null));
                initConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        acceptThread.start();
    }

    /**
     * Initiate the PrintStream, the BufferedReader and the necessary threads when a connection was established.
     */
    public void initConnection() {
        try {
            ps = new PrintStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (listenOnUDPThread != null)
            listenOnUDPThread.interrupt();
        if (sendOnUDPThread != null)
            sendOnUDPThread.interrupt();
        if (receiveOnTCPThread != null)
            receiveOnTCPThread.interrupt();
        if (sendOnTCPThread != null)
            sendOnTCPThread.interrupt();

        sendOnTCPThread = new Thread(() -> {
            try {
                sendOnTCPThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        sendOnUDPThread = new Thread(() -> {
            try {
                sendOnUDPThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        receiveOnTCPThread = new Thread(this::listenOnTCP);
        listenOnUDPThread = new Thread(this::listenToUDP);
        sendOnTCPThread.start();
        receiveOnTCPThread.start();
        sendOnUDPThread.start();
        listenOnUDPThread.start();
        Gdx.app.postRunnable(onConnected);
        connected = true;
    }

    /**
     * Listen all messages from the TCP socket.
     */
    protected void listenOnTCP() {
        String message;
        while (true) {
            try {
                processMessage(message = br.readLine());
                if (message == null)
                    break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * Add (a) message(s) to send on the TCP socket.
     * @param messages The message(s)
     */
    public void sendOnTCP(String... messages) {
        toSendOnTCP.addAll(Arrays.asList(messages));
    }

    /**
     * Check non-stop if there is a message to send on the TCP socket and send it.
     * @throws InterruptedException If the thread was interrupted.
     */
    protected void sendOnTCPThread() throws InterruptedException {
        while (true) {
            if (! toSendOnTCP.isEmpty()) {
                ps.println(toSendOnTCP.pop());
            } else {
                ps.flush();
                Thread.sleep(20);
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
    /**
     * Add a message to send on the UDP socket. Remove the older message if there is more than 10 messages waiting.
     * @param message The message
     */
    public void sendOnUDP(String message) {
        if (toSendOnUDP.size() > 10)
            toSendOnUDP.pop();
        toSendOnUDP.push(message);
    }

    /**
     * Check non-stop if there is a message to send on the UDP socket and send it.
     * @throws InterruptedException If the thread was interrupted.
     */
    protected void sendOnUDPThread() throws InterruptedException {
        while (true) {
            if (toSendOnUDP.size() > 0) {
                sendUDPMessage(toSendOnUDP.pop());
            } else {
                Thread.sleep(100);
            }
        }
    }

    /**
     * Send a message on the UDP socket.
     * @param message The message
     */
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

    /**
     * Send the player information on the TCP socket.
     * @param player The player to send
     */
    public void sendPlayerInformation(People player) {
        sendOnTCP(String.format("PI#%s#%d#%d#%d", player.toString(), player.getType().ordinal(), player.getGender().ordinal(), player.getDifficulty().ordinal()));
    }

    /**
     * Send the mob information on the TCP socket.
     * @param mob The PNJ to send
     * @throws IOException If the PNJ couldn't be serialized.
     */
    public void sendPNJInformation(Character mob) throws IOException {
        sendOnTCP(String.format("PNJ#%s#%d#%d#%s", mob.getCharacteristics().getName(), mob.getPosX(), mob.getPosY(), objectToString(mob.getCharacteristics())));
    }

    /**
     * Send the player positions.
     * @param player The player.
     */
    public void sendPlayerPosition(Player player) {
        sendOnUDP(String.format("PP#%d#%d", player.getPosX(), player.getPosY()));
    }

    /**
     * Send that a hit has been made on the character <code>c</code> and its life.
     * @param c The character hit.
     */
    public void sendHit(Character c) {
        sendOnUDP(String.format("hitPNJ#%s#%f", c.getCharacteristics().getName(), c.getCharacteristics().getActualLife()));
    }

    /**
     * Send the pause signal.
     */
    public void sendPause() {
        sendOnTCP("Pause");
    }

    /**
     * Send the signal ending the pause.
     */
    public void sendEndPause() {
        sendOnTCP("EndPause");
    }

    /**
     * Send the death of a PNJ.
     * @param name The name of the PNJ
     */
    public void sendPNJDeath(String name) {
        sendOnTCP(String.format("PNJDeath#%s", name));
    }

    /**
     * Asks for all PNJs informations and positions.
     * @param map The map on which the PNJ must be.
     */
    public void askPNJsPositions(String map) {
        sendOnTCP("getPNJsPos#" + map);
    }

    public void sendEndOfMasterQuest() {
        sendOnTCP("EMQ");
        ignoreEMQ = true;
    }

    /**
     * Process the received message and execute the necessary actions.
     * @param received The received message
     */
    protected void processMessage(String received) {
        if (received == null) {
            Gdx.app.error("NetworkManager", "Disconnected from distant server !");
            onDisconnected();
            return;
        }
        String[] tab = received.split("#");
        switch (tab[0]) {
            case "MOMServer": // Broadcast of a server
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
                                        Integer.parseInt(y.trim()))));
                } catch (NumberFormatException e) {
                    Gdx.app.error("NetworkManager", "Error detected while parsing position (ignoring message)", e);
                }
                break;
            case "Pause":
                if (onPause != null)
                    Gdx.app.postRunnable(onPause);
                break;
            case "EndPause":
                if (onEndPause != null)
                    Gdx.app.postRunnable(onEndPause);
                break;
            case "PNJ": // Add a PNJ to the map
                String pnjName = tab[1];
                int pnjX = Integer.parseInt(tab[2]);
                int pnjY = Integer.parseInt(tab[3]);
                try {
                    be.ac.umons.mom.g02.Objects.Characters.Character mob = (be.ac.umons.mom.g02.Objects.Characters.Character) objectFromString(tab[4]);
                    if (onPNJDetected != null)
                        Gdx.app.postRunnable(() -> onPNJDetected.run(pnjName, mob, pnjX, pnjY));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "getPNJsPos": // Get all PNJs and their positions
                if (onGetPNJ != null)
                    Gdx.app.postRunnable(() -> onGetPNJ.run(tab[1].trim()));
                else
                    mustSendPNJPos = tab[1].trim();
                break;
            case "hitPNJ": // A PNJ has been hit by the other player.
                if (onHitPNJ != null)
                    Gdx.app.postRunnable(() -> onHitPNJ.run(tab[1],
                            Double.parseDouble(tab[2])));
                break;
            case "PNJDeath":
                if (onPNJDeath != null)
                    Gdx.app.postRunnable(() -> onPNJDeath.run(tab[1].trim()));
                break;
            case "EMQ":
                if (ignoreEMQ)
                    ignoreEMQ = false;
                else
                    Gdx.app.postRunnable(onMasterQuestFinished);
                break;

        }
        msSinceLastMessage = 0;
    }

    protected void onDisconnected() {
        receiveOnTCPThread.interrupt();
        sendOnTCPThread.interrupt();
        Gdx.app.postRunnable(onDisconnected);
    }

    /**
     * Select a server
     * @param si The server's informations.
     */
    public void selectAServer(ServerInfo si) {
        setSelectedServer(si);
        if (onServerSelected != null)
            Gdx.app.postRunnable(onServerSelected);
        detected = null; // Dispose now useless informations
    }

    /**
     * Add a server to the detected one
     * @param tab The received message parts.
     * @param serverAddress The server address.
     */
    protected void addADetectedServer(String[] tab, InetAddress serverAddress) {
//        Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
        if (detected != null && ! detected.contains(tab[1])) {
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

    /**
     * @param broadcast The broadcast address.
     * @return The address of this machine on the network associated with the address <code>broadcast</code>
     */
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



    /** Read the object from Base64 string.
     * From https://stackoverflow.com/a/134918 by OscarRyz*/
    protected static Object objectFromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string.
     * From https://stackoverflow.com/a/134918 by OscarRyz */
    protected static String objectToString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Close all the connections established.
     */
    public void close() {
        ps.close();
        try {
            br.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the selected server.
     * @param selectedServer The selected server
     */
    public void setSelectedServer(ServerInfo selectedServer) {
        if (servInfoBroadcastingThread != null && servInfoBroadcastingThread.isAlive())
            servInfoBroadcastingThread.interrupt();
        this.selectedServer = selectedServer;
    }

    /**
     * @param onServerSelected What to do when a server is detected.
     */
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

    /**
     * @param onConnected What to do when the second player is connected.
     */
    public void setOnConnected(Runnable onConnected) {
        this.onConnected = onConnected;
    }

    /**
     * @param onPlayerDetected What to do when the second player informations has been received.
     */
    public void setOnPlayerDetected(OnPlayerDetectedRunnable onPlayerDetected) {
        this.onPlayerDetected = onPlayerDetected;
    }

    /**
     * @param onPositionDetected What to do when the second player position has been received.
     */
    public void setOnPositionDetected(OnPositionDetectedRunnable onPositionDetected) {
        this.onPositionDetected = onPositionDetected;
    }

    /**
     * @param onPNJDetected What to do when the informations of a PNJ has been received.
     */
    public void setOnPNJDetected(OnPNJDetectedRunnable onPNJDetected) {
        this.onPNJDetected = onPNJDetected;
    }

    /**
     * @param onPause What to do when the second player is in pause.
     */
    public void setOnPause(Runnable onPause) {
        this.onPause = onPause;
    }

    /**
     * @param onGetPNJ What to do when the second player ask informations about the PNJs on the map.
     */
    public void setOnGetPNJ(OnGetPNJRunnable onGetPNJ) {
        this.onGetPNJ = onGetPNJ;
    }

    /**
     * @return If the current manager is considered as a server (if it was waiting for a connection).
     */
    public boolean isTheServer() {
        return isTheServer;
    }

    /**
     * @return On which map the PNJ's informations has been asked.
     */
    public String getMustSendPNJPos() {
        return mustSendPNJPos;
    }

    /**
     * @param onHitPNJ What to do when the second player hit a PNJ.
     */
    public void setOnHitPNJ(OnHitPNJRunnable onHitPNJ) {
        this.onHitPNJ = onHitPNJ;
    }

    /**
     * @param onPNJDeath What to do when the second player killed a PNJ.
     */
    public void setOnPNJDeath(OnPNJDeathRunnable onPNJDeath) {
        this.onPNJDeath = onPNJDeath;
    }

    /**
     * @param onEndPause What to do when the second player isn't in pause anymore.
     */
    public void setOnEndPause(Runnable onEndPause) {
        this.onEndPause = onEndPause;
    }

    /**
     * @param onMasterQuestFinished What to do when the second player finished the current <code>MasterQuest</code>
     */
    public void setOnMasterQuestFinished(Runnable onMasterQuestFinished) {
        this.onMasterQuestFinished = onMasterQuestFinished;
    }

    /**
     * @param onDisconnected What to do when the second player is disconnected
     */
    public void setOnDisconnected(Runnable onDisconnected) {
        this.onDisconnected = onDisconnected;
    }

    /**
     * Represent the runnable executed when the second player informations has been received.
     */
    public interface OnPlayerDetectedRunnable {
        void run(People secondPlayer);
    }

    /**
     * Represent the runnable executed when the informations of a PNJ has been received.
     */
    public interface OnPNJDetectedRunnable {
        void run(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y);
    }

    /**
     * Represent the runnable executed when the second player position has been received.
     */
    public interface OnPositionDetectedRunnable {
        void run(Point pos);
    }

    /**
     * Represent the runnable executed when the second player ask informations about the PNJs on the map.
     */
    public interface OnGetPNJRunnable {
        void run(String map);
    }

    /**
     * Represent the runnable executed when the second player hit a PNJ.
     */
    public interface OnHitPNJRunnable {
        void run(String name, double life);
    }

    /**
     * Represent the runnable executed when the second player killed a PNJ.
     */
    public interface OnPNJDeathRunnable {
        void run(String name);
    }
}
