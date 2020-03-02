package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import com.badlogic.gdx.Gdx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * https://www.baeldung.com/java-broadcast-multicast
 */
public class NetworkManager {

    static NetworkManager instance;

    public static NetworkManager getInstance() throws SocketException {
        if (instance == null)
            instance = new NetworkManager();
        return instance;
    }


    protected final int PORT = 32516;

    ServerSocket serverSocket;
    Socket socket;
    List<ServerInfo> detectedServers;
    DataOutputStream dOut;
    DataInputStream dIn;

    DatagramSocket ds;

    Thread thread;

    protected NetworkManager() throws SocketException {
        ds = new DatagramSocket(PORT);
    }

    public void startBroadcastingMessage(String message) {
        if (thread != null)
            thread.interrupt();
        thread = new Thread(() -> {
            try {
                List<InetAddress> addressToBroadcast = listAllBroadcastAddresses();
                while (socket != null && ! socket.isConnected()) {
                    for (InetAddress address : addressToBroadcast)
                        broadcastMessage(message, address);
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    protected void broadcastMessage(String message, InetAddress address) throws IOException {
//        ds.setBroadcast(true);
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
        ds.send(packet);
    }

    public void startListeningForServer() {
        if (thread != null)
            thread.interrupt();
        thread = new Thread(this::listenToBroadcast);
        thread.start();
    }

    protected void listenToBroadcast() {
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
                Gdx.app.log("NetworkManager", "Server detected");
                break;
            }
        }
    }

    /**
     * https://www.baeldung.com/java-broadcast-multicast
     * @return
     * @throws SocketException
     */
    protected List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces
                = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            networkInterface.getInterfaceAddresses().stream()
                    .map(InterfaceAddress::getBroadcast)
                    .filter(Objects::nonNull)
                    .forEach(broadcastList::add);
        }
        return broadcastList;
    }


}
