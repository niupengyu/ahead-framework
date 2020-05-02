package com.github.niupengyu.socket.handler;


import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

public interface ClientService {

    void sendMessage(Object msg) ;

    //String heartbeat(IoSession session);

    void reconnection();

    void setSession(IoSession session);

    boolean isHeartBeat(Message msg);

    void create();

    void received(Object str, IoSession session);

    void heartbeat(IoSession session, Message obj);

    void connectionError();
}
