package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.*;
import java.util.List;
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
    protected HashMap<InetAddress, InetAddress> addressToBroadcast;
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
     * What to do when the five magics numbers are received.
     */
    protected OnMagicNumberReceivedRunnable onMagicNumberReceived;
    /**
     * What to do when the magic number has been sent
     */
    protected IntRunnable onMagicNumberSent;
    /**
     * What to do if the user chose the wrong magic number.
     */
    protected Runnable onWrongMagicNumber;
    /**
     * What to do when the second player is connected.
     */
    protected Runnable onConnected;
    /**
     * What to do when the second player is disconnected
     */
    protected Runnable onDisconnected;
    /**
     * The server on which we must connect.
     */
    protected ServerInfo selectedServer;
    /**
     * The list of all servers that was detected.
     */
    List<String> detected;
    /**
     * The time gone by since the last message received from the second player
     */
    protected double msSinceLastMessage;

    protected HashMap<Integer,ArrayList<Course>> planningReceived; // TODO
    /**
     * The map making the link between a message and the action to execute
     */
    protected HashMap<String, ObjectsRunnable> runnableMap;
    /**
     * The map making the link between a message (to send) and the action to execute
     */
    protected HashMap<String, Runnable> runnableSendMap;

    protected Stack<String> messagesNotRan;

    /**
     * @throws SocketException If the port used (32516) is already used
     */
    protected NetworkManager() throws SocketException {
        runnableMap = new HashMap<>();
        runnableSendMap = new HashMap<>();
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
        messagesNotRan = new Stack<>();
        whenMessageReceivedDo("TC", (objects) -> Gdx.app.log("NetworkManager", "Test connection received : Possible unstable connection !"));
        msSinceLastMessage = 1;
        whenMessageReceivedDo("MOMServer", (objects) -> {
            InetAddress serverAddress = null;
            try {
                serverAddress = InetAddress.getByName((String) objects[0]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            addADetectedServer(objects, serverAddress);
        });
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
     * Start broadcasting the server's informations with
     * the given server name.
     * @param servName The server name
     */
    public void startBroadcastingMessage(String servName) {
        if (servInfoBroadcastingThread != null)
            servInfoBroadcastingThread.interrupt();
        servInfoBroadcastingThread = new Thread(() -> {
            try {
                while (! connected) {
                    for (InetAddress address : addressToBroadcast.keySet())
                        broadcastMessage("MOMServer#" + objectToString( new Object[] {
                                address.toString().replace("/", ""),
                                addressToBroadcast.get(address).toString().replace("/", ""),
                                servName
                        }), addressToBroadcast.get(address));
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
                waitMagicNumber();
            } catch (NoRouteToHostException e) {
                MasterOfMonsGame.showAnError(GraphicalSettings.getStringFromId("ipUnreachable"));
                Gdx.app.log("NetworkManager", "Unreachable IP !", e);
            }
            catch (IOException e) {
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
                if (serverSocket != null)
                    serverSocket.close();
                serverSocket = new ServerSocket(PORT);
                socket = serverSocket.accept();
                sendMagicNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        acceptThread.start();
    }

    /**
     * Generate and send the 5 numbers (including the magic one) and wait for the answer of the second player.
     */
    protected void sendMagicNumber() {
        int magicNumber = new Random().nextInt(256);
        try {
            Random rand = new Random();
            int pos = rand.nextInt(5);
            int [] magicNumbers = new int[5];
            magicNumbers[pos] = magicNumber;
            for (int i = 0; i < 5; i++) {
                if (i == pos)
                    continue;
                int j = rand.nextInt(256);
                magicNumbers[i] = j;
            }
            for (int i = 0; i < 5; i++)
                socket.getOutputStream().write(magicNumbers[i]);
            Gdx.app.postRunnable(() -> onMagicNumberSent.run(magicNumber));
            int chosenOne = socket.getInputStream().read();
            if (chosenOne == magicNumber) {
                socket.getOutputStream().write(0);
                isTheServer = true;
                setSelectedServer(new ServerInfo(socket.getInetAddress()));
                initConnection();
            } else {
                socket.getOutputStream().write(1);
                socket.close();
                acceptConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait and read the 5 numbers including the magic one.
     */
    protected void waitMagicNumber() {
        int[] numbers = new int[5];
        for (int i = 0; i < 5; i++) {
            try {
                numbers[i] = socket.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        Gdx.app.postRunnable(() -> onMagicNumberReceived.run(
                numbers[0],
                numbers[1],
                numbers[2],
                numbers[3],
                numbers[4]));
    }

    /**
     * Check if the chosen magic number is the good one.
     * @param i The chosen number
     * @return If it's the good one.
     */
    public boolean checkMagicNumber(int i) {
        try {
            socket.getOutputStream().write(i);
            if (socket.getInputStream().read() == 0) {
                initConnection();
                return true;
            } else
                if(onWrongMagicNumber != null)
                    Gdx.app.postRunnable(onWrongMagicNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
            } catch (SocketException e) {
                Gdx.app.error("NetworkManager", "Disconnected from distant partner !", e);
                onDisconnected();
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
        for (String mes : messages) {
            toSendOnTCP.push(mes);
            checkSendRunnableMessage(mes);
        }
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
     * @param messages The messages
     */
    public void sendOnUDP(String... messages) {
        for (String mes : messages) {
            if (toSendOnUDP.size() > 10)
                toSendOnUDP.pop();
            toSendOnUDP.push(mes);
            checkSendRunnableMessage(mes);
        }
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
                Thread.sleep(15);
            }
        }
    }

    /**
     * Send a message on the UDP socket.
     * @param message The message
     */
    protected void sendUDPMessage(String message) {
        if (selectedServer == null)
            throw new NullPointerException("No server was selected !");
        byte[] buf = message.getBytes();
        DatagramPacket p = new DatagramPacket(buf, buf.length, selectedServer.getIp(), PORT);
        try {
            ds.send(p);
        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "A message wasn't sent : " + message, e);
        }
    }

    /**
     * Link the action <code>run</code> to the message
     * @param message The message
     * @param run The action
     */
    public void whenMessageReceivedDo(String message, ObjectsRunnable run) {
        runnableMap.put(message, run);
    }
    /**
     * Link the action <code>run</code> to the message
     * @param message The message
     * @param run The action
     */
    public void whenMessageSentDo(String message, Runnable run) {
        runnableSendMap.put(message, run);
    }

    /**
     * Send the message and the given objects on the TCP link
     * @param message The message to send
     * @param objects The objects to send
     */
    public void sendMessageOnTCP(String message, Object... objects) {
        try {
            sendOnTCP(String.format("%s#%s", message, objectToString(objects)));
            checkSendRunnableMessage(message);

        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "Couldn't send message !", e);
        }
    }
    /**
     * Send the message and the given objects on the UDP link
     * @param message The message to send
     * @param objects The objects to send
     */
    public void sendMessageOnUDP(String message, Object... objects) {
        try {
            sendOnUDP(String.format("%s#%s", message, objectToString(objects)));
            checkSendRunnableMessage(message);
        } catch (IOException e) {
            Gdx.app.error("NetworkManager", "Couldn't send message !", e);
        }
    }

    protected void checkSendRunnableMessage(String mes) {
        Runnable run;
        if ((run = runnableSendMap.get(mes)) != null)
            Gdx.app.postRunnable(run);
    }

    /**
     * Process the received message and execute the necessary actions.
     * @param received The received message
     */
    protected void processMessage(String received) {
        processMessage(received, false);
    }

    /**
     * Process the received message and execute the necessary actions.
     * @param received The received message
     * @param again If it's the second time this message is processed
     */
    protected void processMessage(String received, boolean again) {
        if (received == null) {
            Gdx.app.error("NetworkManager", "Disconnected from distant server !");
            onDisconnected();
            return;
        }
        String[] tab = received.split("#");
        ObjectsRunnable run = runnableMap.get(tab[0]);
        if (run != null) {
            try {
                if (tab.length > 1) {
                    Object[] obj = (Object[]) objectFromString(tab[1].trim());
                    Gdx.app.postRunnable(() -> run.run(obj));
                }
                else
                    Gdx.app.postRunnable(() -> run.run(null));
            } catch (IOException | ClassNotFoundException e) {
                Gdx.app.error("NetworkManager", String.format("Couldn't read the message : %s !", received), e);
            }
        } else if (! again) {
            if (messagesNotRan.size() > 100)
                messagesNotRan.pop();
            messagesNotRan.add(received);
        }
        if (! received.equals(""))
            msSinceLastMessage = 0;
    }

    /**
     * Process again all the messages that wasn't ran.
     * CAUTION : After that, they can't be ran again.
     */
    public void processMessagesNotRan() {
        while (messagesNotRan.size() > 0)
            processMessage(messagesNotRan.pop(), true);
    }

    /**
     * Executed when the second player is disconnected from the game.
     */
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
    protected void addADetectedServer(Object[] tab, InetAddress serverAddress) {
//        Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
        if (detected != null && ! detected.contains(tab[0])) {
            detected.add((String) tab[0]);
            try {
                detectedServers.add(new ServerInfo((String) tab[2], serverAddress,
                        getMyAddressFromBroadcast(InetAddress.getByName((String) tab[1]))));
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
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = false;
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
     * @param onMagicNumberSent What to do when the magic number has been sent
     */
    public void setOnMagicNumberSent(IntRunnable onMagicNumberSent) {
        this.onMagicNumberSent = onMagicNumberSent;
    }

    /**
     * @param onMagicNumberReceived What to do when the five magics numbers are received.
     */
    public void setOnMagicNumberReceived(OnMagicNumberReceivedRunnable onMagicNumberReceived) {
        this.onMagicNumberReceived = onMagicNumberReceived;
    }

    /**
     * @param onWrongMagicNumber What to do if the user chose the wrong magic number.
     */
    public void setOnWrongMagicNumber(Runnable onWrongMagicNumber) {
        this.onWrongMagicNumber = onWrongMagicNumber;
    }

    public void setOnServerDetected(Runnable onServerDetected) {
        this.onServerDetected = onServerDetected;
    }

    /**
     * @return If the current manager is considered as a server (if it was waiting for a connection).
     */
    public boolean isTheServer() {
        return isTheServer;
    }

    /**
     * @param onDisconnected What to do when the second player is disconnected
     */
    public void setOnDisconnected(Runnable onDisconnected) {
        this.onDisconnected = onDisconnected;
    }

    public interface IntRunnable {
        void run(int i);
    }
    /**
     * Represent a runnable with a 5 int as parameter.
     */
    public interface OnMagicNumberReceivedRunnable {
        void run(int i, int j, int k, int l, int m);
    }

    public interface ObjectsRunnable {
        void run(Object[] objects);
    }
}
