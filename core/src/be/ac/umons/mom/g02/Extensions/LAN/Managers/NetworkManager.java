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
    protected Thread connectThread;
    protected Thread acceptThread;

    /**
     * What to do when a server is detected.
     */
    protected Runnable onServerDetected;
    protected Runnable onServerSelected;
    protected Runnable onConnected;
    protected OnPlayerDetectedRunnable onPlayerDetected;
    protected OnPositionDetectedRunnable onPositionDetected;
    protected Runnable onPause;
    protected Runnable onEndPause;
    protected OnPNJDetectedRunnable onPNJDetected;
    protected OnGetPNJRunnable onGetPNJ;
    protected OnHitPNJRunnable onHitPNJ;
    protected OnPNJDeathRunnable onPNJDeath;
    protected String mustSendPNJPos;

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
        toSendOnTCP = new Stack<>();
        toSendOnUDP = new Stack<>();
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

    public void stopBroadcastingServerInfo() {
        servInfoBroadcastingThread.interrupt();
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
        sendOnUDPThread = new Thread(this::sendOnUDPThread);
        receiveOnTCPThread = new Thread(this::listenOnTCP);
        listenOnUDPThread = new Thread(this::listenToUDP);
        sendOnTCPThread.start();
        receiveOnTCPThread.start();
        sendOnUDPThread.start();
        listenOnUDPThread.start();
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

    public void sendOnTCP(String... messages) {
        toSendOnTCP.addAll(Arrays.asList(messages));
    }

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

    public void sendOnUDP(String message) {
        if (toSendOnUDP.size() > 10)
            toSendOnUDP.pop();
        toSendOnUDP.push(message);
    }

    protected void sendOnUDPThread() {
        while (true) {
            if (toSendOnUDP.size() > 0) {
                sendUDPMessage(toSendOnUDP.pop());
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
        sendOnTCP(String.format("PI#%s#%d#%d#%d", player.toString(), player.getType().ordinal(), player.getGender().ordinal(), player.getDifficulty().ordinal()));
    }
    public void sendPNJInformation(Character mob) throws IOException {
        sendOnTCP(String.format("PNJ#%s#%d#%d#%s", mob.getCharacteristics().getName(), mob.getPosX(), mob.getPosY(), objectToString(mob.getCharacteristics())));
    }
    public void sendPlayerPosition(Player player) {
        sendOnUDP(String.format("PP#%d#%d", player.getPosX(), player.getPosY()));
    }

    public void sendHit(Character c) {
        sendOnUDP(String.format("hitPNJ#%s#%f", c.getCharacteristics().getName(), c.getCharacteristics().getActualLife()));
    }

    public void sendPause() {
        sendOnTCP("Pause");
    }

    public void sendEndPause() {
        sendOnTCP("EndPause");

    }

    public void sendPNJDeath(String name) {
        sendOnTCP(String.format("PNJDeath#%s", name));
    }

    public void askPNJsPositions(String map) {
        sendOnTCP("getPNJsPos#" + map);
    }

    protected void processMessage(String received) {
        if (received == null)
            return;
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
     * https://stackoverflow.com/a/134918 by OscarRyz*/
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
     * https://stackoverflow.com/a/134918 by OscarRyz */
    protected static String objectToString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public void setSelectedServer(ServerInfo selectedServer) {
        if (servInfoBroadcastingThread != null && servInfoBroadcastingThread.isAlive())
            servInfoBroadcastingThread.interrupt();
        this.selectedServer = selectedServer;
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

    public void setOnPNJDetected(OnPNJDetectedRunnable onPNJDetected) {
        this.onPNJDetected = onPNJDetected;
    }

    public void setOnPause(Runnable onPause) {
        this.onPause = onPause;
    }

    public void setOnGetPNJ(OnGetPNJRunnable onGetPNJ) {
        this.onGetPNJ = onGetPNJ;
    }

    public boolean isTheServer() {
        return isTheServer;
    }

    public String getMustSendPNJPos() {
        return mustSendPNJPos;
    }

    public void setOnHitPNJ(OnHitPNJRunnable onHitPNJ) {
        this.onHitPNJ = onHitPNJ;
    }

    public void setOnPNJDeath(OnPNJDeathRunnable onPNJDeath) {
        this.onPNJDeath = onPNJDeath;
    }

    public void setOnEndPause(Runnable onEndPause) {
        this.onEndPause = onEndPause;
    }

    public interface OnPlayerDetectedRunnable {
        void run(People secondPlayer);
    }
    public interface OnPNJDetectedRunnable {
        void run(String name, be.ac.umons.mom.g02.Objects.Characters.Character mob, int x, int y);
    }

    public interface OnPositionDetectedRunnable {
        void run(Point pos);
    }

    public interface OnGetPNJRunnable {
        void run(String map);
    }

    public interface OnHitPNJRunnable {
        void run(String name, double life);
    }

    public interface OnPNJDeathRunnable {
        void run(String name);
    }

    public void dispose() {
        ps.close();
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
