package com.github.niupengyu.socket.handler;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.niupengyu.core.exception.SysException;
import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

public interface ClientService {

    void sendRequest(String type,Object msg) throws JsonProcessingException, Exception;

    void sendRequest(Message msg) throws SysException;

    void sendResponse(Message request,Object message) throws JsonProcessingException, Exception;

    public void sendResponse(Message msg) throws SysException;

    //String heartbeat(IoSession session);

    void reconnection() throws JsonProcessingException, Exception;

    void setSession(IoSession session) throws JsonProcessingException, Exception;

    boolean isHeartBeat(Message msg);

    void create() throws JsonProcessingException, Exception;

    void received(Object str, IoSession session);

    void heartbeat(IoSession session, Message obj);

    void connectionError() throws JsonProcessingException, Exception;
}
