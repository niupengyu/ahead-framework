package com.github.niupengyu.jdbc.datasource;

import com.github.niupengyu.core.util.DateUtil;

public class ConnectionInfo {

    private long startLost;

    private long endLost;

    public ConnectionInfo(long startLost) {
        this.startLost = startLost;
    }

    public long getStartLost() {
        return startLost;
    }

    public void setStartLost(long startLost) {
        this.startLost = startLost;
    }

    public long getEndLost() {
        return endLost;
    }

    public void setEndLost(long endLost) {
        this.endLost = endLost;
    }

    public String timeDes() {
        return DateUtil.getTimeDes(endLost-startLost);
    }
}
