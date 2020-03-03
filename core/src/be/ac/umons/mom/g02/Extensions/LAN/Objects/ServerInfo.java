package be.ac.umons.mom.g02.Extensions.LAN.Objects;

import java.net.InetAddress;

public class ServerInfo {
    protected String name;
    protected InetAddress ip;
    protected int port;

    public ServerInfo(String name, InetAddress ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
