package com.github.niupengyu.socket.bean;

import org.apache.mina.core.session.IoSession;

public class SessionInfo {

    private IoSession session;

    private long createTime;

    public SessionInfo(IoSession session) {
        this.session=session;
        this.createTime=System.currentTimeMillis();
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
