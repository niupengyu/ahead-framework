package com.github.niupengyu.socket.handler;

import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

public interface KeepAliveService {

    void heartbeatTimeOut(IoSession session);

    boolean isHeartbeatRequest(Message message);

    Object getResponse(Message request);

    Object getRequest();

    boolean isHeartbeatResponse(Message message);
}
