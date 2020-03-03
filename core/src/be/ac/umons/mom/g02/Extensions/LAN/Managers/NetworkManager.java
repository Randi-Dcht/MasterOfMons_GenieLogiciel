package be.ac.umons.mom.g02.Extensions.LAN.Managers;

import be.ac.umons.mom.g02.Extensions.LAN.Objects.ServerInfo;
import com.badlogic.gdx.Gdx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * https://www.baeldung.com/java-broadcast-multicast
 */
public class NetworkManager {

    protected static NetworkManager instance;

    public static NetworkManager getInstance() throws SocketException {
        if (instance == null)
            instance = new NetworkManager();
        return instance;
    }


    protected final int PORT = 32516;

    protected boolean connected = false;
    protected List<ServerInfo> detectedServers;
    HashMap<InetAddress, InetAddress> addressToBroadcast;
    protected DatagramSocket ds;
    protected Thread thread;
    protected Runnable onServerDetected;

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

    public void startBroadcastingMessage(String message) {
//        if (thread != null)
//            thread.interrupt();
        thread = new Thread(() -> {
            try {
                while (! connected) {
                    for (InetAddress address : addressToBroadcast.keySet())
                        broadcastMessage("MOMServer" + address.toString() + "/TestName", addressToBroadcast.get(address));
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
        thread.start();
    }

    protected void broadcastMessage(String message, InetAddress address) throws IOException {
//        ds.setBroadcast(true);
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, PORT);
        ds.send(packet);
    }

    public void startListeningForServer() {
//        if (thread != null)
//            thread.interrupt();
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
                String[] tab = received.split("/");
                Gdx.app.log("NetworkManager", String.format("Server detected : %s", tab[1]));
                try {
                    InetAddress serverAddress = InetAddress.getByName(tab[1]);
                    detectedServers.add(new ServerInfo(tab[2], serverAddress, PORT));
                    Gdx.app.postRunnable(onServerDetected);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * https://www.baeldung.com/java-broadcast-multicast
     * @return
     * @throws SocketException
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

    public List<ServerInfo> getDetectedServers() {
        return detectedServers;
    }

    public void setOnServerDetected(Runnable onServerDetected) {
        this.onServerDetected = onServerDetected;
    }

    public HashMap<InetAddress, InetAddress> getAddressToBroadcast() {
        return addressToBroadcast;
    }
}
