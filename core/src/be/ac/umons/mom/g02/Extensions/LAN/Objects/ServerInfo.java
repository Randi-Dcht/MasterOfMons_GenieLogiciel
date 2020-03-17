package be.ac.umons.mom.g02.Extensions.LAN.Objects;

import java.net.InetAddress;

public class ServerInfo {
    protected String name;
    protected InetAddress ip;
    protected InetAddress myIPOnTheSameNetwork;

    public ServerInfo(InetAddress ip) {
        this("", ip, null);
    }

    public ServerInfo(String name, InetAddress ip, InetAddress myIPOnTheSameNetwork) {
        this.name = name;
        this.ip = ip;
        this.myIPOnTheSameNetwork = myIPOnTheSameNetwork;
    }

    public String getName() {
        return name;
    }

    public InetAddress getIp() {
        return ip;
    }

    public InetAddress getMyIPOnTheSameNetwork() {
        return myIPOnTheSameNetwork;
    }
}
