package be.ac.umons.mom.g02.Extensions.LAN.Objects;

import java.net.InetAddress;

public class ServerInfo {
    protected String name;
    protected InetAddress ip;

    public ServerInfo(String name, InetAddress ip, int port) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIp() {
        return ip;
    }
}
