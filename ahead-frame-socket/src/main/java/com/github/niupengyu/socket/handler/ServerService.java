package com.github.niupengyu.socket.handler;

import com.github.niupengyu.socket.bean.Message;
import org.apache.mina.core.session.IoSession;

public interface ServerService {

    void messageReceived(Message str, IoSession session) ;

    void heartbeat(IoSession session, Message msg) ;

    void heartbeatTimeOut(IoSession session) ;

    void heartbeatTimeOut(IoSession session, String disconnect) ;

    void setSession(IoSession session) ;

    void closed(IoSession session);

}
