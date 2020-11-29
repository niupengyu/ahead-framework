package com.github.niupengyu.socket.util;

import com.github.niupengyu.socket.bean.Message;
import com.github.niupengyu.socket.bean.SessionInfo;
import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    public static ConcurrentHashMap<Long, SessionInfo> sessionsConcurrentHashMap =
            new ConcurrentHashMap<Long, SessionInfo>();

    public static ConcurrentHashMap<Long, StringBuffer> messagesConcurrentHashMap =
            new ConcurrentHashMap<Long, StringBuffer>();

    public static ConcurrentHashMap<String, Long> sessionsNodesHashMap =
            new ConcurrentHashMap<String, Long>();


    public static void sendMessage(Message message, Long id){
        IoSession session=getSession(id);
        if(session!=null){
            session.write(message.toJsonString());
        }
    }

    public static IoSession getSession(Long id){
        SessionInfo sessionInfo=getSessionInfo(id);
        if(sessionInfo!=null){
            IoSession session=sessionInfo.getSession();
            return session;
        }
        return null;
    }

    public static SessionInfo getSessionInfo(Long id){
        if(sessionsConcurrentHashMap.containsKey(id)){
            SessionInfo sessionInfo=sessionsConcurrentHashMap.get(Long.valueOf(id));
            return sessionInfo;
        }
        return null;
    }

    public static void putMessage(long id,String msg){
        if(messagesConcurrentHashMap.contains(id)){
            messagesConcurrentHashMap.get(id).append(msg);
        }else{
            messagesConcurrentHashMap.put(id,new StringBuffer().append(msg));
        }
    }

    public static StringBuffer getMessage(long id){
        return messagesConcurrentHashMap.get(id);
    }
}
