package com.github.niupengyu.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

public interface KeepAliveService {

    void heartbeatTimeOut(IoSession session);

    boolean isHeartbeatRequest(Message message);

    Object getResponse(Message request) throws JsonProcessingException;

    Object getRequest() throws JsonProcessingException;

    boolean isHeartbeatResponse(Message message);
}
