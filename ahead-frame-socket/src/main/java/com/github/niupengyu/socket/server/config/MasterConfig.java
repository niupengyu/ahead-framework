package com.github.niupengyu.socket.server.config;

import com.github.niupengyu.core.annotation.AutoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("masterConfig")
@ConfigurationProperties(prefix = "news.server")
@AutoConfig(name="news.server.enable")
public class MasterConfig {

    private String name;

    private int port;

    private boolean enable;

    private int ideTime=60;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getIdeTime() {
        return ideTime;
    }

    public void setIdeTime(int ideTime) {
        this.ideTime = ideTime;
    }
}
