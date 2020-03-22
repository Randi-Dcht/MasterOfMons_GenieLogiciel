package be.ac.umons.mom.g02.Extensions.LAN.Objects;

import java.net.InetAddress;

public class ServerInfo {
    /**
     * The name of the server
     */
    protected String name;
    /**
     * The IP address of the server
     */
    protected InetAddress ip;
    /**
     * The IP of this machine on the same network as the server
     */
    protected InetAddress myIPOnTheSameNetwork;

    /**
     * @param ip The IP address of the server
     */
    public ServerInfo(InetAddress ip) {
        this("", ip, null);
    }

    /**
     * @param name The name of the server
     * @param ip The IP address of the server
     * @param myIPOnTheSameNetwork The IP address of this machine on the same network as the server
     */
    public ServerInfo(String name, InetAddress ip, InetAddress myIPOnTheSameNetwork) {
        this.name = name;
        this.ip = ip;
        this.myIPOnTheSameNetwork = myIPOnTheSameNetwork;
    }

    /**
     * @return The name of the server
     */
    public String getName() {
        return name;
    }

    /**
     * @return The IP address of the server
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * @return The IP of this machine on the same network as the server
     */
    public InetAddress getMyIPOnTheSameNetwork() {
        return myIPOnTheSameNetwork;
    }
}
