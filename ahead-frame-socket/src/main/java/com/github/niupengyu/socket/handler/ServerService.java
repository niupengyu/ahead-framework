package com.github.niupengyu.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.util.SessionManager;
import org.apache.mina.core.session.IoSession;

public interface ServerService {

    void sendRequest(long sessionId, Message message) throws Exception;

    void messageReceived(Message str, IoSession session) throws SysException, Exception;

    void heartbeat(IoSession session, Message msg) throws SysException, JsonProcessingException, Exception;

    void heartbeatTimeOut(IoSession session) ;

    void heartbeatTimeOut(IoSession session, String disconnect) ;

    void setSession(IoSession session) ;

    void closed(IoSession session);

    void sendRequest(long sessionId, String type,Object msg) throws SysException, Exception;

    void sendResponse(long sessionId, Message request,Object msg) throws SysException, Exception;

    void sendResponse(long sessionId,Message message) throws SysException, Exception;
}
