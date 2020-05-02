package com.github.niupengyu.socket.client.config;

import com.github.niupengyu.core.annotation.AutoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("clientConfig")
@ConfigurationProperties(prefix = "news.client")
@AutoConfig(name="news.client.enable")
public class ClientConfig {

    private String server;

    private int port;

    private String id;

    private int count;

    private long mill;

    private String serverIp;

    private int serverPort;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getMill() {
        return mill;
    }

    public void setMill(long mill) {
        this.mill = mill;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
